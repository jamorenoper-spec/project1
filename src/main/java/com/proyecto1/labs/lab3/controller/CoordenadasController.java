package com.proyecto1.labs.lab3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.labs.lab3.entity.Coordenadas;
import com.proyecto1.labs.lab3.service.ICoordenadasService;

/** LAB 3 - Consulta las coordenadas guardadas. URL: GET /Laboratorio3/coordenadas (requiere token). */
@RestController("lab3CoordenadasController")
@RequestMapping("/Laboratorio3")
public class CoordenadasController {

    // ==========INYECCION DEL SERVICE==========
    @Autowired
    @Qualifier("lab3CoordenadasService")
    private ICoordenadasService coordenadaService;

    // ==========MÉTODOS HTTP====================
    // @CrossOrigin permite que la página html (XAMPP, http://localhost) haga la petición
    @CrossOrigin(origins = { "http://localhost", "http://127.0.0.1" })
    @GetMapping("/coordenadas")
    public List<Coordenadas> consultarAllCoordenadas(Pageable pageable) {
        return coordenadaService.consultarAllCoordenadas(pageable);
    }
}
