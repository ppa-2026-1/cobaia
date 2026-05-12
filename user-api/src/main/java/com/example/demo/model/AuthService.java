package com.example.demo.model;

import org.springframework.stereotype.Service;

@Service
public class AuthService { // Singleton
    // Redis in-memory database (key-value database)
    // mapa de tokens
    // Map<String, Auth> tokens
    // TODO: PostgreSQL na veia

    public String validateToken(String token) {
        // TODO: verificar se o token existe
        // TODO: o token não está expirado
        // retornar o handle do usuário associado ao token
        return "userhandle";
    }
    
}
