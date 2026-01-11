package com.vehiclub.api.domain.commande;

import com.vehiclub.api.domain.Vehicule;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMPTANT")
public class CommandeComptant extends Commande {

    public CommandeComptant() {
        super();
    }

    public CommandeComptant(Vehicule vehicule, double montantInitial, String paysLivraison) {
        super(vehicule, montantInitial, paysLivraison);
    }

    @Override
    public String getTypeCommande() {
        return "Comptant";
    }

    @Override
    public boolean validerCommande() {
        // Logique de validation simple pour un paiement comptant
        this.setStatus(StatutCommande.VALIDEE);
        System.out.println("Commande comptant validée.");
        return true;
    }

    @Override
    protected double calculerTaxes(double prixBase, String paysLivraison) {
        if ("France".equalsIgnoreCase(paysLivraison)) {
            return prixBase * 0.20; // TVA française
        } else if ("Suisse".equalsIgnoreCase(paysLivraison)) {
            return prixBase * 0.08; // TVA suisse
        }
        return prixBase * 0.20; // Taux par défaut
    }
}
