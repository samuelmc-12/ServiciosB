package com.example.demo_service.repository;

import com.example.demo_service.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository
        extends JpaRepository<Mascota, Long> {

    List<Mascota> findByPropietarioId(Long propietarioId);

    boolean existsByNombreAndPropietarioId(
            String nombre,
            Long propietarioId
    );
}
