package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.User;

// não deve importar classes de camadas superiores
// import com.example.demo.controller.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class UserRepository {

    private final EntityManager em;
    // Camadas inferiores não devem depender
    // de camadas superiores.
    // private final UserController userController;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<User> findByEmail(String email) {
        return em.createQuery("FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    public Optional<User> findByHandle(String handle) {
        return em.createQuery("FROM User u WHERE u.handle = :handle", User.class)
                .setParameter("handle", handle)
                .getResultStream()
                .findFirst();
    }

    public boolean existsByHandle(String handle) {
        return em.createQuery("SELECT COUNT(u) FROM User u WHERE u.handle = :handle", Long.class)
                .setParameter("handle", handle)
                .getSingleResult() > 0L;
    }

    public List<User> findAll() {
        return em.createQuery("FROM User u", User.class)
                .getResultList();
    }

    @Transactional
    public void save(User user) {
        if (user.getId() == null) {
            em.persist(user); // insert
        } else {
            em.merge(user); // update
        }
    }
}
