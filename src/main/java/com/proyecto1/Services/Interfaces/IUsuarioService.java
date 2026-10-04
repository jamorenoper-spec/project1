package com.proyecto1.Services.Interfaces;

public interface IUsuarioService {

    void cambiarPassword(String login, String nuevoPassword);

    String regenerarApikey(String login);
}