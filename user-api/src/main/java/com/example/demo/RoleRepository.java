package com.example.demo;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
public class RoleRepository {

    private final EntityManager em;

    public RoleRepository(EntityManager em) {
        this.em = em;
    }

    public Role findByName(String name) {
        return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Set<Role> findByNameIn(Collection<String> names) {
        return em.createQuery("SELECT r FROM Role r WHERE r.name IN :names", Role.class)
                .setParameter("names", names)
                .getResultStream()
                .collect(toSet());
    }

    public boolean existsByName(String name) {
        return em.createQuery("SELECT COUNT(r) FROM Role r WHERE r.name = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult() > 0L;
    }
    
}
