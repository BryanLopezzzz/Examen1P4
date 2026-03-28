package org.example.examen1programacion4_119600374.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final PromocionRepository promocionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(ClienteRepository clienteRepository,
                      CategoriaRepository categoriaRepository,
                      ProductoRepository productoRepository,
                      PromocionRepository promocionRepository,
                      PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.promocionRepository = promocionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // === CLIENTES ===
        if (clienteRepository.count() == 0) {
            Cliente c1 = new Cliente();
            c1.setLogin("juan");
            c1.setPassword(passwordEncoder.encode("1234"));
            c1.setNombre("Juan");
            c1.setApellido("Perez");

            Cliente c2 = new Cliente();
            c2.setLogin("maria");
            c2.setPassword(passwordEncoder.encode("1234"));
            c2.setNombre("Maria");
            c2.setApellido("Lopez");

            clienteRepository.saveAll(List.of(c1, c2));
            System.out.println("Clientes insertados.");
        }

        // === CATEGORÍAS ===
        if (categoriaRepository.count() == 0) {
            Categoria lacteos    = new Categoria("Lácteos");
            Categoria bebidas    = new Categoria("Bebidas");
            Categoria snacks     = new Categoria("Snacks");

            categoriaRepository.saveAll(List.of(lacteos, bebidas, snacks));

            // === PRODUCTOS ===
            Producto leche   = new Producto("Leche Dos Pinos 1L",     850.0, lacteos);
            Producto yogurt  = new Producto("Yogurt Natural",         650.0, lacteos);
            Producto jugo    = new Producto("Jugo Del Valle 500ml",   750.0, bebidas);
            Producto agua    = new Producto("Agua Cristal 600ml",     400.0, bebidas);
            Producto papas   = new Producto("Papas Fritas Pringles",  1200.0, snacks);
            Producto galleta = new Producto("Galletas Oreo",          900.0, snacks);

            productoRepository.saveAll(List.of(leche, yogurt, jugo, agua, papas, galleta));

            // === PROMOCIONES (2x3) ===
            // Compra 2 leches → recibe 1 de regalo (total 3)
            Promocion promo1 = new Promocion("2x3 Leche", 2, 1, leche);
            // Compra 3 jugos → recibe 1 de regalo (total 4)
            Promocion promo2 = new Promocion("3x4 Jugo",  3, 1, jugo);

            promocionRepository.saveAll(List.of(promo1, promo2));

            System.out.println("Categorías, productos y promociones insertados.");
        }
    }
}