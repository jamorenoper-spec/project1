package com.proyecto1.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.DTO.CambioPasswordRequest;
import com.proyecto1.Services.Interfaces.IUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/proyecto1/usuario")
public class UsuarioController {

    // ==========INYECCION DEL SERVICE==========
    @Autowired
    @Qualifier("UsuarioService")
    private IUsuarioService usuarioService;

    // PUT: cambia el password. El login va en la URL y el nuevo password en el body.
    @PutMapping("/{login}/password")
    public ResponseEntity<Map<String, String>> cambiarPassword(@PathVariable("login") String login,
            @RequestBody @Valid CambioPasswordRequest request) {
        usuarioService.cambiarPassword(login, request.password());
        return ResponseEntity.ok(Map.of("mensaje", "Password actualizado correctamente"));
    }

    // GET: genera nuevamente el APIKey del usuario.
    @GetMapping("/{login}/apikey")
    public ResponseEntity<Map<String, String>> regenerarApikey(@PathVariable("login") String login) {
        String apikey = usuarioService.regenerarApikey(login);
        return ResponseEntity.ok(Map.of("login", login, "apikey", apikey));
    }
}