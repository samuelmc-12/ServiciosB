package com.example.demo_service.repository;

import com.example.demo_service.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CitaRepository
        extends JpaRepository<Cita, Long> {

    boolean existsByVeterinarioIdAndFechaHora(
            Long veterinarioId,
            LocalDateTime fechaHora
    );

    boolean existsByMascotaIdAndFechaHora(
            Long mascotaId,
            LocalDateTime fechaHora
    );
}