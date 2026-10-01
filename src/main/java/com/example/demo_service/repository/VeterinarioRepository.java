package com.example.demo_service.repository;

import com.example.demo_service.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository
        extends JpaRepository<Veterinario, Long> {

    boolean existsByLicencia(String licencia);
}