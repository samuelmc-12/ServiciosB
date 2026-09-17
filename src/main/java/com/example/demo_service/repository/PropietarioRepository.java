package com.example.demo_service.repository;

import com.example.demo_service.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropietarioRepository
        extends JpaRepository<Propietario, Long> {

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);
}