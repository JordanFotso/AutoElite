package com.vehiclub.api.services.templatemethod;

public class CalculateurCommandeFrance extends CalculateurCommande {

    @Override
    protected double calculerTaxes(double prixBase) {
        // TVA française de 20%
        return prixBase * 0.20;
    }
}
