package com.vehiclub.api.services.factorymethod;

import com.vehiclub.api.domain.User;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.CommandeCredit;
import com.vehiclub.api.domain.commande.OrderItem;

import java.util.List;

public class CommandeCreditCreator extends CommandeCreator {
    @Override
    public Commande createCommande(List<OrderItem> items, User user, String paysLivraison) {
        return new CommandeCredit(items, user, paysLivraison);
    }
}
