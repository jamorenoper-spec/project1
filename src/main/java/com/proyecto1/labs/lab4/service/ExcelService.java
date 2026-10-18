package com.proyecto1.labs.lab4.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto1.labs.lab4.repository.ExcelRepository;

@Service("lab4ExcelService")
public class ExcelService implements IExcelService {

    // ========= INYECCIÓN DE DEPENDENCIAS =========
    @Autowired
    @Qualifier("lab4ExcelRepository")
    private ExcelRepository excelRepository;

    // ==================== LOGS ====================
    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    private final DataFormatter formatter = new DataFormatter();

    /** Devuelve el texto de la celda o null si está vacía (así el procedimiento de validación la marca como error). */
    private String texto(Row row, int columna) {
        Cell cell = row.getCell(columna);
        if (cell == null) {
            return null;
        }
        String valor = formatter.formatCellValue(cell).trim();
        return valor.isEmpty() ? null : valor;
    }

    @Override
    public Double processExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // El idCargue identifica los datos de ESTE archivo (año mes día hora minuto segundo)
        double idCargue = Double.parseDouble(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        try (InputStream inputStream = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // se procesa la primera hoja

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // fila 0 = encabezados (NOMBRE, EDAD, UBICACIÓN)
                }
                String pnombre = texto(row, 0);
                String edad = texto(row, 1);
                String ubicacion = texto(row, 2);

                if (pnombre == null && edad == null && ubicacion == null) {
                    continue; // fila totalmente vacía
                }
                logger.info("Dato: {} - {} - {} - {}", pnombre, edad, ubicacion, idCargue);
                if (cargarDatosExcel(pnombre, edad, ubicacion, idCargue)) {
                    logger.info("Cargue de datos inicial. EXITOSO");
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("No se pudo leer el archivo Excel: " + e.getMessage(), e);
        }

        if (validarDatosExcel(idCargue)) {
            logger.info("Validación de datos cargados. EXITOSO");
            if (procesarDatosExcel(idCargue)) {
                logger.info("Procesado de datos validados. EXITOSO");
            }
        }
        return idCargue;
    }

    @Override
    public boolean cargarDatosExcel(String pnombre, String edad, String ubicacion, Double idCargue) {
        try {
            excelRepository.CARGAR_DATOS_EXCEL(pnombre, edad, ubicacion, idCargue);
            return true;
        } catch (Exception e) {
            logger.error("ERROR CARGAR DATOS DE ARCHIVO EXCEL: PROBLEMAS CON LA INVOCACIÓN DEL PROCEDIMIENTO ALMACENADO", e);
            return false;
        }
    }

    @Override
    public boolean validarDatosExcel(Double idCargue) {
        try {
            excelRepository.VALIDAR_DATOS_CARGUE(idCargue);
            return true;
        } catch (Exception e) {
            logger.error("ERROR VALIDANDO DATOS DE ARCHIVO EXCEL: PROBLEMAS CON LA INVOCACIÓN DEL PROCEDIMIENTO ALMACENADO", e);
            return false;
        }
    }

    @Override
    public boolean procesarDatosExcel(Double idCargue) {
        try {
            excelRepository.PROCESAR_DATOS_EXCEL(idCargue);
            return true;
        } catch (Exception e) {
            logger.error("ERROR PROCESAR DATOS DE ARCHIVO EXCEL: PROBLEMAS CON LA INVOCACIÓN DEL PROCEDIMIENTO ALMACENADO", e);
            return false;
        }
    }
}
