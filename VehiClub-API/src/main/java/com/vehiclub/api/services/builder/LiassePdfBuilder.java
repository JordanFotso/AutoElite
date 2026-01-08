package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.services.adapter.PdfDocumentAdapter;
import com.vehiclub.api.services.adapter.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Rendre ce builder un bean Spring pour l'injection du service
public class LiassePdfBuilder implements LiasseDocumentBuilder {
    private Liasse liasse = new Liasse();
    private final PdfGeneratorService pdfGeneratorService;

    // Injection du service via constructeur
    @Autowired
    public LiassePdfBuilder(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    // Le constructeur par défaut est nécessaire si Spring ne gère pas ce bean
    // ou si on veut l'instancier manuellement. Ici, on va s'assurer qu'il est géré par Spring.
    public LiassePdfBuilder() {
        this.pdfGeneratorService = new PdfGeneratorService(); // Fallback ou pour tests hors Spring
    }

    @Override
    public void buildDemandeImmatriculation(Vehicule vehicule) {
        liasse.addDocument(new PdfDocumentAdapter(pdfGeneratorService, "Demande d'Immatriculation", "Contenu pour l'immatriculation du " + vehicule.getNom()));
    }

    @Override
    public void buildCertificatCession(Vehicule vehicule) {
        liasse.addDocument(new PdfDocumentAdapter(pdfGeneratorService, "Certificat de Cession", "Contenu pour la cession du " + vehicule.getNom()));
    }

    @Override
    public void buildBonCommande(Vehicule vehicule) {
        liasse.addDocument(new PdfDocumentAdapter(pdfGeneratorService, "Bon de Commande", "Contenu pour la commande du " + vehicule.getNom()));
    }

    @Override
    public Liasse getResult() {
        return liasse;
    }
}
