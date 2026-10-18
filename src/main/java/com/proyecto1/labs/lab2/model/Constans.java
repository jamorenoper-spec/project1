package com.proyecto1.labs.lab2.model;

/** LAB 2 - Constantes de seguridad y JWT. */
public final class Constans {

    private Constans() {}

    // Spring Security
    public static final String LOGIN_URL = "/Laboratorio2/authenticate";
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String HEADER_APIKEY = "apikey";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // Rutas que protege la seguridad de los laboratorios
    public static final String[] RUTAS_LABORATORIOS = {
            "/Laboratorio1/**", "/Laboratorio2/**", "/Laboratorio3/**", "/Laboratorio4/**"
    };

    // JWT
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000L; // 10 días
}
