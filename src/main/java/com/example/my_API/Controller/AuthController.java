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
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getClave())
            );
            String token = jwtService.generateToken(loginRequest.getCorreo());
            
            // --- ÚNICO CAMBIO REALIZADO PARA LA PRUEBA ---
            // En lugar de devolver solo el token, ahora devolvemos un objeto con una señal de versión.
            java.util.Map<String, String> response = new java.util.HashMap<>();
            response.put("token", token);
            response.put("version", "DEPLOY_PRUEBA_2.0"); // <-- La señal para verificar el despliegue
            return ResponseEntity.ok(response);
            // --- FIN DEL CAMBIO ---

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
