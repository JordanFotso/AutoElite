package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.OrderItem;
import com.vehiclub.api.domain.documents.HtmlDocument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class LiasseHtmlBuilder implements LiasseDocumentBuilder {
    private Liasse liasse = new Liasse();

    public String formatPrice(double price) {
        return String.format("%,.2f €", price);
    }

    public String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getStatusClass(Commande.StatutCommande status) {
        switch (status) {
            case EN_COURS: return "status-pending";
            case VALIDEE: return "status-validated";
            case LIVREE: return "status-delivered";
            case ANNULEE: return "status-cancelled";
            default: return "";
        }
    }
    
    public String getStatusLabel(Commande.StatutCommande status) {
        switch (status) {
            case EN_COURS: return "En cours";
            case VALIDEE: return "Validée";
            case LIVREE: return "Livrée";
            case ANNULEE: return "Annulée";
            default: return "Inconnu";
        }
    }

    public String generateDemandeImmatriculationHtml(Commande commande) {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html><html lang=\"fr\"><head><meta charset=\"UTF-8\"/><title>Demande d'Immatriculation - CMD-").append(commande.getId()).append("</title>");
        content.append("<style>body { font-family: 'Segoe UI', Arial, sans-serif; margin: 40px; color: #1a1a1a; } .header { text-align: center; border-bottom: 3px solid #c9a227; padding-bottom: 20px; margin-bottom: 30px; } .logo { font-size: 28px; font-weight: bold; color: #c9a227; } .document-title { font-size: 22px; margin-top: 15px; color: #333; } .section { margin: 25px 0; padding: 20px; background: #f8f8f8; border-radius: 8px; } .section-title { font-size: 16px; font-weight: bold; color: #c9a227; margin-bottom: 15px; text-transform: uppercase; } .field { margin: 10px 0; display: flex; } .field-label { font-weight: 600; width: 200px; color: #666; } .field-value { color: #1a1a1a; } .footer { margin-top: 40px; text-align: center; font-size: 12px; color: #888; } .signature-box { margin-top: 40px; border: 1px dashed #ccc; padding: 30px; text-align: center; }</style>");
        content.append("</head><body><div class=\"header\"><div class=\"logo\">AutoÉlite</div><div class=\"document-title\">Demande d'Immatriculation</div><div style=\"font-size: 14px; color: #666; margin-top: 10px;\">Référence: CMD-").append(commande.getId()).append("</div></div>");
        
        content.append("<div class=\"section\"><div class=\"section-title\">Informations du Demandeur</div>");
        content.append("<div class=\"field\"><span class=\"field-label\">Date de la demande:</span><span class=\"field-value\">").append(formatDate(commande.getDateCommande())).append("</span></div>");
        content.append("<div class=\"field\"><span class=\"field-label\">Pays de livraison:</span><span class=\"field-value\">").append(commande.getPaysLivraison()).append("</span></div>");
        content.append("<div class=\"field\"><span class=\"field-label\">Mode de paiement:</span><span class=\"field-value\">").append(commande.getTypeCommande()).append("</span></div></div>");

        content.append("<div class=\"section\"><div class=\"section-title\">Véhicule(s) à Immatriculer</div>");
        for (int i = 0; i < commande.getItems().size(); i++) {
            OrderItem item = commande.getItems().get(i);
            Vehicule v = item.getVehicule();
            content.append("<div style=\"margin-bottom: 20px; ").append(i > 0 ? "border-top: 1px solid #ddd; padding-top: 20px;" : "").append("\">");
            content.append("<div class=\"field\"><span class=\"field-label\">Véhicule:</span><span class=\"field-value\">").append(v.getBrand()).append(" ").append(v.getName()).append("</span></div>");
            content.append("<div class=\"field\"><span class=\"field-label\">Modèle:</span><span class=\"field-value\">").append(v.getModel()).append(" (").append(v.getYear()).append(")</span></div>");
            content.append("<div class=\"field\"><span class=\"field-label\">Type:</span><span class=\"field-value\">").append(v.getType()).append("</span></div>");
            content.append("<div class=\"field\"><span class=\"field-label\">Motorisation:</span><span class=\"field-value\">").append(v.getEnergie()).append("</span></div>");
            content.append("</div>");
        }
        content.append("</div>");

        content.append("<div class=\"signature-box\"><p style=\"margin-bottom: 30px;\">Signature du demandeur</p><div style=\"border-bottom: 1px solid #333; width: 300px; margin: 0 auto;\"></div><p style=\"margin-top: 10px; font-size: 12px; color: #666;\">Date: _______________</p></div>");
        content.append("<div class=\"footer\"><p>AutoÉlite - Véhicules d'Exception</p><p>Document généré le ").append(formatDate(LocalDateTime.now())).append("</p></div></body></html>");
        return content.toString();
    }
    
    public String generateCertificatCessionHtml(Commande commande) {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html><html lang=\"fr\"><head><meta charset=\"UTF-8\"/><title>Certificat de Cession - CMD-").append(commande.getId()).append("</title>");
        content.append("<style>body { font-family: 'Segoe UI', Arial, sans-serif; margin: 40px; color: #1a1a1a; } .header { text-align: center; border-bottom: 3px solid #c9a227; padding-bottom: 20px; margin-bottom: 30px; } .logo { font-size: 28px; font-weight: bold; color: #c9a227; } .document-title { font-size: 22px; margin-top: 15px; color: #333; } .official { background: #f0f0f0; padding: 10px; text-align: center; font-weight: bold; color: #666; margin-top: 10px; } .section { margin: 25px 0; padding: 20px; background: #f8f8f8; border-radius: 8px; } .section-title { font-size: 16px; font-weight: bold; color: #c9a227; margin-bottom: 15px; text-transform: uppercase; } .field { margin: 10px 0; overflow: hidden; } .field-label { font-weight: 600; width: 200px; float: left; color: #666; } .field-value { float: left; color: #1a1a1a; } .footer { margin-top: 40px; text-align: center; font-size: 12px; color: #888; } .dual-signature { overflow: hidden; margin-top: 40px; } .signature-box { border: 1px dashed #ccc; padding: 30px; text-align: center; width: 49%; float: left; }</style>");
        content.append("</head><body><div class=\"header\"><div class=\"logo\">AutoÉlite</div><div class=\"document-title\">Certificat de Cession</div><div class=\"official\">DOCUMENT OFFICIEL DE TRANSFERT DE PROPRIÉTÉ</div></div>");

        content.append("<div class=\"section\"><div class=\"section-title\">Parties Concernées</div><div style=\"overflow: hidden;\"><div style=\"float: left; width: 48%; margin-right: 4%;\"><h4 style=\"color: #c9a227; margin-bottom: 10px;\">Vendeur</h4><div class=\"field\"><span class=\"field-label\">Société:</span><span class=\"field-value\">AutoÉlite SAS</span></div><div class=\"field\"><span class=\"field-label\">SIRET:</span><span class=\"field-value\">XXX XXX XXX XXXXX</span></div></div><div style=\"float: left; width: 48%;\"><h4 style=\"color: #c9a227; margin-bottom: 10px;\">Acquéreur</h4><div class=\"field\"><span class=\"field-label\">Client:</span><span class=\"field-value\">").append(commande.getUser().getEmail()).append("</span></div><div class=\"field\"><span class=\"field-label\">Pays:</span><span class=\"field-value\">").append(commande.getPaysLivraison()).append("</span></div></div></div></div>");

        content.append("<div class=\"section\"><div class=\"section-title\">Véhicule(s) Cédé(s)</div>");
        for (OrderItem item : commande.getItems()) {
            Vehicule v = item.getVehicule();
            content.append("<div style=\"margin-bottom: 15px; padding: 15px; background: white; border-radius: 4px;\"><div class=\"field\"><span class=\"field-label\">Désignation:</span><span class=\"field-value\"><strong>").append(v.getBrand()).append(" ").append(v.getName()).append("</strong></span></div><div class=\"field\"><span class=\"field-label\">Modèle / Année:</span><span class=\"field-value\">").append(v.getModel()).append(" / ").append(v.getYear()).append("</span></div><div class=\"field\"><span class=\"field-label\">Catégorie:</span><span class=\"field-value\">").append(v.getType()).append("</span></div></div>");
        }
        content.append("</div>");
        
        content.append("<div class=\"dual-signature\"><div class=\"signature-box\"><p style=\"margin-bottom: 30px;\">Le Vendeur</p><div style=\"border-bottom: 1px solid #333; width: 80%; margin: 0 auto;\"></div><p style=\"margin-top: 10px; font-size: 12px;\">AutoÉlite SAS</p></div><div class=\"signature-box\"><p style=\"margin-bottom: 30px;\">L'Acquéreur</p><div style=\"border-bottom: 1px solid #333; width: 80%; margin: 0 auto;\"></div><p style=\"margin-top: 10px; font-size: 12px;\">Signature précédée de \"Lu et approuvé\"</p></div></div>");
        content.append("<div class=\"footer\"><p>Ce certificat atteste du transfert de propriété du véhicule mentionné ci-dessus.</p><p>Document généré le ").append(formatDate(LocalDateTime.now())).append("</p></div></body></html>");
        return content.toString();
    }

    public String generateBonCommandeHtml(Commande commande) {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html><html lang=\"fr\"><head><meta charset=\"UTF-8\"/><title>Bon de Commande - CMD-").append(commande.getId()).append("</title>");
        content.append("<style>body { font-family: 'Segoe UI', Arial, sans-serif; margin: 40px; color: #1a1a1a; } .header { text-align: center; border-bottom: 3px solid #c9a227; padding-bottom: 20px; margin-bottom: 30px; } .logo { font-size: 28px; font-weight: bold; color: #c9a227; } .document-title { font-size: 22px; margin-top: 15px; color: #333; } .order-ref { background: #c9a227; color: white; padding: 8px 20px; display: inline-block; margin-top: 10px; border-radius: 4px; } .section { margin: 25px 0; padding: 20px; background: #f8f8f8; border-radius: 8px; } .section-title { font-size: 16px; font-weight: bold; color: #c9a227; margin-bottom: 15px; text-transform: uppercase; } .table { width: 100%; border-collapse: collapse; margin-top: 10px; } .table th { background: #c9a227; color: white; padding: 12px; text-align: left; } .table td { padding: 12px; border-bottom: 1px solid #ddd; } .table tr:hover { background: #f0f0f0; } .total-row { background: #1a1a1a !important; color: white; font-weight: bold; } .total-row:hover { background: #1a1a1a !important; } .footer { margin-top: 40px; text-align: center; font-size: 12px; color: #888; } .status { display: inline-block; padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: bold; } .status-pending { background: #fef3c7; color: #d97706; } .status-validated { background: #dbeafe; color: #2563eb; } .status-delivered { background: #d1fae5; color: #059669; } .status-cancelled { background: #fee2e2; color: #dc2626; }</style>");
        content.append("</head><body><div class=\"header\"><div class=\"logo\">AutoÉlite</div><div class=\"document-title\">Bon de Commande</div><div class=\"order-ref\">CMD-").append(commande.getId()).append("</div></div>");

        String statusClass = getStatusClass(commande.getStatus());
        String statusLabel = getStatusLabel(commande.getStatus());
        content.append("<div class=\"section\"><div class=\"section-title\">Informations de Commande</div><div style=\"overflow: hidden;\"><div style=\"float: left; width: 68%;\"><p><strong>Date:</strong> ").append(formatDate(commande.getDateCommande())).append("</p><p><strong>Client:</strong> ").append(commande.getUser().getEmail()).append("</p><p><strong>Pays de livraison:</strong> ").append(commande.getPaysLivraison()).append("</p><p><strong>Mode de paiement:</strong> ").append(commande.getTypeCommande()).append("</p></div><div style=\"float: right; width: 30%; text-align: right;\"><span class=\"status ").append(statusClass).append("\">").append(statusLabel).append("</span></div></div></div>");

        content.append("<div class=\"section\"><div class=\"section-title\">Détail de la Commande</div><table class=\"table\"><thead><tr><th>Article</th><th>Qté</th><th style=\"text-align: right;\">Prix Total</th></tr></thead><tbody>");
        for (OrderItem item : commande.getItems()) {
            Vehicule v = item.getVehicule();
            double totalItemPrice = (v.getBasePrice() - v.getSaleDiscount()) * item.getQuantity();
            content.append("<tr><td><strong>").append(v.getBrand()).append(" ").append(v.getName()).append("</strong><br /><span style=\"font-size: 12px; color: #666;\">").append(v.getModel()).append(" (").append(v.getYear()).append(")</span></td><td>").append(item.getQuantity()).append("</td><td style=\"text-align: right;\">").append(formatPrice(totalItemPrice)).append("</td></tr>");
        }
        content.append("</tbody></table></div>");

        content.append("<div class=\"section\"><div class=\"section-title\">Récapitulatif</div><table class=\"table\"><tbody><tr class=\"total-row\"><td><strong>TOTAL TTC</strong></td><td style=\"text-align: right; font-size: 18px;\">").append(formatPrice(commande.getMontantTotal())).append("</td></tr></tbody></table></div>");

        content.append("<div class=\"section\" style=\"background: #1a1a1a; color: white;\"><p style=\"margin: 0; text-align: center;\">Merci pour votre confiance. Pour toute question, contactez-nous.</p></div>");
        content.append("<div class=\"footer\"><p>AutoÉlite - Véhicules d'Exception | contact@autoelite.fr</p><p>Document généré le ").append(formatDate(LocalDateTime.now())).append("</p></div></body></html>");
        return content.toString();
    }
    

    @Override
    public void buildDemandeImmatriculation(Commande commande) {
        liasse.addDocument(new HtmlDocument("demande_immatriculation", generateDemandeImmatriculationHtml(commande)));
    }

    @Override
    public void buildCertificatCession(Commande commande) {
        liasse.addDocument(new HtmlDocument("certificat_cession", generateCertificatCessionHtml(commande)));
    }

    @Override
    public void buildBonCommande(Commande commande) {
        liasse.addDocument(new HtmlDocument("bon_commande", generateBonCommandeHtml(commande)));
    }
            
    @Override
    public Liasse getResult() {
        Liasse result = this.liasse;
        this.liasse = new Liasse(); // Reset for next build
        return result;
    }
}