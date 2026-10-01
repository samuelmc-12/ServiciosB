package com.example.demo_service.service;

import com.example.demo_service.model.Cita;
import com.example.demo_service.model.enums.EstadoCita;
import com.example.demo_service.repository.CitaRepository;
import com.example.demo_service.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaService mascotaService;
    private final VeterinarioService veterinarioService;

    public CitaService(
            CitaRepository citaRepository,
            MascotaService mascotaService,
            VeterinarioService veterinarioService
    ) {
        this.citaRepository = citaRepository;
        this.mascotaService = mascotaService;
        this.veterinarioService = veterinarioService;
    }

    public List<Cita> listarTodos() {
        return citaRepository.findAll();
    }

    public Cita buscarPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "No existe la cita con id: " + id
                        )
                );
    }

    public Cita guardar(Cita cita) {

        if (cita.getFechaHora() == null) {
            throw new ReglaNegocioException(
                    "La fecha y hora de la cita son obligatorias"
            );
        }

        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new ReglaNegocioException(
                    "La cita debe programarse para una fecha futura"
            );
        }

        if (cita.getMascota() == null ||
                cita.getMascota().getId() == null) {

            throw new ReglaNegocioException(
                    "Debe indicar la mascota de la cita"
            );
        }

        if (cita.getVeterinario() == null ||
                cita.getVeterinario().getId() == null) {

            throw new ReglaNegocioException(
                    "Debe indicar el veterinario de la cita"
            );
        }

        Long mascotaId = cita.getMascota().getId();
        Long veterinarioId = cita.getVeterinario().getId();

        mascotaService.buscarPorId(mascotaId);
        veterinarioService.buscarPorId(veterinarioId);

        if (citaRepository.existsByVeterinarioIdAndFechaHora(
                veterinarioId,
                cita.getFechaHora()
        )) {
            throw new ReglaNegocioException(
                    "El veterinario ya tiene una cita en ese horario"
            );
        }

        if (citaRepository.existsByMascotaIdAndFechaHora(
                mascotaId,
                cita.getFechaHora()
        )) {
            throw new ReglaNegocioException(
                    "La mascota ya tiene una cita en ese horario"
            );
        }

        if (cita.getEstado() == null) {
            cita.setEstado(EstadoCita.PROGRAMADA);
        }

        return citaRepository.save(cita);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        citaRepository.deleteById(id);
    }
}