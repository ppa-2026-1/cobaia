package com.example.demo.model.dto;

import java.util.List;

import com.example.demo.repository.entity.Profile;
import com.example.demo.transversal.constraints.AtLeastOne;

import jakarta.validation.constraints.NotBlank;

public record NewUserDTO( // "bolsa de dados" -> representa o JSON
    @NotBlank(message = "O nome é obrigatório")
    String name,
    String handle,
    String email,
    String password,
    String company,
    Profile.AccountType type,
    @AtLeastOne(message = "Deve ter pelo menos um role definido")
    List<String> roles
) {}
