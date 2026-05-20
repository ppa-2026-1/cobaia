package com.example.demo.model.port;

import java.util.List;
import java.util.Optional;

import com.example.demo.repository.entity.User;

public interface IUserRepository {
    // APENAS OS MÉTODOS ESTRITAMENTE NECESSÁRIOS
    // METÓDOS SÃO ADICIONADOS SOB DEMANDA, APENAS
    // SE USADOS PELO DOMÍNIO

    Optional<User> findByHandle(String handle);

    Optional<User> findByEmail(String email);

    boolean existsByHandle(String handle);

    List<User> findAll();

    void save(User user);

    // void deactivate(User user);
}
