package com.vehiclub.api.services;

import com.vehiclub.api.domain.User;
import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.CommandeCredit;
import com.vehiclub.api.domain.commande.OrderItem;
import com.vehiclub.api.domain.documents.HtmlDocument;
import com.vehiclub.api.repositories.CommandeRepository;
import com.vehiclub.api.repositories.VehiculeRepository;
import com.vehiclub.api.services.builder.DirecteurLiasse;
import com.vehiclub.api.services.builder.Liasse;
import com.vehiclub.api.services.builder.LiasseDocumentBuilder;
import com.vehiclub.api.services.builder.LiasseHtmlBuilder;
import com.vehiclub.api.services.builder.LiassePdfBuilder;
import com.vehiclub.api.services.factory.VehiculeFactory;
import com.vehiclub.api.services.factorymethod.CommandeComptantCreator;
import com.vehiclub.api.services.factorymethod.CommandeCreditCreator;
import com.vehiclub.api.services.factorymethod.CommandeCreator;
import com.vehiclub.api.services.iterator.CatalogueVehicules;
import com.vehiclub.api.services.iterator.Iterateur;
import com.vehiclub.api.services.singleton.LiasseVierge;
import com.vehiclub.api.services.templatemethod.CalculateurCommande;
import com.vehiclub.api.services.templatemethod.CalculateurCommandeFrance;
import com.vehiclub.api.services.templatemethod.CalculateurCommandeSuisse;
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
    private final LiasseHtmlBuilder liasseHtmlBuilder; // Injected
    private final LiassePdfBuilder liassePdfBuilder;   // Injected

    @Autowired
    public VehiculeService(VehiculeRepository vehiculeRepository,
                           CommandeRepository commandeRepository,
                           LiasseVierge liasseVierge,
                           CloudinaryService cloudinaryService,
                           LiasseHtmlBuilder liasseHtmlBuilder,
                           LiassePdfBuilder liassePdfBuilder) {
        this.vehiculeRepository = vehiculeRepository;
        this.commandeRepository = commandeRepository;
        this.liasseVierge = liasseVierge;
        this.cloudinaryService = cloudinaryService;
        this.liasseHtmlBuilder = liasseHtmlBuilder;
        this.liassePdfBuilder = liassePdfBuilder;
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

    public Vehicule createVehicule(Vehicule vehicule, MultipartFile imageFile, VehiculeFactory factory) {
        String imageUrl = imageFile != null ? cloudinaryService.uploadFile(imageFile) : null;
        vehicule.setImage(imageUrl);
        vehicule.setMoteur(factory.createMoteur());
        System.out.println("Création -> " + vehicule.getFullDescription());
        return vehiculeRepository.save(vehicule);
    }

    public Vehicule createVehicule(Vehicule vehicule, byte[] imageBytes, String filename, VehiculeFactory factory) throws IOException {
        // Upload de l'image sur Cloudinary
        String imageUrl = cloudinaryService.uploadFile(imageBytes, filename);
        vehicule.setImage(imageUrl);
        
        // Logique de création existante
        vehicule.setMoteur(factory.createMoteur());
        System.out.println("Création -> " + vehicule.getFullDescription());
        return vehiculeRepository.save(vehicule);
    }

    
    public Optional<Commande> createCommande(List<OrderItem> items, User user, String typeCommande, String paysLivraison) {
        double prixBaseTotal = items.stream().mapToDouble(item -> {
            double vehiclePrice = item.getVehicule().getBasePrice() - item.getVehicule().getSaleDiscount();
            double optionsPrice = item.getVehicule().getAvailableOptions().stream()
                .filter(opt -> item.getSelectedOptionsIds().contains(opt.getId()))
                .mapToDouble(opt -> opt.getPrice())
                .sum();
            return (vehiclePrice + optionsPrice) * item.getQuantity();
        }).sum();

        CalculateurCommande calculateur;
        if ("France".equalsIgnoreCase(paysLivraison)) {
            calculateur = new CalculateurCommandeFrance();
        } else if ("Suisse".equalsIgnoreCase(paysLivraison)) {
            calculateur = new CalculateurCommandeSuisse();
        } else {
            calculateur = new CalculateurCommandeFrance(); // Défaut
        }
        double montantTotal = calculateur.calculerMontantTotal(prixBaseTotal);

        CommandeCreator creator;
        if ("comptant".equalsIgnoreCase(typeCommande)) {
            creator = new CommandeComptantCreator();
        } else if ("credit".equalsIgnoreCase(typeCommande)) {
            creator = new CommandeCreditCreator();
        } else {
            return Optional.empty();
        }
        
        Commande commande = creator.createCommande(items, user, paysLivraison);
        commande.setMontantTotal(montantTotal);
        return Optional.of(commandeRepository.save(commande));
    }

    public Optional<Liasse> generateLiasseForCommande(Long commandeId, String format) {
        return commandeRepository.findById(commandeId).map(commande -> {
            DirecteurLiasse directeur = new DirecteurLiasse();
            LiasseDocumentBuilder builder;
            if ("pdf".equalsIgnoreCase(format)) {
                builder = liassePdfBuilder; 
            } else if ("html".equalsIgnoreCase(format)) {
                builder = liasseHtmlBuilder;
            } else {
                return null;
            }
            directeur.setBuilder(builder);
            return directeur.buildFullLiasse(commande);
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

    public void appliquerRemise(Vehicule vehicule, double pourcentageRemise) {
        double nouvelleRemise = vehicule.getBasePrice() * (pourcentageRemise / 100.0);
        vehicule.setSaleDiscount(nouvelleRemise);
        vehicule.setOnSale(true);
        vehiculeRepository.save(vehicule);
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

    public List<Commande> getCommandesByUser(User user) {
        return commandeRepository.findByUser(user);
    }

    public List<Vehicule> getVehiculesEnPromotion() {
        return vehiculeRepository.findByIsOnSaleTrue();
    }
}

