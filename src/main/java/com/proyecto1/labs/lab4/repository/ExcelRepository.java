package com.proyecto1.labs.lab4.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository;

import com.proyecto1.labs.lab1.entity.Persona;

/**
 * LAB 4 - Invoca los procedimientos almacenados de MySQL.
 * El procedureName debe ser IGUAL al nombre del procedimiento en la base de datos.
 */
@org.springframework.stereotype.Repository("lab4ExcelRepository")
public interface ExcelRepository extends Repository<Persona, Long> {

    @Procedure(procedureName = "CARGAR_DATOS_EXCEL")
    void CARGAR_DATOS_EXCEL(String pnombre, String edad, String ubicacion, Double idCargue);

    @Procedure(procedureName = "VALIDAR_DATOS_CARGUE")
    void VALIDAR_DATOS_CARGUE(Double idCargue);

    @Procedure(procedureName = "PROCESAR_DATOS_EXCEL")
    void PROCESAR_DATOS_EXCEL(Double idCargue);
}
