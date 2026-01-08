package com.vehiclub.api.services.builder;

import com.vehiclub.api.domain.documents.Document;
import java.util.ArrayList;
import java.util.List;

public class Liasse {
    private List<Document> documents = new ArrayList<>();

    public void addDocument(Document document) {
        documents.add(document);
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void displayLiasse() {
        System.out.println("--- Liasse de Documents ---");
        if (documents.isEmpty()) {
            System.out.println("Aucun document dans la liasse.");
        } else {
            for (Document doc : documents) {
                System.out.println("  " + doc.getType() + ": " + doc.getContent());
            }
        }
        System.out.println("---------------------------");
    }
}
