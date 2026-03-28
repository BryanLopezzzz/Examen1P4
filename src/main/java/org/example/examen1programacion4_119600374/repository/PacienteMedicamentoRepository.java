package org.example.examen1programacion4_119600374.repository;

import org.example.examen1programacion4_119600374.model.Paciente;
import org.example.examen1programacion4_119600374.model.PacienteMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteMedicamentoRepository extends JpaRepository<PacienteMedicamento, Integer> {
    List<PacienteMedicamento> findByPaciente(Paciente paciente);
}