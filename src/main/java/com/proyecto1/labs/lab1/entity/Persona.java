package com.proyecto1.labs.lab1.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * LAB 1 - Entidad base Persona (la usan Lab 2, Lab 3 y Lab 4).
 * Se llama "lab_persona" para NO chocar con la tabla "persona" de las Entregas del proyecto.
 */
@Entity(name = "Lab1Persona")
@Table(name = "lab_persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PNOMBRE")
    private String pnombre;

    @Column(name = "EDAD")
    private Integer edad;

    @Column(name = "UBICACION")
    private String ubicacion;

    public Persona() {}

    public Persona(String pnombre, Integer edad, String ubicacion) {
        this.pnombre = pnombre;
        this.edad = edad;
        this.ubicacion = ubicacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPnombre() { return pnombre; }
    public void setPnombre(String pnombre) { this.pnombre = pnombre; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}
