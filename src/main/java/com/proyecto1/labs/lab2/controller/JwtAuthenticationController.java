package com.proyecto1.labs.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.labs.lab2.config.JWTAuthtenticationConfig;
import com.proyecto1.labs.lab2.entity.Usuario;
import com.proyecto1.labs.lab2.model.JwtRequest;
import com.proyecto1.labs.lab2.model.JwtResponse;
import com.proyecto1.labs.lab2.service.IUsuarioService;

/** LAB 2 - Autenticación: devuelve el token. Valida usuario, password y APIKey (header "apikey"). */
@RestController("lab2JwtController")
@RequestMapping("/Laboratorio2")
public class JwtAuthenticationController {

    @Autowired
    @Qualifier("lab2UsuarioService")
    private IUsuarioService usuarioService;

    @Autowired
    @Qualifier("lab2PasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("lab2JwtConfig")
    private JWTAuthtenticationConfig jwtConfig;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest request,
            @RequestHeader(value = "apikey", required = false) String apikey) {

        Usuario usuario = usuarioService.getUsuarioByLogin(request.getUsername());

        if (usuario == null || request.getPassword() == null
                || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
        if (apikey == null || !apikey.equals(usuario.getApikey())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("APIKey inválida");
        }
        return ResponseEntity.ok(new JwtResponse(jwtConfig.getJWTToken(usuario.getId().getLogin())));
    }
}
