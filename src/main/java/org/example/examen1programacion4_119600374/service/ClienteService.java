package org.example.examen1programacion4_119600374.service;

import cr.ac.una.tiendapromociones.model.Cliente;
import cr.ac.una.tiendapromociones.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente buscarPorLogin(String login) {
        return clienteRepository.findByLogin(login).orElse(null);
    }
}