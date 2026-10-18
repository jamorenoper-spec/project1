package com.proyecto1.labs.lab2.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** LAB 2 - Clave primaria compuesta (login + persona). */
@Embeddable
public class UsuarioPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "login")
    private String login;

    @Column(name = "persona")
    private Long persona;

    public UsuarioPK() {}

    public UsuarioPK(String login, Long persona) {
        this.login = login;
        this.persona = persona;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public Long getPersona() { return persona; }
    public void setPersona(Long persona) { this.persona = persona; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioPK)) return false;
        UsuarioPK other = (UsuarioPK) o;
        return Objects.equals(login, other.login) && Objects.equals(persona, other.persona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, persona);
    }
}
