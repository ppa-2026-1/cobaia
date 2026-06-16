package com.example.demo.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "workstations")
public class Workstation {

    public boolean isOccupied() { // propriedade calculada (UAP)
        return this.user != null;
    }

    public boolean isFree() {
        return this.user == null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String specs;

    @ManyToOne
    @JoinColumn(name = "island_id")
    private Island island;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime updatedAt;

    public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
      return createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
      return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    @Override
    public String toString() {
        return "Workstation [createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + ", id=" + id
            + ", specs=" + specs    
            + ", island=" + island
            + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Workstation other = (Workstation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public class WorkstationNotAvailableException
            extends RuntimeException {}

    public void assign(User user) {
        if (this.user == null) this.user = user;
        else throw new WorkstationNotAvailableException();
    };
}


