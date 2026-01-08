package com.vehiclub.api.services.factorymethod;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.CommandeComptant;

public class CommandeComptantCreator extends CommandeCreator {
    @Override
    public Commande createCommande(Vehicule vehicule, double montantTotal) {
        return new CommandeComptant(vehicule, montantTotal);
    }
}
