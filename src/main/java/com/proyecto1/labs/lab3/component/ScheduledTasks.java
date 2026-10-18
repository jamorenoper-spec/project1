package com.proyecto1.labs.lab3.component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proyecto1.labs.lab1.entity.Persona;
import com.proyecto1.labs.lab1.repository.PersonaRepository;
import com.proyecto1.labs.lab3.apis.Geocoder;
import com.proyecto1.labs.lab3.entity.Coordenadas;
import com.proyecto1.labs.lab3.repository.CoordenadasRepository;

/**
 * LAB 3 - Tarea programada: busca las coordenadas de la ubicación de cada persona y las guarda.
 * Se activa con labs.lab3.scheduler.enabled=true; la frecuencia se cambia con labs.lab3.cron
 */
@Component("lab3ScheduledTasks")
@ConditionalOnProperty(name = "labs.lab3.scheduler.enabled", havingValue = "true")
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // ========= INYECCIÓN DE DEPENDENCIAS =========
    @Autowired
    @Qualifier("lab1PersonaRepository")
    private PersonaRepository personaRepository;

    @Autowired
    @Qualifier("lab3CoordenadasRepository")
    private CoordenadasRepository coordenadaRepository;

    @Autowired
    @Qualifier("lab3Geocoder")
    private Geocoder geocoder;

    @Scheduled(cron = "${labs.lab3.cron:*/30 * * * * ?}")
    public void scheduleTaskWithCronExpression() {
        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

        if (!geocoder.tieneApiKey()) {
            logger.warn("Falta la llave de Google Maps (google.maps.api-key). La tarea no hace nada.");
            return;
        }

        List<Persona> listPersonas = personaRepository.getPersonas();
        if (listPersonas == null || listPersonas.isEmpty()) {
            return;
        }

        for (Persona persona : listPersonas) {
            try {
                String latLng = geocoder.getLatLng(persona.getUbicacion());
                if (latLng == null) {
                    continue;
                }
                String[] coor = latLng.split(",");
                double latitud = Double.parseDouble(coor[0]);
                double longitud = Double.parseDouble(coor[1]);
                logger.info("{} -> {} - {}", persona.getPnombre(), latLng,
                        dateTimeFormatter.format(LocalDateTime.now()));

                Coordenadas coorXper = coordenadaRepository.getCoordenadaXPersona(persona.getId());
                if (coorXper == null) {
                    coordenadaRepository.save(new Coordenadas(persona.getId(), persona.getPnombre(),
                            longitud, latitud));
                } else {
                    coorXper.setMarca(persona.getPnombre());
                    coorXper.setLatitud(latitud);
                    coorXper.setLongitud(longitud);
                    coordenadaRepository.save(coorXper);
                }
            } catch (Exception e) {
                logger.error("Error con la persona {}: {}", persona.getId(), e.getMessage());
            }
        }
    }
}
