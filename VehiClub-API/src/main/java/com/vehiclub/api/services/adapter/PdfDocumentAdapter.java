package com.vehiclub.api.services.adapter;

import com.vehiclub.api.domain.documents.Document;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PdfDocumentAdapter implements Document {
    private PdfGeneratorService adaptee;
    private String title;
    private String rawContent; // Contenu avant conversion en PDF

    public PdfDocumentAdapter(PdfGeneratorService adaptee, String title, String rawContent) {
        this.adaptee = adaptee;
        this.title = title;
        this.rawContent = rawContent;
    }

    @Override
    public String getType() {
        return "PDF (via Adapter) - " + title;
    }

    @Override
    public String getContent() {
        // L'adaptateur utilise le service incompatible (adaptee)
        // et adapte sa sortie pour correspondre à l'interface Target (Document).
        byte[] pdfBytes = adaptee.generateRawPdf(title, rawContent);
        // Pour la démonstration, on encode en Base64 pour l'affichage textuel
        return Base64.getEncoder().encodeToString(pdfBytes);
    }
}
