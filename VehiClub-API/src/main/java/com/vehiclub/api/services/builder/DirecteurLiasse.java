package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.commande.Commande;

public class DirecteurLiasse {
    private LiasseDocumentBuilder builder;

    public void setBuilder(LiasseDocumentBuilder builder) {
        this.builder = builder;
    }

    public Liasse buildFullLiasse(Commande commande) {
        builder.buildDemandeImmatriculation(commande);
        builder.buildCertificatCession(commande);
        builder.buildBonCommande(commande);
        return builder.getResult();
    }
}
