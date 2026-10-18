package com.proyecto1.labs.lab2.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** LAB 2 - Entidad Usuario (tabla lab_usuario). */
@Entity(name = "Lab2Usuario")
@Table(name = "lab_usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UsuarioPK id = new UsuarioPK();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "apikey")
    private String apikey;

    public Usuario() {}

    public Usuario(String login, Long persona, String password) {
        this.id = new UsuarioPK(login, persona);
        this.password = password;
    }

    public UsuarioPK getId() { return id; }
    public void setId(UsuarioPK id) { this.id = id; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getApikey() { return apikey; }
    public void setApikey(String apikey) { this.apikey = apikey; }
}
