package com.proyecto1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto1.DTO.CargueTrayectoResponse;
import com.proyecto1.Entities.TrayectoTmp;
import com.proyecto1.Services.Interfaces.ITrayectoCargueService;

@RestController
@RequestMapping("/proyecto1/trayecto/cargue")
public class TrayectoCargueController {

    // ==========INYECCION DEL SERVICE==========
    @Autowired
    @Qualifier("TrayectoCargueService")
    private ITrayectoCargueService trayectoCargueService;

    // POST: cargue masivo de trayectos a partir de la plantilla Excel (multipart/form-data, campo "archivo").
    @PostMapping
    public ResponseEntity<CargueTrayectoResponse> cargar(@RequestParam("archivo") MultipartFile archivo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trayectoCargueService.cargarExcel(archivo));
    }

    // GET: consulta de los registros de un cargue especifico por su identificador.
    @GetMapping("/{idCargue}")
    public ResponseEntity<List<TrayectoTmp>> consultarCargue(@PathVariable("idCargue") Long idCargue) {
        return ResponseEntity.ok(trayectoCargueService.consultarCargue(idCargue));
    }
}
