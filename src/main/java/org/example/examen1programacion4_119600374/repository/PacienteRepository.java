package org.example.examen1programacion4_119600374.repository;

import org.example.examen1programacion4_119600374.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, String> {
}