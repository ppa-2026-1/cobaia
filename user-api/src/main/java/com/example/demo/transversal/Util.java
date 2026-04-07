package com.example.demo.transversal;

// Utility Static Class
// Estudo de Caso -- don't
public class Util {
    private Util(){}

    public static boolean validaSenha(String senha) {
        return senha.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");
    }
}
