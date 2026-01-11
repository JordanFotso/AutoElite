package com.vehiclub.api.services.command;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoldesManagerService {

    private final List<CommandePatron> commandes = new ArrayList<>();

    public void addCommande(CommandePatron commande) {
        this.commandes.add(commande);
    }

    public void executeAll() {
        for (CommandePatron commande : commandes) {
            commande.execute();
        }
        commandes.clear(); // Vider la liste après exécution
    }
}
