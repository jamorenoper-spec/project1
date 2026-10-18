package com.proyecto1.labs.lab1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.labs.lab1.entity.Persona;
import com.proyecto1.labs.lab1.service.IPersonaService;

/** LAB 1 - CRUD de persona. URL base: /Laboratorio1 */
@RestController("lab1PersonaController")
@RequestMapping("/Laboratorio1")
public class PersonaController {

    @Autowired
    @Qualifier("lab1PersonaService")
    private IPersonaService personaService;

    @PostMapping("/personas")
    public ResponseEntity<String> guardar(@RequestBody Persona persona) {
        return personaService.guardar(persona)
                ? ResponseEntity.status(HttpStatus.CREATED).body("Persona guardada")
                : ResponseEntity.badRequest().body("No se pudo guardar la persona");
    }

    @GetMapping("/personas")
    public List<Persona> consultarTodas() {
        return personaService.consultarTodas();
    }

    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> consultarPorId(@PathVariable Long id) {
        Persona p = personaService.consultarPorId(id);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @PutMapping("/personas")
    public ResponseEntity<String> actualizar(@RequestBody Persona persona) {
        return personaService.actualizar(persona)
                ? ResponseEntity.ok("Persona actualizada")
                : ResponseEntity.badRequest().body("No se pudo actualizar la persona");
    }

    @DeleteMapping("/personas/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return personaService.eliminar(id)
                ? ResponseEntity.ok("Persona eliminada")
                : ResponseEntity.badRequest().body("No se pudo eliminar la persona");
    }
}
