package com.vehiclub.api.services;

import com.vehiclub.api.domain.societe.composite.Societe;
import com.vehiclub.api.domain.societe.composite.SocieteComposite;
import com.vehiclub.api.domain.societe.composite.SocieteFeuille;
import com.vehiclub.api.repositories.SocieteRepository;
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
            if (parent instanceof SocieteComposite) {
                SocieteComposite composite = (SocieteComposite) parent;
                composite.add(filiale);
                filiale.setParent(composite); // S'assurer que le parent est bien défini pour la filiale
                return societeRepository.save(composite);
            } else {
                throw new UnsupportedOperationException("Impossible d'ajouter une filiale à une société feuille.");
            }
        }).orElseThrow(() -> new RuntimeException("Société parente non trouvée avec l'ID: " + parentId));
    }

    // Méthodes utilitaires pour créer des types spécifiques (pour faciliter les tests et l'utilisation)
    public SocieteComposite createSocieteComposite(String nom) {
        return new SocieteComposite(nom);
    }

    public SocieteFeuille createSocieteFeuille(String nom) {
        return new SocieteFeuille(nom);
    }
}
