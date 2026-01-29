package com.vehiclub.api.services.factorymethod;

import com.vehiclub.api.domain.user.User;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.OrderItem;

import java.util.List;

public abstract class CommandeCreator {

    // La Factory Method abstraite
    public abstract Commande createCommande(List<OrderItem> items, User user, String paysLivraison);

}
