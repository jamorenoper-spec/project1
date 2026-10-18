package com.proyecto1.labs.lab1.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.proyecto1.labs.lab1.entity.Persona;
import com.proyecto1.labs.lab1.repository.PersonaRepository;

@Service("lab1PersonaService")
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    @Qualifier("lab1PersonaRepository")
    private PersonaRepository personaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PersonaServiceImpl.class);

    @Override
    public boolean guardar(Persona persona) {
        try {
            if (persona == null) {
                logger.error("ERROR AGREGAR_PERSONA: LA PERSONA ES NULA");
                return false;
            }
            personaRepository.save(persona);
            return true;
        } catch (Exception e) {
            logger.error("ERROR AGREGAR_PERSONA: NO SE HA GUARDADO", e);
            return false;
        }
    }

    @Override
    public boolean actualizar(Persona persona) {
        try {
            if (persona == null || persona.getId() == null || !personaRepository.existsById(persona.getId())) {
                logger.error("ERROR EDITAR_PERSONA: PERSONA NULA O ID NO EXISTE");
                return false;
            }
            personaRepository.save(persona);
            return true;
        } catch (Exception e) {
            logger.error("ERROR EDITAR_PERSONA: NO SE HA EDITADO", e);
            return false;
        }
    }

    @Override
    public boolean eliminar(Long id) {
        try {
            if (id == null || !personaRepository.existsById(id)) {
                logger.error("ERROR ELIMINAR_PERSONA: ID NULO O NO EXISTE");
                return false;
            }
            personaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("ERROR ELIMINAR_PERSONA: NO SE HA ELIMINADO", e);
            return false;
        }
    }

    @Override
    public List<Persona> consultarTodas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona consultarPorId(Long id) {
        return personaRepository.findById(id).orElse(null);
    }
}
