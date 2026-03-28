package org.example.examen1programacion4_119600374.repository;

import org.example.examen1programacion4_119600374.model.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmaciaRepository extends JpaRepository<Farmacia, String> {
    Farmacia findByUsuario(String usuarioId);
}