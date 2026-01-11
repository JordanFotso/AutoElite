package com.vehiclub.api.services.factorymethod;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.CommandeCredit;

public class CommandeCreditCreator extends CommandeCreator {
    @Override
    public Commande createCommande(Vehicule vehicule, double montantInitial, String paysLivraison) {
        return new CommandeCredit(vehicule, montantInitial, paysLivraison);
    }
}
