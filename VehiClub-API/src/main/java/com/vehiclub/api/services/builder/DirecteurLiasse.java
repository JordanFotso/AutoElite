package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.Vehicule;

public class DirecteurLiasse {
    private LiasseDocumentBuilder builder;

    public void setBuilder(LiasseDocumentBuilder builder) {
        this.builder = builder;
    }

    public Liasse buildFullLiasse(Vehicule vehicule) {
        builder.buildDemandeImmatriculation(vehicule);
        builder.buildCertificatCession(vehicule);
        builder.buildBonCommande(vehicule);
        return builder.getResult();
    }
}
