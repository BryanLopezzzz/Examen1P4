package org.example.examen1programacion4_119600374.service;

import cr.ac.una.tiendapromociones.model.Categoria;
import org.example.examen1programacion4_119600374.model.Producto;
import cr.ac.una.tiendapromociones.repository.CategoriaRepository;
import org.example.examen1programacion4_119600374.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
        if (categoria != null) {
            return productoRepository.findByCategoria(categoria);
        }
        return new ArrayList<>();
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }
}
