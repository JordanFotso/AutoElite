package com.vehiclub.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vehiclub.api.domain.embeddable.Specifications;
import com.vehiclub.api.domain.enums.VehicleType;
import com.vehiclub.api.domain.parts.Moteur;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private String brand;
    private String model;
    private int year;
    private double basePrice;
    private String description;
    private String image;
    private double saleDiscount;
    private boolean isOnSale;
    private LocalDate inStockSince;

    @Embedded
    private Specifications specifications;
    public String getEnergie(){
        return this.specifications.getEngine();
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vehicule_id")
    private List<VehicleOption> availableOptions = new ArrayList<>();

    @Transient
    private Moteur moteur;

    public Vehicule(String name, Moteur moteur) {
        this.name = name;
        this.moteur = moteur;
        this.basePrice = 0.0;
        this.saleDiscount = 0.0;
        this.inStockSince = LocalDate.now();
    }

    public String getFullDescription() {
        if (moteur == null) {
            return "Véhicule incomplet";
        }
        return "Véhicule: " + name + ", Énergie: " + moteur.getEnergie();
    }
}
