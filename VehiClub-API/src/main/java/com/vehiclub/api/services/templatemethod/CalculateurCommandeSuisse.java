package com.vehiclub.api.services.templatemethod;

public class CalculateurCommandeSuisse extends CalculateurCommande {

    @Override
    protected double calculerTaxes(double prixBase) {
        // TVA suisse de 8%
        return prixBase * 0.08;
    }
}
