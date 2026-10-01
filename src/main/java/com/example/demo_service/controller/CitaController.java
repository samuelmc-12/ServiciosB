package com.example.demo_service.controller;

import com.example.demo_service.model.Cita;
import com.example.demo_service.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(
            CitaService citaService
    ) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(
                citaService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> buscar(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                citaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Cita> guardar(
            @RequestBody Cita cita
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(citaService.guardar(cita));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
