package com.vehiclub.api.services.iterator;

import com.vehiclub.api.domain.Vehicule;

import java.util.List;

public class IterateurVehicule implements Iterateur {
    private List<Vehicule> vehicules;
    private int position = 0;

    public IterateurVehicule(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    @Override
    public boolean aSuivant() {
        return position < vehicules.size();
    }

    @Override
    public Vehicule suivant() {
        if (this.aSuivant()) {
            return vehicules.get(position++);
        }
        return null;
    }
}
