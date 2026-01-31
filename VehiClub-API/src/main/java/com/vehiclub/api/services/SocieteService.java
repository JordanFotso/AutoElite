package com.vehiclub.api.services;

import com.vehiclub.api.domain.societe.composite.Societe;
import com.vehiclub.api.domain.societe.composite.SocieteComposite;
import com.vehiclub.api.domain.societe.composite.SocieteFeuille;
import com.vehiclub.api.repositories.SocieteRepository;
import com.vehiclub.api.dto.CreateSocieteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocieteService {

    private final SocieteRepository societeRepository;

    @Autowired
    public SocieteService(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    public Societe saveSociete(Societe societe) {
        return societeRepository.save(societe);
    }

    public Optional<Societe> getSocieteById(Long id) {
        return societeRepository.findById(id);
    }

    public List<Societe> getAllSocietes() {
        return societeRepository.findAll();
    }

    public List<Societe> getRootSocietes() {
        return societeRepository.findByParentIsNull();
    }

    public void deleteSociete(Long id) {
        societeRepository.deleteById(id);
    }

    public Societe addFiliale(Long parentId, Societe filiale) {
        return societeRepository.findById(parentId).map(parent -> {
            SocieteComposite composite = (SocieteComposite) parent;
            composite.add(filiale);
            filiale.setParent(composite); // S'assurer que le parent est bien défini pour la filiale
            return societeRepository.save(composite);
        }).orElseThrow(() -> new RuntimeException("Société parente non trouvée avec l'ID: " + parentId));
    }

    public ResponseEntity<?> createSociete(CreateSocieteRequest request) {
        Optional<Societe> parentOptional = societeRepository.findById(request.getParentId());
        if (parentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Parent company not found.");
        }

        Societe parent = parentOptional.get();
        if (!(parent instanceof SocieteComposite)) {
            return ResponseEntity.badRequest().body("Cannot add a subsidiary to a leaf company.");
        }

        SocieteComposite parentComposite = (SocieteComposite) parent;
        SocieteComposite newSubsidiary = new SocieteComposite(request.getNom()); // Always create Composite
        parentComposite.add(newSubsidiary);
        societeRepository.save(parentComposite); // Saving the parent should cascade and save the new subsidiary
        
        return ResponseEntity.ok(newSubsidiary);
    }

    // Méthodes utilitaires pour créer des types spécifiques (pour faciliter les tests et l'utilisation)
    public SocieteComposite createSocieteComposite(String nom) {
        return new SocieteComposite(nom);
    }

    public SocieteFeuille createSocieteFeuille(String nom) {
        return new SocieteFeuille(nom);
    }
}
