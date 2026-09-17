package com.example.demo_service.controller;

import com.example.demo_service.model.Mascota;
import com.example.demo_service.service.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(
            MascotaService mascotaService
    ) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> listar() {
        return ResponseEntity.ok(
                mascotaService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> buscar(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                mascotaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Mascota> guardar(
            @RequestBody Mascota mascota
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mascotaService.guardar(mascota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
