package com.example.demo_service.controller;

import com.example.demo_service.model.Veterinario;
import com.example.demo_service.service.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(
            VeterinarioService veterinarioService
    ) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    public ResponseEntity<List<Veterinario>> listar() {
        return ResponseEntity.ok(
                veterinarioService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> buscar(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                veterinarioService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Veterinario> guardar(
            @RequestBody Veterinario veterinario
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(veterinarioService.guardar(veterinario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        veterinarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
