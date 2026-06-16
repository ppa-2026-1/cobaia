package com.example.demo.repository.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "islands")
public class Island {

    public enum Disposition {
        PAIRED(2),      // 0
        TRIANGULAR(3),  // 1
        SQUARED(4),     // 2
        RECTANGULAR(6), // 3
        CIRCULAR(8);    // 4

        public final int slots;

        private Disposition(int slots) { this.slots = slots; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Disposition disposition;

    @OneToMany(mappedBy = "island")
    private Set<Workstation> workstations = new HashSet<>();

    
    
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


    public void removeWorkstations(Predicate<Workstation> predicate) {
        this.workstations.removeIf(predicate);
    }

    public Set<Workstation> getWorkstations() {
        return workstations;
    }

    public void setWorkstations(Set<Workstation> workstations) {
        this.workstations = workstations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Disposition getDisposition() {
        return disposition;
    }

    public void setDisposition(Disposition disposition) {
        this.disposition = disposition;
    }



    @Override
    public String toString() {
        return "Island [id=" + id 
        + ", description=" + description 
        + ", disposition=" + disposition 
        + ", createdAt=" + createdAt 
        + ", updatedAt=" + updatedAt 
        + "]";
    }

    public Optional<Workstation> firstAvailableWorkstation() {
        return this.workstations.stream()
            .filter(w -> w.getUser() == null)
            .findFirst();
    }

    public Optional<Workstation> getFirstAvailableWorkstation() {
        return this.getWorkstations().stream()
                .filter(ws -> ws.getUser() == null)
                .findFirst();
    }
    // estação de trabalho tem usuário?
    // workstation está ocupada?
    public long getOcuppation() {
        return this.getWorkstations().stream()
                    .filter(Workstation::isOccupied) // línguagem úbiqua
                    .count();
                    /*
                   .map(Workstation::getUser) // está ocupada?
                   .filter(Objects::nonNull)
                   .count();
                    */
    }

    public Workstation assignUser(User user) {
        
        var workstation = this.getWorkstations().stream()
            .filter(Workstation::isFree)
            .findAny()
            .orElseThrow();

        workstation.assign(user);

        return workstation;
    }
}
