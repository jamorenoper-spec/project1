package com.proyecto1.labs.lab2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto1.labs.lab2.entity.Usuario;
import com.proyecto1.labs.lab2.entity.UsuarioPK;

@Repository("lab2UsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioPK> {

    // Consultas JPQL (usan el nombre de entidad "Lab2Usuario")
    @Query("SELECT usr FROM Lab2Usuario usr WHERE usr.id.login = :login AND usr.id.persona = :persona")
    Usuario getUsuarioANDPersona(@Param("login") String login, @Param("persona") Long persona);

    @Query("SELECT usr FROM Lab2Usuario usr WHERE usr.id.login = :login")
    List<Usuario> findByUsername(@Param("login") String login);
}
