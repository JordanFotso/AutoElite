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

    public CommandeComptant(Vehicule vehicule, double montantTotal) {
        super(vehicule, montantTotal);
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
    public void calculerTotalAvecTaxes(String paysLivraison) {
        double tauxTaxe = 0.20; // Exemple de TVA par défaut
        if (paysLivraison != null && paysLivraison.equalsIgnoreCase("Canada")) {
            tauxTaxe = 0.05; // Exemple de TPS au Canada
        }
        // Le montant total sera mis à jour avec les taxes
        this.setMontantTotal(this.getMontantTotal() * (1 + tauxTaxe));
        System.out.println("Total commande comptant calculé avec taxes pour " + paysLivraison + ". Nouveau total: " + this.getMontantTotal());
    }
}
