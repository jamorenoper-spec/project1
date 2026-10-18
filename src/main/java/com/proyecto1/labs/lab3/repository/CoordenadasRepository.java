package com.proyecto1.labs.lab3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto1.labs.lab3.entity.Coordenadas;

@Repository("lab3CoordenadasRepository")
public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long> {

    // JPA ya trae estos métodos, se redefinen para tener control en el service
    Page<Coordenadas> findAll(Pageable pageable);

    @Query("SELECT coord FROM Lab3Coordenadas coord WHERE coord.persona = :id_persona")
    Coordenadas getCoordenadaXPersona(@Param("id_persona") Long persona);
}
