package com.vehiclub.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class VehicleOption {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double price;
    private String category;
    
    // Pour la gestion des options incompatibles, nous pouvons utiliser une liste d'IDs ou de noms.
    // Pour l'instant, je vais utiliser une liste de noms pour simplifier.
    private List<String> incompatibleWith;
}
