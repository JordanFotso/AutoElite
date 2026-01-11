package com.vehiclub.api.controllers;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.services.VehiculeService;
import com.vehiclub.api.services.builder.Liasse;
import com.vehiclub.api.services.command.CommandePatron;
import com.vehiclub.api.services.command.SoldeCommande;
import com.vehiclub.api.services.command.SoldesManagerService;
import com.vehiclub.api.services.singleton.LiasseVierge;
import com.vehiclub.api.services.factory.ElectriqueFactory;
import com.vehiclub.api.services.factory.EssenceFactory;
import com.vehiclub.api.services.factory.VehiculeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api") // Gardons-le comme ça pour le moment pour éviter de casser les autres endpoints.
public class VehiculeController {

    private final VehiculeService vehiculeService;
    private final SoldesManagerService soldesManagerService;

    @Autowired
    public VehiculeController(VehiculeService vehiculeService, SoldesManagerService soldesManagerService) {
        this.vehiculeService = vehiculeService;
        this.soldesManagerService = soldesManagerService;
    }

    // Endpoints pour les véhicules
    @PostMapping("/vehicules")
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Map<String, String> body) {
        String nom = body.get("nom");
        String type = body.get("type");

        VehiculeFactory factory = getFactory(type);
        if (factory == null) {
            return ResponseEntity.badRequest().build();
        }

        Vehicule vehicule = vehiculeService.createVehicule(nom, factory);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicule);
    }

    @GetMapping("/vehicules/{id}/liasse")
    public ResponseEntity<Liasse> getLiasse(@PathVariable Long id, @RequestParam String format) {
        Optional<Liasse> liasse = vehiculeService.generateLiasse(id, format);
        return liasse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/vehicules/catalogue")
    public ResponseEntity<List<Vehicule>> getCatalogue() {
        List<Vehicule> vehicules = vehiculeService.getVehiculesFromIterator();
        return ResponseEntity.ok(vehicules);
    }
    
    @PutMapping("/vehicules/{id}/prix")
    public ResponseEntity<Vehicule> setPrix(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        double prix = body.get("prix");
        return vehiculeService.updatePrix(id, prix)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vehicules/{id}/solder")
    public ResponseEntity<Vehicule> solderVehicule(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        double pourcentage = body.get("pourcentage");

        return vehiculeService.getVehiculeById(id).map(vehicule -> {
            CommandePatron soldeCommande = new SoldeCommande(vehicule, pourcentage, vehiculeService);
            soldesManagerService.addCommande(soldeCommande);
            soldesManagerService.executeAll();
            return vehiculeService.getVehiculeById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoints pour les commandes
    @PostMapping("/commandes")
    public ResponseEntity<Commande> createCommande(@RequestBody Map<String, String> body) {
        Long vehiculeId = Long.parseLong(body.get("vehiculeId"));
        String typeCommande = body.get("typeCommande");
        double montantInitial = Double.parseDouble(body.get("montantInitial"));
        String paysLivraison = body.get("paysLivraison");

        Optional<Commande> commande = vehiculeService.createCommande(vehiculeId, typeCommande, montantInitial, paysLivraison);
        return commande.map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/commandes/{id}")
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        Optional<Commande> commande = vehiculeService.getCommandeById(id);
        return commande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/commandes/{id}/valider")
    public ResponseEntity<Commande> validerCommande(@PathVariable Long id) {
        Optional<Commande> commande = vehiculeService.validerCommande(id);
        return commande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/commandes/{id}/approuver-credit")
    public ResponseEntity<Commande> approuverCreditCommande(@PathVariable Long id) {
        Optional<Commande> commande = vehiculeService.approuverCreditCommande(id);
        return commande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoints pour le Singleton LiasseVierge
    @GetMapping("/liasse-vierge")
    public ResponseEntity<LiasseVierge> getLiasseVierge() {
        return ResponseEntity.ok(vehiculeService.getLiasseVierge());
    }

    @PostMapping("/liasse-vierge/documents")
    public ResponseEntity<LiasseVierge> addDocumentToLiasseVierge(@RequestBody Map<String, String> body) {
        String type = body.get("type");
        String content = body.get("content");
        vehiculeService.addDocumentToLiasseVierge(type, content);
        return ResponseEntity.ok(vehiculeService.getLiasseVierge());
    }


    private VehiculeFactory getFactory(String type) {
        if (type == null) return null;
        return switch (type.toLowerCase()) {
            case "essence" -> new EssenceFactory();
            case "electrique" -> new ElectriqueFactory();
            default -> null;
        };
    }
}
