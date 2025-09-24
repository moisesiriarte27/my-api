package com.example.my_API.security.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeneradorContraseña {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String claveEncriptada = encoder.encode("admin");
        System.out.println("Clave encriptada: " + claveEncriptada);
    }

}
