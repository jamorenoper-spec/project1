package com.proyecto1.labs.lab1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto1.labs.lab1.entity.Persona;

@Repository("lab1PersonaRepository")
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query("SELECT p FROM Lab1Persona p WHERE p.ubicacion IS NOT NULL")
    List<Persona> getPersonas();
}
