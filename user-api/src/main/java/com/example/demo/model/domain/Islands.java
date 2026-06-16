package com.example.demo.model.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.demo.repository.entity.Island;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.Workstation;

public final class Islands { // lógica para várias ilhas, partirá daqui!

    public enum AllocationStrategy {
        FIRST_AVAILABLE, // PRIMEIRO DISPONÍVEL
        MOST_AVAILABLE, // COM MAIS ESTAÇÕES DISPONÍVEIS
        LEAST_AVAILABLE, // COM MENOS ESTAÇÕES DISPONÍVEIS
        PRIORIZE_LARGERS_ISLANDS // PRIORIZE ILHAS MAIORES (CIRCULAR > RECTANGULAR > SQUARED > TRIANGULAR > PAIRED)
    }

    // Technical Debt
    private final Collection<Island> islands; // estado

    private Islands(Collection<Island> islands) {
        this.islands = islands;
    }

    public static Islands of(Collection<Island> islands) {
        return new Islands(islands);
    }

    // comportamento
    public Workstation assignUser(@NonNull  User user, 
                                  @Nullable AllocationStrategy strategy) { 
        // UAP (Uniform Access Principle): 
        // o acesso de propriedades é o mesmo para
        // informações armazenadas ou computadas

        // Law of Demeter
        // em vez de: setor.getFuncionarios().find(23).getUser().login(password);
        // isso: setor.login(23, password);

        if (strategy == null) strategy = AllocationStrategy.MOST_AVAILABLE;
        
        if (strategy == AllocationStrategy.MOST_AVAILABLE) {
            for (int slots = 1; slots < Island.Disposition.CIRCULAR.slots; slots++) {
                final int positions = slots;
                final var island = findIslandWithOccupation(positions);
                if (island.isPresent()) {
                    return island.get().assignUser(user);
                }
            }
        } else {
            throw new UnsupportedOperationException(
                "Estratégia %s não implementada".formatted(strategy));
        }

        // fallback
        throw new IllegalStateException("Não há estações de trabalho disponíveis");
    }
    // fiwo() // mnemônico
    public Optional<Island> findIslandWithOccupation(int positions) {
        return this.islands.stream()
                    .filter(i -> i.getOcuppation() == positions)
                    .findFirst();
    }
}

