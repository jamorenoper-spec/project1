package com.proyecto1.labs.lab1.service;

import java.util.List;

import com.proyecto1.labs.lab1.entity.Persona;

public interface IPersonaService {

    boolean guardar(Persona persona);

    boolean actualizar(Persona persona);

    boolean eliminar(Long id);

    List<Persona> consultarTodas();

    Persona consultarPorId(Long id);
}
