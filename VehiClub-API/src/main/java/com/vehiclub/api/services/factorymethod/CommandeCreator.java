package com.vehiclub.api.services.factorymethod;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.commande.Commande;

public abstract class CommandeCreator {

    // La Factory Method abstraite
    public abstract Commande createCommande(Vehicule vehicule, double montantInitial, String paysLivraison);

    // D'autres opérations qui pourraient utiliser la factory method
    public Commande creerEtPreparerCommande(Vehicule vehicule, double montantInitial, String paysLivraison) {
        Commande commande = createCommande(vehicule, montantInitial, paysLivraison);
        commande.calculerMontantTotal(); // Appel de la Template Method
        // La validation sera faite séparément par le service ou une autre étape
        return commande;
    }
}
