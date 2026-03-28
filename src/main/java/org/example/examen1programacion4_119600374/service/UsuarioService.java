package org.example.examen1programacion4_119600374.service;

import org.example.examen1programacion4_119600374.model.Usuario;
import org.example.examen1programacion4_119600374.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findById(uname).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + uname));
        return User.builder().username(usuario.getId()).password(usuario.getClave()).roles(usuario.getRol()).build();
    }
}