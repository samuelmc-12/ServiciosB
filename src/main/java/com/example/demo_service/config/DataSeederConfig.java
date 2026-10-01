package com.example.demo_service.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo_service.model.Cita;
import com.example.demo_service.model.Mascota;
import com.example.demo_service.model.Propietario;
import com.example.demo_service.model.Veterinario;
import com.example.demo_service.model.enums.EstadoCita;
import com.example.demo_service.repository.CitaRepository;
import com.example.demo_service.repository.MascotaRepository;
import com.example.demo_service.repository.PropietarioRepository;
import com.example.demo_service.repository.VeterinarioRepository;

@Configuration
public class DataSeederConfig {

    @Bean
    public CommandLineRunner sembrarDatos(
            PropietarioRepository propietarioRepository,
            MascotaRepository mascotaRepository,
            VeterinarioRepository veterinarioRepository,
            CitaRepository citaRepository) {
        
        return args -> {
            // Solo sembramos si la tabla de citas está vacía
            if (citaRepository.count() == 0) {

                // 1. Crear propietarios
                Propietario prop1 = new Propietario(
                    null,
                    "Juan Pérez",
                    "555-1234",
                    "juan@example.com"
                );

                Propietario prop2 = new Propietario(
                    null,
                    "María Gómez",
                    "555-5678",
                    "maria@example.com"
                );

                propietarioRepository.save(prop1);
                propietarioRepository.save(prop2);

                // 2. Crear veterinarios
                Veterinario vet1 = new Veterinario(
                    null,
                    "Dr. López",
                    "Cirugía",
                    "VET-001"
                );

                Veterinario vet2 = new Veterinario(
                    null,
                    "Dra. Martínez",
                    "Dermatología",
                    "VET-002"
                );

                veterinarioRepository.save(vet1);
                veterinarioRepository.save(vet2);

                // 3. Crear mascotas
                Mascota mascota1 = new Mascota(
                    null,
                    "Max",
                    "Canino",
                    "Golden Retriever",
                    LocalDate.of(2020, 5, 15),
                    prop1
                );

                Mascota mascota2 = new Mascota(
                    null,
                    "Luna",
                    "Felino",
                    "Siamés",
                    LocalDate.of(2021, 3, 20),
                    prop2
                );

                mascotaRepository.save(mascota1);
                mascotaRepository.save(mascota2);

                // 4. Crear citas
                Cita cita1 = new Cita(
                    null,
                    LocalDateTime.of(2026, 10, 15, 10, 30),
                    "Revisión general",
                    EstadoCita.CONFIRMADA,
                    mascota1,
                    vet1
                );

                Cita cita2 = new Cita(
                    null,
                    LocalDateTime.of(2026, 10, 16, 14, 0),
                    "Vacunación",
                    EstadoCita.PENDIENTE,
                    mascota2,
                    vet2
                );

                Cita cita3 = new Cita(
                    null,
                    LocalDateTime.of(2026, 10, 17, 11, 0),
                    "Limpieza dental",
                    EstadoCita.CONFIRMADA,
                    mascota1,
                    vet2
                );

                citaRepository.saveAll(List.of(cita1, cita2, cita3));

                System.out.println(">>> SEED EXITOSA: Se han sembrado 2 propietarios, 2 veterinarios, 2 mascotas y 3 citas.");
            }
        };
    }
}