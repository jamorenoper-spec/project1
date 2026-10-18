package com.proyecto1.labs.lab2.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.proyecto1.labs.lab2.entity.Usuario;
import com.proyecto1.labs.lab2.entity.UsuarioPK;

public interface IUsuarioService {

    // MÉTODOS CRUD
    boolean guardar(Usuario usuario);

    boolean actualizar(Usuario usuario);

    boolean eliminar(UsuarioPK id);

    List<Usuario> consultarUsuario(Pageable pageable);

    Usuario getUsuarioById(UsuarioPK id);

    Usuario getUsuarioByLogin(String login);
}
