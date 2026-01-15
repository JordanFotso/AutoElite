package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.documents.PdfDocument;
import com.vehiclub.api.services.utils.HtmlToPdfConverter;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vehiclub.api.domain.documents.PdfDocument;

@Component
public class LiassePdfBuilder implements LiasseDocumentBuilder {
    private Liasse liasse = new Liasse();
    private final HtmlToPdfConverter htmlToPdfConverter;
    private final LiasseHtmlBuilder liasseHtmlBuilder; // To reuse HTML generation logic

    @Autowired
    public LiassePdfBuilder(HtmlToPdfConverter htmlToPdfConverter, LiasseHtmlBuilder liasseHtmlBuilder) {
        this.htmlToPdfConverter = htmlToPdfConverter;
        this.liasseHtmlBuilder = liasseHtmlBuilder;
    }

    @Override
    public void buildDemandeImmatriculation(Commande commande) {
        try {
            String htmlContent = liasseHtmlBuilder.generateDemandeImmatriculationHtml(commande);
            byte[] pdfBytes = htmlToPdfConverter.convertHtmlToPdf(htmlContent);
            liasse.addDocument(new PdfDocument("demande_immatriculation", pdfBytes));
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du PDF de la demande d'immatriculation: " + e.getMessage());
            // Optionally add a fallback or rethrow a custom exception
        }
    }

    @Override
    public void buildCertificatCession(Commande commande) {
        try {
            String htmlContent = liasseHtmlBuilder.generateCertificatCessionHtml(commande);
            byte[] pdfBytes = htmlToPdfConverter.convertHtmlToPdf(htmlContent);
            liasse.addDocument(new PdfDocument("certificat_cession", pdfBytes));
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du PDF du certificat de cession: " + e.getMessage());
            // Optionally add a fallback or rethrow a custom exception
        }
    }

    @Override
    public void buildBonCommande(Commande commande) {
        try {
            String htmlContent = liasseHtmlBuilder.generateBonCommandeHtml(commande);
            byte[] pdfBytes = htmlToPdfConverter.convertHtmlToPdf(htmlContent);
            liasse.addDocument(new PdfDocument("bon_commande", pdfBytes));
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du PDF du bon de commande: " + e.getMessage());
            // Optionally add a fallback or rethrow a custom exception
        }
    }

    @Override
    public Liasse getResult() {
        Liasse result = this.liasse;
        this.liasse = new Liasse(); // Reset for next build
        return result;
    }
}