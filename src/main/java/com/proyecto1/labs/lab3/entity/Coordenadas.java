package com.proyecto1.labs.lab3.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** LAB 3 - Coordenadas (latitud/longitud) de la ubicación de cada persona. */
@Entity(name = "Lab3Coordenadas")
@Table(name = "lab_coordenadas")
public class Coordenadas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenada")
    private Long id;

    @Column(name = "persona")
    private Long persona;

    @Column(name = "marca")
    private String marca;

    @Column(name = "longitud")
    private double longitud;

    @Column(name = "latitud")
    private double latitud;

    public Coordenadas() {}

    public Coordenadas(Long persona, String marca, double longitud, double latitud) {
        this.persona = persona;
        this.marca = marca;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Coordenadas(Long id, Long persona, String marca, double longitud, double latitud) {
        this(persona, marca, longitud, latitud);
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPersona() { return persona; }
    public void setPersona(Long persona) { this.persona = persona; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }
}
