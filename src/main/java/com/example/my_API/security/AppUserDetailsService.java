package com.example.my_API.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.my_API.model.Usuario;
import com.example.my_API.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AppUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // --- LÍNEA DE DEBUG 1 ---
        System.out.println("--- INTENTANDO BUSCAR USUARIO CON CORREO: " + correo + " ---");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            // --- LÍNEA DE DEBUG 2 ---
            System.out.println("--- USUARIO NO ENCONTRADO EN LA BASE DE DATOS ---");
            throw new UsernameNotFoundException("Usuario no encontrado: " + correo);
        }

        Usuario usuario = usuarioOpt.get();
        // --- LÍNEA DE DEBUG 3 ---
        System.out.println("--- USUARIO ENCONTRADO: " + usuario.getCorreo() + " ---");

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getClave())
                .roles("USER") // Puedes mapear tus permisos si quieres
                .build();
    }
}

