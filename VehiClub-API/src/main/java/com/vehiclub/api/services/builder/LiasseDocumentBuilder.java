package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.commande.Commande;

public interface LiasseDocumentBuilder {
    void buildDemandeImmatriculation(Commande commande);
    void buildCertificatCession(Commande commande);
    void buildBonCommande(Commande commande);
    Liasse getResult();
}
