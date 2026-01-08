package com.vehiclub.api.services.adapter;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service // Peut être un bean Spring si elle a des dépendances ou est stateless
public class PdfGeneratorService {

    public byte[] generateRawPdf(String title, String content) {
        // Simule la génération d'un PDF binaire
        String rawPdfContent = "%%PDF-1.4\n" +
                               "% simulate a raw PDF content\n" +
                               "1 0 obj\n" +
                               "<< /Type /Page\n" +
                               "/Contents << /Length 2 0 R >>\n" +
                               ">>\n" +
                               "endobj\n" +
                               "2 0 obj\n" +
                               "<< /Length 12 >>\n" +
                               "stream\n" +
                               "BT /F1 12 Tf 72 720 Td (" + title + ") Tj ET\n" +
                               "BT /F1 10 Tf 72 700 Td (" + content + ") Tj ET\n" +
                               "endstream\n" +
                               "endobj\n" +
                               "xref\n" +
                               "0 3\n" +
                               "0000000000 65535 f\n" +
                               "0000000009 00000 n\n" +
                               "0000000108 00000 n\n" +
                               "trailer\n" +
                               "<< /Size 3 /Root 1 0 R >>\n" +
                               "startxref\n" +
                               "200\n" + // Placeholder
                               "%%EOF";

        // Pour la démonstration, on retourne le contenu Base64-encodé du "PDF" simulé
        return rawPdfContent.getBytes(StandardCharsets.UTF_8);
    }
}
