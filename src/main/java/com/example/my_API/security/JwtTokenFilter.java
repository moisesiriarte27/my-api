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
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            if (jwtService.isTokenValid(token) && !tokenBlacklist.isTokenRevoked(token)) {
                String correo = jwtService.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(correo);
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}