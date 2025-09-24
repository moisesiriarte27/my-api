package com.example.my_API.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_API.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    
    Optional<Usuario> findByCorreo(String correo);

}
