package com.vehiclub.api.controllers;

import com.vehiclub.api.domain.societe.composite.Societe;
import com.vehiclub.api.domain.societe.composite.SocieteComposite;
import com.vehiclub.api.domain.societe.composite.SocieteFeuille;
import com.vehiclub.api.services.SocieteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/societes")
public class SocieteController {

    private final SocieteService societeService;

    @Autowired
    public SocieteController(SocieteService societeService) {
        this.societeService = societeService;
    }

    @PostMapping("/feuille")
    public ResponseEntity<Societe> createSocieteFeuille(@RequestBody SocieteFeuille societeFeuille) {
        Societe savedSociete = societeService.saveSociete(societeFeuille);
        return new ResponseEntity<>(savedSociete, HttpStatus.CREATED);
    }

    @PostMapping("/composite")
    public ResponseEntity<Societe> createSocieteComposite(@RequestBody SocieteComposite societeComposite) {
        Societe savedSociete = societeService.saveSociete(societeComposite);
        return new ResponseEntity<>(savedSociete, HttpStatus.CREATED);
    }

    @PostMapping("/{parentId}/filiales/feuille")
    public ResponseEntity<Societe> addFeuilleFiliale(@PathVariable Long parentId, @RequestBody SocieteFeuille filiale) {
        try {
            Societe updatedSociete = societeService.addFiliale(parentId, filiale);
            return ResponseEntity.ok(updatedSociete);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{parentId}/filiales/composite")
    public ResponseEntity<Societe> addCompositeFiliale(@PathVariable Long parentId, @RequestBody SocieteComposite filiale) {
        try {
            Societe updatedSociete = societeService.addFiliale(parentId, filiale);
            return ResponseEntity.ok(updatedSociete);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Societe> getSocieteById(@PathVariable Long id) {
        return societeService.getSocieteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/roots")
    public ResponseEntity<List<Societe>> getRootSocietes() {
        List<Societe> rootSocietes = societeService.getRootSocietes();
        return new ResponseEntity<>(rootSocietes, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Societe>> getAllSocietes() {
        List<Societe> allSocietes = societeService.getAllSocietes();
        return new ResponseEntity<>(allSocietes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociete(@PathVariable Long id) {
        societeService.deleteSociete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
