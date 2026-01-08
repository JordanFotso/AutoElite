package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.documents.PdfDocument;

public class LiassePdfBuilder implements LiasseDocumentBuilder {
    private Liasse liasse = new Liasse();

    @Override
    public void buildDemandeImmatriculation(Vehicule vehicule) {
        liasse.addDocument(new PdfDocument("Demande d'Immatriculation", "Contenu PDF pour l'immatriculation du " + vehicule.getNom()));
    }

    @Override
    public void buildCertificatCession(Vehicule vehicule) {
        liasse.addDocument(new PdfDocument("Certificat de Cession", "Contenu PDF pour la cession du " + vehicule.getNom()));
    }

    @Override
    public void buildBonCommande(Vehicule vehicule) {
        liasse.addDocument(new PdfDocument("Bon de Commande", "Contenu PDF pour la commande du " + vehicule.getNom()));
    }

    @Override
    public Liasse getResult() {
        return liasse;
    }
}
