package com.vehiclub.api.domain.commande;

import com.vehiclub.api.domain.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("COMPTANT")
public class CommandeComptant extends Commande {

    public CommandeComptant() {
        super();
    }

    public CommandeComptant(List<OrderItem> items, User user, String paysLivraison) {
        super(items, user, paysLivraison);
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
}
