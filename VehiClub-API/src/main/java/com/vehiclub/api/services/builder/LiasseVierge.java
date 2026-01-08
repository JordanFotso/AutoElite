package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.documents.Document;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component // En utilisant @Component, Spring gère cette classe comme un Singleton par défaut
public class LiasseVierge {

    private List<Document> documentsVierges = new ArrayList<>();

    // @PostConstruct est appelé après que l'instance a été créée et les dépendances injectées.
    // Parfait pour initialiser notre Singleton.
    @PostConstruct
    private void init() {
        System.out.println("--- Initialisation du Singleton LiasseVierge ---");
        // On pourrait ici ajouter des documents "modèles" si nécessaire.
        // Pour l'instant, la liasse est simplement vide, mais l'instance est unique.
    }

    public List<Document> getDocumentsVierges() {
        return documentsVierges;
    }

    // Pour prouver que c'est la même instance, on peut ajouter un document et voir
    // s'il persiste entre les appels.
    public void addDocument(Document doc) {
        documentsVierges.add(doc);
    }
}
