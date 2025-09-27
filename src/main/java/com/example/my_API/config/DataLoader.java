package com.example.my_API.config; // Asegúrate de que el paquete sea el correcto

import com.example.my_API.model.Usuario; // Asegúrate de que la ruta a tu clase Usuario sea correcta
import com.example.my_API.repository.UsuarioRepository; // Asegúrate de que la ruta a tu Repositorio sea correcta
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                System.out.println(">>> Creando usuario administrador inicial...");

                Usuario admin = new Usuario();
                admin.setCorreo("admin@app.com"); // Reemplaza con tu email
                admin.setNombre("Administrador");
                admin.setClave(passwordEncoder.encode("admin")); // La contraseña será "admin"

                usuarioRepository.save(admin);

                System.out.println(">>> Usuario administrador creado con éxito.");
            }
        };
    }
}