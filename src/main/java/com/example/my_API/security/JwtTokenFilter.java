package com.example.my_API.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenBlacklist tokenBlacklist;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtService jwtService, TokenBlacklist tokenBlacklist, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.tokenBlacklist = tokenBlacklist;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request URL: " + request.getRequestURL());
        
        String header = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println("Token extraído: " + token.substring(0, Math.min(token.length(), 20)) + "...");

            boolean isTokenValid = jwtService.isTokenValid(token);
            boolean isTokenRevoked = tokenBlacklist.isTokenRevoked(token);
            
            System.out.println("Token válido: " + isTokenValid);
            System.out.println("Token revocado: " + isTokenRevoked);

            if (!isTokenValid || isTokenRevoked) {
                System.out.println("Token inválido o revocado - enviando 401");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o revocado");
                return;
            }

            String correo = jwtService.getUsernameFromToken(token);
            System.out.println("Correo extraído del token: " + correo);
            
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(correo);
                System.out.println("Usuario cargado: " + userDetails.getUsername());
                System.out.println("Authorities: " + userDetails.getAuthorities());
                
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Autenticación establecida correctamente");
                
            } catch (Exception e) {
                System.out.println("Error cargando usuario: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no encontrado");
                return;
            }
        } else {
            System.out.println("No hay header Authorization o no empieza con 'Bearer '");
        }

        System.out.println("=== FIN JWT FILTER DEBUG ===");
        filterChain.doFilter(request, response);
    }
}