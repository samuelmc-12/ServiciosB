package com.example.demo_service.service;

import com.example.demo_service.model.Mascota;
import com.example.demo_service.repository.MascotaRepository;
import com.example.demo_service.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final PropietarioService propietarioService;

    public MascotaService(
            MascotaRepository mascotaRepository,
            PropietarioService propietarioService
    ) {
        this.mascotaRepository = mascotaRepository;
        this.propietarioService = propietarioService;
    }

    public List<Mascota> listarTodos() {
        return mascotaRepository.findAll();
    }

    public Mascota buscarPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "No existe la mascota con id: " + id
                        )
                );
    }

    public Mascota guardar(Mascota mascota) {

        if (mascota.getFechaNacimiento() == null) {
            throw new ReglaNegocioException(
                    "La fecha de nacimiento es obligatoria"
            );
        }

        if (mascota.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new ReglaNegocioException(
                    "La fecha de nacimiento no puede ser futura"
            );
        }

        if (mascota.getPropietario() == null ||
                mascota.getPropietario().getId() == null) {

            throw new ReglaNegocioException(
                    "Debe indicar el propietario de la mascota"
            );
        }

        Long propietarioId = mascota.getPropietario().getId();

        propietarioService.buscarPorId(propietarioId);

        if (mascotaRepository.existsByNombreAndPropietarioId(
                mascota.getNombre(),
                propietarioId
        )) {
            throw new ReglaNegocioException(
                    "El propietario ya tiene una mascota con ese nombre"
            );
        }

        return mascotaRepository.save(mascota);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        mascotaRepository.deleteById(id);
    }
}
