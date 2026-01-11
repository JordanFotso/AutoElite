package com.vehiclub.api.services.templatemethod;

import com.vehiclub.api.domain.commande.Commande;

public abstract class CalculateurCommande {

    // La Template Method
    public final double calculerMontantTotal(Commande commande) {
        double prixBase = commande.getMontantInitial();
        double taxes = calculerTaxes(prixBase);
        double remise = calculerRemise(prixBase); // Étape optionnelle que les sous-classes peuvent surcharger

        return prixBase + taxes - remise;
    }

    // Étape abstraite que les sous-classes doivent implémenter
    protected abstract double calculerTaxes(double prixBase);

    // "Hook" - une étape optionnelle avec une implémentation par défaut
    protected double calculerRemise(double prixBase) {
        // Aucune remise par défaut
        return 0.0;
    }
}
