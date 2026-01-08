package com.vehiclub.api.services.factory;

import com.vehiclub.api.domain.parts.Moteur;
import com.vehiclub.api.domain.parts.MoteurEssence;

public class EssenceFactory implements VehiculeFactory {
    @Override
    public Moteur createMoteur() {
        return new MoteurEssence();
    }
}