package com.vehiclub.api.services.iterator;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.repositories.VehiculeRepository;

import java.util.List;

public class CatalogueVehicules implements Agregat {
    private List<Vehicule> vehicules;

    public CatalogueVehicules(VehiculeRepository vehiculeRepository) {
        this.vehicules = vehiculeRepository.findAll();
    }

    @Override
    public Iterateur creeIterateur() {
        return new IterateurVehicule(this.vehicules);
    }
}
