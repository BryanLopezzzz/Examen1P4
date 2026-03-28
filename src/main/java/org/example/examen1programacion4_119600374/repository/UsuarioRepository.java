package org.example.examen1programacion4_119600374.repository;

import org.example.examen1programacion4_119600374.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}