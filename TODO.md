# TODO - Avancement du Projet VehiClub

Ce fichier suit l'avancement de l'implémentation des différentes fonctionnalités et patrons de conception requis pour le projet.

## Fait

- [x] **1. (Abstract Factory) Construire les objets du domaine**
    - :heavy_check_mark: Initialisation du projet Spring Boot (`VehiClub-API`).
    - :heavy_check_mark: Configuration de la base de données PostgreSQL avec Docker.
    - :heavy_check_mark: Implémentation du patron Abstract Factory pour créer des `Vehicule` avec différents types de `Moteur`.
    - :heavy_check_mark: Mise en place d'un Service et d'un Controller REST pour exposer la création de véhicules via l'API (`POST /api/vehicules`).
    - :heavy_check_mark: Simplification du code (suppression de la `Carrosserie`) suite à la demande.
    - :heavy_check_mark: Test réussi de la création de véhicule via `curl`.

- [x] **2. (Builder) Construire les liasses de documents nécessaires en cas d’acquisition d’un véhicule.**
    - :heavy_check_mark: Définition des interfaces `Document` et de leurs implémentations concrètes (`PdfDocument`, `HtmlDocument`).
    - :heavy_check_mark: Création de la classe `Liasse` (le produit complexe).
    - :heavy_check_mark: Définition de l'interface `LiasseDocumentBuilder`.
    - :heavy_check_mark: Implémentation des builders concrets (`LiassePdfBuilder`, `LiasseHtmlBuilder`).
    - :heavy_check_mark: Création de la classe `DirecteurLiasse` (le Director).
    - :heavy_check_mark: Intégration dans `VehiculeService` et `VehiculeController` avec un endpoint `GET /api/vehicules/{id}/liasse?format={format}`.
    - :heavy_check_mark: Test réussi de la génération des liasses via `curl`.

- [x] **3. (Factory Method) Créer les commandes.**
    - :heavy_check_mark: Création de la classe abstraite `Commande` (Produit Abstraite) et de l'enum `StatutCommande`.
    - :heavy_check_mark: Création des classes concrètes `CommandeComptant` et `CommandeCredit` (Produits Concrets).
    - :heavy_check_mark: Création de la classe abstraite `CommandeCreator` (Créateur Abstrait).
    - :heavy_check_mark: Création des classes concrètes `CommandeComptantCreator` et `CommandeCreditCreator` (Créateurs Concrets).
    - :heavy_check_mark: Intégration dans `VehiculeService` avec des méthodes pour créer, valider et gérer les commandes.
    - :heavy_check_mark: Ajout d'endpoints dans `VehiculeController` pour la gestion des commandes (`POST /api/commandes`, `GET /api/commandes/{id}`, `PUT /api/commandes/{id}/valider`, `PUT /api/commandes/{id}/approuver-credit`).

## À Faire

- [ ] **4. (Singleton)** Créer la liasse vierge de documents.
- [ ] **5. (Adapter)** Gérer des documents PDF.
- [ ] **6. (Bridge)** Implanter des formulaires HTML ou à l’aide de widgets.
- [ ] **7. (Composite)** Représenter les sociétés clientes.
- [ ] **8. (Decorator, Observer)** Afficher les véhicules du catalogues.
- [ ] **9. (Iterator)** Retrouver séquentiellement les véhicules du catalogue.
- [ ] **10. (Template Method)** Calculer le montant d’une commande.
- [ ] **11. (Command)** Solder les véhicules restés en stock pendant une longue durée.
- [ ] **Frontend** : Implémenter l'interface utilisateur qui consommera l'API.

