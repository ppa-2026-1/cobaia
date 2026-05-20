package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.model.dto.NewUserDTO;
import com.example.demo.model.port.IUserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.entity.Profile;
import com.example.demo.repository.entity.Role;
import com.example.demo.repository.entity.User;

import jakarta.validation.Valid;

@Validated // spring, por favor, processe essa classe na fila de validação
@Service
public class UserService { // MÓDULO DE ALTO NÍVEL

    // DEPENDÊNCIAS CONCRETAS DE NÍVEL MENOR
    // private final UserRepository userRepository;
    // DEPENDÊNCIA ABSTRATA NO MESMO NÍVEL
    // REPOSITORY AGNOSTIC (IMPLEMENTATION AGNOSTIC)
    private final IUserRepository userRepository;

    // FIXME: TAMBÉM DEVE SER ABSTRAÍDO: VIRAR UM PORT E TER UM ADAPTER
    private final RoleRepository roleRepository;
    // DEPENDÊNCIA CONCRETA -> DEPENDÊNCIA ABSTRATA
    // private BCryptPasswordEncoder passwordEncoder;
    private PasswordEncoder passwordEncoder;

    private Set<String> defaultRoles;


    public UserService( // DEPENDÊNCIAS
            PasswordEncoder passwordEncoder, // É ABSTRATO
            IUserRepository userRepository, // É ABSTRATO
            RoleRepository roleRepository,
            @Value("${app.user.default.roles}") Set<String> defaultRoles) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;   
        this.passwordEncoder = passwordEncoder;
        this.defaultRoles = defaultRoles;
    }
    
                       // este objeto deve ser válido
    public void registerNewUser(@Valid NewUserDTO newUser) {
        // TRANSACTION SCRIPT
        // MÉTODOZÃO QUE FAZ TUDO

        // ALTERNATIVAS:
        // - TABLE MODULE (CHAMAR UMA STORED PROCEDURE "PROC") -- ABORDAGEM ANTIGA
        //   COMUM EM BASES DE DADOS PROPRIETÁRIA E COM SUPORTE (ORACLE, IBM DB2, MS SQL SERVER)
        // - DOMAIN MODEL (VERSÃO "VERDADEIRAMENTE" POO)
        //   DDD - DOMAIN-DRIVEN DESIGN (PROJETO GUIADO PELO DOMÍNIO)

        userRepository.findByHandle(newUser.handle())
            .ifPresent(user -> {
                throw new IllegalArgumentException("Usuário com o nome " + newUser.handle() + " já existe");
            });

        User user = new User();
        
        user.setEmail(newUser.email());
        user.setHandle(newUser.handle() != null ? newUser.handle() : generateHandle(newUser.email()));
        user.setPassword(passwordEncoder.encode(newUser.password()));
        
        Set<Role> roles = new HashSet<>();
        
        roles.addAll(roleRepository.findByNameIn(defaultRoles));

        Set<Role> additionalRoles = roleRepository.findByNameIn(newUser.roles());
        if (additionalRoles.size() != newUser.roles().size()) {
            throw new IllegalArgumentException("Alguns papéis não existem");
        }

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("O usuário deve ter pelo menos um papel");
        }

        user.setRoles(roles);

        Profile profile = new Profile();
        
        profile.setName(newUser.name());
        profile.setCompany(newUser.company());
        profile.setType(newUser.type() != null ? newUser.type() : Profile.AccountType.FREE);

        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user); 
    }


    private String generateHandle(String email) {
        String[] parts = email.split("@");
        String handle = parts[0];
        int i = 1;
        while (userRepository.existsByHandle(handle)) {
            handle = parts[0] + i++;
        }
        return handle;
    }
}
