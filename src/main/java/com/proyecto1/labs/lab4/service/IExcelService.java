package com.proyecto1.labs.lab4.service;

import org.springframework.web.multipart.MultipartFile;

public interface IExcelService {

    /** Lee el Excel, lo carga, valida y procesa. Devuelve el idCargue generado. */
    Double processExcel(MultipartFile file);

    boolean cargarDatosExcel(String pnombre, String edad, String ubicacion, Double idCargue);

    boolean validarDatosExcel(Double idCargue);

    boolean procesarDatosExcel(Double idCargue);
}
