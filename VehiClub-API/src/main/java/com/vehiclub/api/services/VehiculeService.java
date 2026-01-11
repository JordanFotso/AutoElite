package com.vehiclub.api.services;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.CommandeCredit;
import com.vehiclub.api.domain.documents.HtmlDocument;
import com.vehiclub.api.repositories.CommandeRepository;
import com.vehiclub.api.repositories.VehiculeRepository;
import com.vehiclub.api.services.singleton.LiasseVierge;
import com.vehiclub.api.services.factory.VehiculeFactory;
import com.vehiclub.api.services.builder.Liasse;
import com.vehiclub.api.services.builder.DirecteurLiasse;
import com.vehiclub.api.services.builder.LiasseDocumentBuilder;
import com.vehiclub.api.services.builder.LiasseHtmlBuilder;
import com.vehiclub.api.services.builder.LiassePdfBuilder;
import com.vehiclub.api.services.factorymethod.CommandeComptantCreator;
import com.vehiclub.api.services.factorymethod.CommandeCreditCreator;
import com.vehiclub.api.services.factorymethod.CommandeCreator;
import com.vehiclub.api.services.iterator.CatalogueVehicules;
import com.vehiclub.api.services.iterator.Iterateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final CommandeRepository commandeRepository;
    private final LiasseVierge liasseVierge;

    @Autowired
    public VehiculeService(VehiculeRepository vehiculeRepository, CommandeRepository commandeRepository, LiasseVierge liasseVierge) {
        this.vehiculeRepository = vehiculeRepository;
        this.commandeRepository = commandeRepository;
        this.liasseVierge = liasseVierge;
    }

    public List<Vehicule> getVehiculesFromIterator() {
        CatalogueVehicules catalogue = new CatalogueVehicules(vehiculeRepository);
        Iterateur iterateur = catalogue.creeIterateur();
        List<Vehicule> vehicules = new ArrayList<>();
        while (iterateur.aSuivant()) {
            vehicules.add(iterateur.suivant());
        }
        return vehicules;
    }

    public Vehicule createVehicule(String nom, VehiculeFactory factory) {
        // Utilise la factory pour créer les composants
        Vehicule vehicule = new Vehicule(nom, factory.createMoteur());

        // Affiche la description pour la démonstration
        System.out.println("Création -> " + vehicule.getDescription());

        // Sauvegarde l'entité en base de données.
        // Note: le champ 'moteur' marqué @Transient ne sera pas persisté.
        return vehiculeRepository.save(vehicule);
    }

    public Optional<Liasse> generateLiasse(Long vehiculeId, String format) {
        return vehiculeRepository.findById(vehiculeId).map(vehicule -> {
            DirecteurLiasse directeur = new DirecteurLiasse();
            LiasseDocumentBuilder builder;

            if ("pdf".equalsIgnoreCase(format)) {
                builder = new LiassePdfBuilder();
            } else if ("html".equalsIgnoreCase(format)) {
                builder = new LiasseHtmlBuilder();
            } else {
                return null; // Format non supporté
            }

            directeur.setBuilder(builder);
            return directeur.buildFullLiasse(vehicule);
        });
    }

    public Optional<Commande> createCommande(Long vehiculeId, String typeCommande, double montantInitial, String paysLivraison) {
        return vehiculeRepository.findById(vehiculeId).map(vehicule -> {
            CommandeCreator creator;
            if ("comptant".equalsIgnoreCase(typeCommande)) {
                creator = new CommandeComptantCreator();
            } else if ("credit".equalsIgnoreCase(typeCommande)) {
                creator = new CommandeCreditCreator();
            } else {
                return null; // Type de commande non supporté
            }

            Commande commande = creator.creerEtPreparerCommande(vehicule, montantInitial, paysLivraison);
            return commandeRepository.save(commande);
        });
    }

    public Optional<Commande> validerCommande(Long commandeId) {
        return commandeRepository.findById(commandeId).map(commande -> {
            commande.validerCommande();
            return commandeRepository.save(commande);
        });
    }

    public Optional<Commande> approuverCreditCommande(Long commandeId) {
        return commandeRepository.findById(commandeId).map(commande -> {
            if (commande instanceof CommandeCredit) {
                ((CommandeCredit) commande).approuverCredit();
                return commandeRepository.save(commande);
            }
            return commande; // Pas une commande crédit, ne fait rien
        });
    }

    public Optional<Commande> getCommandeById(Long commandeId) {
        return commandeRepository.findById(commandeId);
    }

    public LiasseVierge getLiasseVierge() {
        return liasseVierge;
    }

    public void addDocumentToLiasseVierge(String type, String content) {
        liasseVierge.addDocument(new HtmlDocument(type, content));
    }

    // Méthodes pour le patron Command
    public void appliquerRemise(Vehicule vehicule, double pourcentageRemise) {
        double nouvelleRemise = vehicule.getPrix() * (pourcentageRemise / 100.0);
        vehicule.setRemise(nouvelleRemise);
        vehiculeRepository.save(vehicule);
        System.out.println("Remise de " + pourcentageRemise + "% appliquée au véhicule " + vehicule.getNom());
    }

    public Optional<Vehicule> updatePrix(Long vehiculeId, double prix) {
        return vehiculeRepository.findById(vehiculeId).map(vehicule -> {
            vehicule.setPrix(prix);
            return vehiculeRepository.save(vehicule);
        });
    }

    public Optional<Vehicule> getVehiculeById(Long vehiculeId) {
        return vehiculeRepository.findById(vehiculeId);
    }
}

