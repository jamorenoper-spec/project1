package com.proyecto1.labs.lab3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto1.labs.lab3.entity.Coordenadas;
import com.proyecto1.labs.lab3.repository.CoordenadasRepository;

@Service("lab3CoordenadasService")
public class CoordenadasServiceImpl implements ICoordenadasService {

    // ========= INYECCIÓN DE DEPENDENCIAS =========
    @Autowired
    @Qualifier("lab3CoordenadasRepository")
    private CoordenadasRepository coordenadaRepository;

    @Override
    public List<Coordenadas> consultarAllCoordenadas(Pageable pageable) {
        return coordenadaRepository.findAll(pageable).getContent();
    }
}
