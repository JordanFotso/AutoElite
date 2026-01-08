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

    public CommandeCredit(Vehicule vehicule, double montantTotal) {
        super(vehicule, montantTotal);
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
    public void calculerTotalAvecTaxes(String paysLivraison) {
        double tauxTaxe = 0.20; // Exemple de TVA par défaut
        if (paysLivraison != null && paysLivraison.equalsIgnoreCase("Canada")) {
            tauxTaxe = 0.05; // Exemple de TPS au Canada
        }
        double fraisCredit = 0.02; // Frais de dossier pour le crédit
        this.setMontantTotal(this.getMontantTotal() * (1 + tauxTaxe) * (1 + fraisCredit));
        System.out.println("Total commande crédit calculé avec taxes et frais de crédit pour " + paysLivraison + ". Nouveau total: " + this.getMontantTotal());
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
