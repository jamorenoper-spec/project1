package com.proyecto1.labs.lab2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.labs.lab2.entity.Usuario;
import com.proyecto1.labs.lab2.entity.UsuarioPK;
import com.proyecto1.labs.lab2.service.IUsuarioService;

/** LAB 2 - CRUD de usuario (protegido con token). */
@RestController("lab2UsuarioController")
@RequestMapping("/Laboratorio2")
public class UsuarioController {

    @Autowired
    @Qualifier("lab2UsuarioService")
    private IUsuarioService usuarioService;

    @PostMapping("/usuarios")
    public ResponseEntity<String> guardar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario)
                ? ResponseEntity.status(HttpStatus.CREATED).body("Usuario guardado")
                : ResponseEntity.badRequest().body("No se pudo guardar el usuario");
    }

    @GetMapping("/usuarios")
    public List<Usuario> consultar(Pageable pageable) {
        return usuarioService.consultarUsuario(pageable);
    }

    @PutMapping("/usuarios")
    public ResponseEntity<String> actualizar(@RequestBody Usuario usuario) {
        return usuarioService.actualizar(usuario)
                ? ResponseEntity.ok("Usuario actualizado")
                : ResponseEntity.badRequest().body("No se pudo actualizar el usuario");
    }

    @DeleteMapping("/usuarios")
    public ResponseEntity<String> eliminar(@RequestParam String login, @RequestParam Long persona) {
        return usuarioService.eliminar(new UsuarioPK(login, persona))
                ? ResponseEntity.ok("Usuario eliminado")
                : ResponseEntity.badRequest().body("No se pudo eliminar el usuario");
    }
}
