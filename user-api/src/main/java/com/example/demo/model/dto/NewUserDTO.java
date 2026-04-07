package com.example.demo.model.dto;

import java.util.List;

import com.example.demo.model.validation.UniqueUser;
import com.example.demo.repository.entity.Profile;
import com.example.demo.transversal.validation.AtLeastOne;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Validação Declarativa

public record NewUserDTO( // "bolsa de dados" -> representa o JSON
    
    @Size(min = 5, max = 255, message = "O nome deve ter entre 5 e 255 caracteres")
    String name,
    
    String handle,
    
    @Email(message = "O email não é válido")
    @UniqueUser
    String email,
    
    @NotBlank(message = "A senha não pode ser vazia")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$",
        message = "A senha deve ter pelo menos 8 caracteres e conter pelo menos uma letra e um número"
    )
    String password,
    
    String company,
    Profile.AccountType type,

    @AtLeastOne(message = "Deve ter pelo menos um role atribuído")
    List<String> roles
) {}
