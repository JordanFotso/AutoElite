package com.vehiclub.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @JsonIgnore // On ne veut pas exposer l'ID de la BDD
    private Long jpaId;

    @Column(unique = true, nullable = false)
    private String id; // ID métier (ex: "sieges-cuir")

    private String name;
    private double price;
    private String category;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> incompatibleWith;
}
