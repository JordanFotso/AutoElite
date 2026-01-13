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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final CommandeRepository commandeRepository;
    private final LiasseVierge liasseVierge;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public VehiculeService(VehiculeRepository vehiculeRepository, CommandeRepository commandeRepository, LiasseVierge liasseVierge, CloudinaryService cloudinaryService) {
        this.vehiculeRepository = vehiculeRepository;
        this.commandeRepository = commandeRepository;
        this.liasseVierge = liasseVierge;
        this.cloudinaryService = cloudinaryService;
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

    // Méthode pour le contrôleur (avec MultipartFile)
    public Vehicule createVehicule(Vehicule vehicule, MultipartFile imageFile, VehiculeFactory factory) {
        // Upload de l'image sur Cloudinary
        String imageUrl = cloudinaryService.uploadFile(imageFile);
        vehicule.setImage(imageUrl);

        // Logique de création existante
        vehicule.setMoteur(factory.createMoteur());
        System.out.println("Création -> " + vehicule.getFullDescription());
        return vehiculeRepository.save(vehicule);
    }

    // Méthode surchargée pour le seeder (avec byte[] et filename)
    public Vehicule createVehicule(Vehicule vehicule, byte[] imageBytes, String filename, VehiculeFactory factory) throws IOException {
        // Upload de l'image sur Cloudinary
        String imageUrl = cloudinaryService.uploadFile(imageBytes, filename);
        vehicule.setImage(imageUrl);
        
        // Logique de création existante
        vehicule.setMoteur(factory.createMoteur());
        System.out.println("Création -> " + vehicule.getFullDescription());
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
                return null;
            }

            directeur.setBuilder(builder);
            return directeur.buildFullLiasse(vehicule);
        });
    }

    public Optional<Commande> createCommande(Long vehiculeId, String typeCommande, String paysLivraison) {
        return vehiculeRepository.findById(vehiculeId).map(vehicule -> {
            CommandeCreator creator;
            if ("comptant".equalsIgnoreCase(typeCommande)) {
                creator = new CommandeComptantCreator();
            } else if ("credit".equalsIgnoreCase(typeCommande)) {
                creator = new CommandeCreditCreator();
            } else {
                return null;
            }

            Commande commande = creator.creerEtPreparerCommande(vehicule, vehicule.getBasePrice(), paysLivraison);
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
            return commande;
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
        double nouvelleRemise = vehicule.getBasePrice() * (pourcentageRemise / 100.0);
        vehicule.setSaleDiscount(nouvelleRemise);
        vehicule.setOnSale(true);
        vehiculeRepository.save(vehicule);
        System.out.println("Remise de " + pourcentageRemise + "% appliquée au véhicule " + vehicule.getName());
    }

    public Optional<Vehicule> updatePrix(Long vehiculeId, double prix) {
        return vehiculeRepository.findById(vehiculeId).map(vehicule -> {
            vehicule.setBasePrice(prix);
            return vehiculeRepository.save(vehicule);
        });
    }

    public Optional<Vehicule> getVehiculeById(Long vehiculeId) {
        return vehiculeRepository.findById(vehiculeId);
    }

    public List<Vehicule> getVehiculesEnPromotion() {
        return vehiculeRepository.findByIsOnSaleTrue();
    }
}

