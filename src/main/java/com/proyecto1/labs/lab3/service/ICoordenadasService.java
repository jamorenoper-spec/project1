package com.proyecto1.labs.lab3.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.proyecto1.labs.lab3.entity.Coordenadas;

public interface ICoordenadasService {

    List<Coordenadas> consultarAllCoordenadas(Pageable pageable);
}
