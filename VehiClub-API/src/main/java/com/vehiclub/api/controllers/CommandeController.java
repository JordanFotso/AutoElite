package com.vehiclub.api.controllers;

import com.vehiclub.api.domain.User;
import com.vehiclub.api.domain.commande.Commande;
import com.vehiclub.api.domain.commande.OrderItem;
import com.vehiclub.api.dto.CommandeRequestDTO;
import com.vehiclub.api.repositories.UserRepository;
import com.vehiclub.api.services.VehiculeService;
import com.vehiclub.api.services.builder.Liasse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final VehiculeService vehiculeService;
    private final UserRepository userRepository;

    @Autowired
    public CommandeController(VehiculeService vehiculeService, UserRepository userRepository) {
        this.vehiculeService = vehiculeService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createCommande(@RequestBody CommandeRequestDTO commandeRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Enrichir les items avec les données complètes du véhicule
        List<OrderItem> enrichedItems = commandeRequest.getItems().stream().map(item -> {
            vehiculeService.getVehiculeById(item.getVehicule().getId())
                .ifPresent(item::setVehicule);
            return item;
        }).collect(Collectors.toList());

        return vehiculeService.createCommande(
                enrichedItems,
                user,
                commandeRequest.getTypeCommande(),
                commandeRequest.getPaysLivraison()
            )
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/me")
    public ResponseEntity<List<Commande>> getCurrentUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Commande> commandes = vehiculeService.getCommandesByUser(user);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}/liasse")
    public ResponseEntity<Liasse> getLiasse(@PathVariable Long id, @RequestParam String format) {
        return vehiculeService.generateLiasseForCommande(id, format)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        return vehiculeService.getCommandeById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<Commande> validerCommande(@PathVariable Long id) {
        return vehiculeService.validerCommande(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/approuver-credit")
    public ResponseEntity<Commande> approuverCreditCommande(@PathVariable Long id) {
        return vehiculeService.approuverCreditCommande(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
