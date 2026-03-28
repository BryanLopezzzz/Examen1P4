package org.example.examen1programacion4_119600374.repository;

import cr.ac.una.tiendapromociones.model.Producto;
import cr.ac.una.tiendapromociones.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(Categoria categoria);
}
