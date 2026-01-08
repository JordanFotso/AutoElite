package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.documents.Document;

public interface LiasseDocumentBuilder {
    void buildDemandeImmatriculation(Vehicule vehicule);
    void buildCertificatCession(Vehicule vehicule);
    void buildBonCommande(Vehicule vehicule);
    Liasse getResult();
}
