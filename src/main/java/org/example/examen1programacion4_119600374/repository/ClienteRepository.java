package org.example.examen1programacion4_119600374.repository;

import cr.ac.una.tiendapromociones.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByLogin(String login);
}
