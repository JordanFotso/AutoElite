package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.documents.HtmlDocument;

public class LiasseHtmlBuilder implements LiasseDocumentBuilder {
    private Liasse liasse = new Liasse();

    @Override
    public void buildDemandeImmatriculation(Vehicule vehicule) {
        liasse.addDocument(new HtmlDocument("Demande d'Immatriculation", "Contenu HTML pour l'immatriculation du " + vehicule.getNom()));
    }

    @Override
    public void buildCertificatCession(Vehicule vehicule) {
        liasse.addDocument(new HtmlDocument("Certificat de Cession", "Contenu HTML pour la cession du " + vehicule.getNom()));
    }

    @Override
    public void buildBonCommande(Vehicule vehicule) {
        liasse.addDocument(new HtmlDocument("Bon de Commande", "Contenu HTML pour la commande du " + vehicule.getNom()));
    }

    @Override
    public Liasse getResult() {
        return liasse;
    }
}
