package com.vehiclub.api.domain.parts;

public class MoteurElectrique implements Moteur {
    @Override
    public String getEnergie() {
        return "Électrique";
    }
}
