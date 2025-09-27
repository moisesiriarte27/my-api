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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) { // <-- 1. Usa @RequestBody y el DTO
        try {
            Authentication authentication = authenticationManager.authenticate(
                    // 2. Obtiene los datos del objeto loginRequest
                    new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getClave())
            );
            // 3. Genera el token usando el correo del objeto
            String token = jwtService.generateToken(loginRequest.getCorreo());
            
            // 4. Devuelve el token en un objeto JSON para que sea más fácil de manejar en el frontend
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

