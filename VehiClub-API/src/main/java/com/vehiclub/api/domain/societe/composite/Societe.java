package com.vehiclub.api.domain.societe.composite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_societe", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Societe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nom;

    // Pour la gestion des relations parent-enfant dans le Composite
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference("filiales-parent") // Gère la référence inverse pour éviter les boucles de sérialisation
    protected Societe parent;

    public Societe(String nom) {
        this.nom = nom;
    }

    public abstract void afficher(int indentation);

    public void add(Societe societe) {
        throw new UnsupportedOperationException("Cette opération n'est pas supportée par ce type de société.");
    }

    public void remove(Societe societe) {
        throw new UnsupportedOperationException("Cette opération n'est pas supportée par ce type de société.");
    }

    @JsonIgnore
    public List<Societe> getFiliales() {
        return Collections.emptyList();
    }
}