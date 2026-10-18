package com.proyecto1.labs.lab3.component;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proyecto1.labs.lab1.entity.Persona;
import com.proyecto1.labs.lab1.repository.PersonaRepository;

/**
 * LAB 3 (actividad complementaria 7) - Cambia al azar la ubicación de las personas
 * usando direcciones estáticas, para que los marcadores del mapa se muevan.
 * Se activa con labs.lab3.random.enabled=true
 */
@Component("lab3UbicacionAleatoriaTask")
@ConditionalOnProperty(name = "labs.lab3.random.enabled", havingValue = "true")
public class UbicacionAleatoriaTask {

    private static final Logger logger = LoggerFactory.getLogger(UbicacionAleatoriaTask.class);

    private static final String[] UBICACIONES = {
            "Conservatorio del Tolima, Ibagué, Tolima",
            "Museo Panóptico de Ibagué, Ibagué, Tolima",
            "La Estación Centro Comercial Ibagué, Ibagué, Tolima",
            "Acqua Power Center, Ibagué, Tolima",
            "Parque Deportivo, Ibagué, Tolima",
            "Centro Comercial Multicentro, Ibagué, Tolima",
            "Universidad del Tolima, Ibagué, Tolima"
    };

    private final Random random = new Random();

    @Autowired
    @Qualifier("lab1PersonaRepository")
    private PersonaRepository personaRepository;

    @Scheduled(cron = "${labs.lab3.random.cron:0 */2 * * * ?}")
    public void cambiarUbicaciones() {
        List<Persona> personas = personaRepository.findAll();
        for (Persona persona : personas) {
            persona.setUbicacion(UBICACIONES[random.nextInt(UBICACIONES.length)]);
        }
        personaRepository.saveAll(personas);
        logger.info("Ubicaciones aleatorias actualizadas para {} personas", personas.size());
    }
}
