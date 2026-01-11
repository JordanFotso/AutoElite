package com.vehiclub.api.domain.commande;

import com.vehiclub.api.domain.Vehicule;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CREDIT")
public class CommandeCredit extends Commande {

    // Attributs spécifiques à la commande crédit
    private boolean creditApprouve;

    public CommandeCredit() {
        super();
    }

    public CommandeCredit(Vehicule vehicule, double montantInitial, String paysLivraison) {
        super(vehicule, montantInitial, paysLivraison);
        this.creditApprouve = false; // Par défaut, le crédit n'est pas approuvé
    }

    @Override
    public String getTypeCommande() {
        return "Crédit";
    }

    @Override
    public boolean validerCommande() {
        // Logique de validation spécifique au crédit
        if (this.creditApprouve) {
            this.setStatus(StatutCommande.VALIDEE);
            System.out.println("Commande crédit validée (crédit approuvé).");
            return true;
        } else {
            System.out.println("Commande crédit en attente (crédit non approuvé).");
            return false;
        }
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

    @Override
    protected double calculerFrais(double prixBase) {
        // Ajout des frais de dossier pour le crédit (2% du prix de base)
        return prixBase * 0.02;
    }

    // Méthode pour simuler l'approbation du crédit
    public void approuverCredit() {
        this.creditApprouve = true;
        System.out.println("Crédit approuvé pour la commande " + this.getId());
    }

    public boolean isCreditApprouve() {
        return creditApprouve;
    }
}
