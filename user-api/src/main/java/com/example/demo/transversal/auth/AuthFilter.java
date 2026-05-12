package com.example.demo.transversal.auth;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Java Servlet
@Component
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        var authString = request.getHeader("Authorization");

        if (authString != null && authString.startsWith("Bearer ")) {
            var token = authString.substring(7); // akldlajksdkasjdl

            var userHandle = authService.validateToken(token);

            if (userHandle != null) {
                
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userHandle, null, List.of()));

            }
        }

        filterChain.doFilter(request, response);
    }
    
}
