package com.proyecto1.labs.lab4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto1.labs.lab4.service.IExcelService;

/**
 * LAB 4 - Cargue masivo desde Excel.
 * POST /Laboratorio4/upload-excel  (Body > form-data > key "file" de tipo File). Requiere token.
 */
@RestController("lab4CargueFileExcel")
@RequestMapping("/Laboratorio4")
public class CargueFileExcel {

    private final IExcelService excelService;

    public CargueFileExcel(@Qualifier("lab4ExcelService") IExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping(value = "/upload-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            Double idCargue = excelService.processExcel(file);
            return ResponseEntity.ok("Archivo subido y procesado exitosamente. idCargue = " + idCargue.longValue());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
