package com.example.demo.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.model.dto.NewUserDTO;
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

    public UserService( // DEPENDÊNCIAS
            PasswordEncoder passwordEncoder, // É ABSTRATO
            IUserRepository userRepository, // É ABSTRATO
            IIslandRepository islandRepository, // É ABSTRATO
            RoleRepository roleRepository,
            @Value("${app.user.default.roles}") Set<String> defaultRoles) {

        this.userRepository = userRepository;
        this.islandRepository = islandRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultRoles = defaultRoles;
    }

    public enum AllocationStrategy {
        FIRST_AVAILABLE, // PRIMEIRO DISPONÍVEL
        MOST_AVAILABLE, // COM MAIS ESTAÇÕES DISPONÍVEIS
        LEAST_AVAILABLE, // COM MENOS ESTAÇÕES DISPONÍVEIS
        PRIORIZE_LARGERS_ISLANDS // PRIORIZE ILHAS MAIORES (CIRCULAR > RECTANGULAR > SQUARED > TRIANGULAR > PAIRED)
    }

    public Long assignWorkstationToUser(@NonNull String userHandle, AllocationStrategy strategy) {

        if (strategy == null) {
            strategy = AllocationStrategy.MOST_AVAILABLE;
        }

        final var user = userRepository.findByHandle(userHandle)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        final List<Island> islands = islandRepository.findIslandWithAvailableWorkstations();

        Island islandToAssignTo = null;
        Workstation freeWorkstation = null;

        switch (strategy) {
            case FIRST_AVAILABLE:
                throw new UnsupportedOperationException("Estratégia FIRST_AVAILABLE ainda não implementada");
            case MOST_AVAILABLE:
                // também é possivel classificar as ilhas por número de estações de trabalho disponíveis e escolher a primeira
                for (int slots = 1; slots < Island.Disposition.CIRCULAR.slots; slots++) {
                    final int positions = slots;
                    var possibleIsland = islands.stream()
                            .filter(i -> i.getWorkstations().stream()
                                    .map(Workstation::getUser)
                                    .filter(Objects::nonNull)
                                    .count() == positions)
                            .findFirst();
                    if (possibleIsland.isPresent()) {
                        islandToAssignTo = possibleIsland.get();
                        break;
                    }
                }

                if (islandToAssignTo == null) {
                    islandToAssignTo = islands.iterator().next();
                }

                for (var w : islandToAssignTo.getWorkstations()) {
                    if (w.getUser() == null) {
                        freeWorkstation = w;
                        break;
                    }
                }

                break;
            case LEAST_AVAILABLE:
                throw new UnsupportedOperationException("Estratégia LEAST_AVAILABLE ainda não implementada");
            default:
                break;
        }

        if (freeWorkstation == null) {
            throw new IllegalStateException("Não há estações de trabalho disponíveis na ilha selecionada");
        }

        freeWorkstation.setUser(user);

        islandRepository.save(islandToAssignTo);

        return freeWorkstation.getId();

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
