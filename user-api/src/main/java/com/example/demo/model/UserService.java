package com.example.demo.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.model.domain.Islands;
import com.example.demo.model.domain.Islands.AllocationStrategy;
import com.example.demo.model.dto.NewUserDTO;
import com.example.demo.model.dto.NotificationDTO;
import com.example.demo.model.port.IIslandRepository;
import com.example.demo.model.port.IUserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.entity.Island;
import com.example.demo.repository.entity.Profile;
import com.example.demo.repository.entity.Role;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.Workstation;

import jakarta.validation.Valid;

@Validated // spring, por favor, processe essa classe na fila de validação
@Service
public class UserService { // MÓDULO DE ALTO NÍVEL

    // DEPENDÊNCIAS CONCRETAS DE NÍVEL MENOR
    // private final UserRepository userRepository;
    // DEPENDÊNCIA ABSTRATA NO MESMO NÍVEL
    // REPOSITORY AGNOSTIC (IMPLEMENTATION AGNOSTIC)
    private final IUserRepository userRepository;

    private final IIslandRepository islandRepository;

    // FIXME: TAMBÉM DEVE SER ABSTRAÍDO: VIRAR UM PORT E TER UM ADAPTER
    private final RoleRepository roleRepository;
    // DEPENDÊNCIA CONCRETA -> DEPENDÊNCIA ABSTRATA
    // private BCryptPasswordEncoder passwordEncoder;
    private PasswordEncoder passwordEncoder;

    private Set<String> defaultRoles;

    private NotificationFacade notificationService;

    public UserService( // DEPENDÊNCIAS
            PasswordEncoder passwordEncoder, // É ABSTRATO
            IUserRepository userRepository, // É ABSTRATO
            IIslandRepository islandRepository, // É ABSTRATO
            RoleRepository roleRepository,
            NotificationFacade notification,
            @Value("${app.user.default.roles}") Set<String> defaultRoles) {

        this.userRepository = userRepository;
        this.islandRepository = islandRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultRoles = defaultRoles;
        this.notificationService = notification;
    }

    

    public Long assignWorkstationToUser(@NonNull String userHandle, AllocationStrategy strategy) {
        // TRANSACTION SCRIPT: padrão de arquitetura para organização da lógica
        // toda a lógica é roteirizada no método caso de uso
        
        
        // APPLICATION LAYER (não é mais um domain layer)
        // ------------------------------------------------------
        final User user = userRepository.findByHandle(userHandle)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        final Islands islands = Islands.of( // factory method
            islandRepository.findIslandWithAvailableWorkstations()
        );

        final Workstation workstation = 
            islands.assignUser(user, strategy);

        islandRepository.save(workstation.getIsland());

        return workstation.getId();
        // ------------------------------------------------------

    }

    // este objeto deve ser válido
    public void registerNewUser(@Valid NewUserDTO newUser) {
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

        notificationService.sendNotification(
            new NotificationDTO(user.getEmail(),
                "Sua conta foi criada",
                "Parabéns %s, sua conta foi criada com sucesso. Bem-vindo a bordo do nosso espetacular serviço de usuários. lorem ipsum dolor nocet".formatted(user.getProfile().getName()),
                List.of("mail"))
        );
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
