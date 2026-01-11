package com.vehiclub.api.services.command;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.services.VehiculeService;

public class SoldeCommande implements CommandePatron {

    private final Vehicule vehicule;
    private final double pourcentageRemise;
    private final VehiculeService vehiculeService; // Le "Receiver"

    public SoldeCommande(Vehicule vehicule, double pourcentageRemise, VehiculeService vehiculeService) {
        this.vehicule = vehicule;
        this.pourcentageRemise = pourcentageRemise;
        this.vehiculeService = vehiculeService;
    }

    @Override
    public void execute() {
        vehiculeService.appliquerRemise(vehicule, pourcentageRemise);
    }
}
