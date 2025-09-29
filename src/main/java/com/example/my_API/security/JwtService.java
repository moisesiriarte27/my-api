package com.example.my_API.security;

import java.security.Key;
import java.util.Date;
import jakarta.annotation.PostConstruct; // <-- Se necesita esta importación
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    // Esta es la variable correcta que lee desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // --- CAMBIOS AQUÍ ---
    // 1. ELIMINAMOS la constante hardcodeada que causaba el problema
    // private static final String SECRET_KEY = "MiClaveSecretaMuySeguraParaJWT123456789";

    // 2. Declaramos la variable 'key' sin inicializarla aquí
    private Key key;

    // 3. Usamos @PostConstruct para inicializar la 'key' DESPUÉS de que 'secretKey' haya sido inyectada
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    // --- FIN DE LOS CAMBIOS ---


    // El resto de tu clase no necesita NINGÚN cambio, ya que usa la variable 'key' que ahora es correcta.
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getUsernameFromToken(String token) {
        return extractUsername(token);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

