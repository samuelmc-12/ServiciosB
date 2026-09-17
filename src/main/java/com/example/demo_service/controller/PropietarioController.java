package com.example.demo_service.controller;

import com.example.demo_service.model.Propietario;
import com.example.demo_service.service.PropietarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(
            PropietarioService propietarioService
    ) {
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public ResponseEntity<List<Propietario>> listar() {
        return ResponseEntity.ok(
                propietarioService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propietario> buscar(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                propietarioService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Propietario> guardar(
            @RequestBody Propietario propietario
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(propietarioService.guardar(propietario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        propietarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}