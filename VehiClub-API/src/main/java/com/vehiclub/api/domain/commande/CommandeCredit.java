package com.vehiclub.api.domain.commande;

import com.vehiclub.api.domain.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("CREDIT")
public class CommandeCredit extends Commande {

    // Attributs spécifiques à la commande crédit
    private boolean creditApprouve;

    public CommandeCredit() {
        super();
    }

    public CommandeCredit(List<OrderItem> items, User user, String paysLivraison) {
        super(items, user, paysLivraison);
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

    // Méthode pour simuler l'approbation du crédit
    public void approuverCredit() {
        this.creditApprouve = true;
        System.out.println("Crédit approuvé pour la commande " + this.getId());
    }

    public boolean isCreditApprouve() {
        return creditApprouve;
    }
}
