package com.vehiclub.api.domain;

import com.vehiclub.api.domain.parts.Moteur;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue
    private Long id;

    private String nom;

    @Transient
    private Moteur moteur;

    public Vehicule(String nom, Moteur moteur) {
        this.nom = nom;
        this.moteur = moteur;
    }

    public String getDescription() {
        if (moteur == null) {
            return "Véhicule incomplet";
        }
        return "Véhicule: " + nom + ", Énergie: " + moteur.getEnergie();
    }
}
