package com.vehiclub.api.services.factory;

import com.vehiclub.api.domain.parts.Moteur;
import com.vehiclub.api.domain.parts.MoteurElectrique;

public class ElectriqueFactory implements VehiculeFactory {
    @Override
    public Moteur createMoteur() {
        return new MoteurElectrique();
    }
}