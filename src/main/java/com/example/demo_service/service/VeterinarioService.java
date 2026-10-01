package com.example.demo_service.service;

import com.example.demo_service.model.Veterinario;
import com.example.demo_service.repository.VeterinarioRepository;
import com.example.demo_service.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(
            VeterinarioRepository veterinarioRepository
    ) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<Veterinario> listarTodos() {
        return veterinarioRepository.findAll();
    }

    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "No existe el veterinario con id: " + id
                        )
                );
    }

    public Veterinario guardar(Veterinario veterinario) {

        if (veterinarioRepository.existsByLicencia(
                veterinario.getLicencia()
        )) {
            throw new ReglaNegocioException(
                    "La licencia del veterinario ya existe"
            );
        }

        return veterinarioRepository.save(veterinario);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        veterinarioRepository.deleteById(id);
    }
}