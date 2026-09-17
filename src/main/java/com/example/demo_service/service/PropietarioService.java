package com.example.demo_service.service;

import com.example.demo_service.exception.ReglaNegocioException;
import com.example.demo_service.model.Propietario;
import com.example.demo_service.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioService(
            PropietarioRepository propietarioRepository
    ) {
        this.propietarioRepository = propietarioRepository;
    }

    public List<Propietario> listarTodos() {
        return propietarioRepository.findAll();
    }

    public Propietario buscarPorId(Long id) {
        return propietarioRepository.findById(id)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "No existe el propietario con id: " + id
                        )
                );
    }

    public Propietario guardar(Propietario propietario) {

        if (propietarioRepository.existsByEmail(
                propietario.getEmail()
        )) {
            throw new ReglaNegocioException(
                    "El email ya está registrado"
            );
        }

        if (propietarioRepository.existsByTelefono(
                propietario.getTelefono()
        )) {
            throw new ReglaNegocioException(
                    "El teléfono ya está registrado"
            );
        }

        return propietarioRepository.save(propietario);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        propietarioRepository.deleteById(id);
    }
}