package com.example.my_API.Controller;

import com.example.my_API.security.JwtService;
import com.example.my_API.security.TokenBlacklist;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklist tokenBlacklist;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                          TokenBlacklist tokenBlacklist) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenBlacklist = tokenBlacklist;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Autentica al usuario con sus credenciales
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getClave())
            );
            
            // 2. Si la autenticación es exitosa, genera un token DINÁMICO
            String token = jwtService.generateToken(loginRequest.getCorreo());
            
            // 3. Devuelve el token en un objeto JSON estándar
            return ResponseEntity.ok(java.util.Collections.singletonMap("token", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token faltante o inválido");
        }

        String token = authHeader.substring(7);
        tokenBlacklist.revokeToken(token);
        return ResponseEntity.ok("Token revocado correctamente");
    }
}
