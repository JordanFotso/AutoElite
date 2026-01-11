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
    - :heavy_check_mark: Ajout d'endpoints dans `VehiculeController` pour la gestion des commandes.
    - :heavy_check_mark: Test réussi du cycle de vie des commandes via `curl`.

- [x] **4. (Singleton)** Créer la liasse vierge de documents.
    - :heavy_check_mark: Création de la classe `LiasseVierge` annotée avec `@Component` pour être gérée comme un Singleton par Spring.
    - :heavy_check_mark: Ajout d'une méthode d'initialisation avec `@PostConstruct`.
    - :heavy_check_mark: Intégration dans `VehiculeService` et `VehiculeController` pour tester son comportement.

- [x] **5. (Adapter) Gérer des documents PDF.**
    - :heavy_check_mark: Simulation d'un service externe `PdfGeneratorService` (Adaptee).
    - :heavy_check_mark: Création de l'adaptateur `PdfDocumentAdapter` qui implémente `Document` et utilise `PdfGeneratorService`.
    - :heavy_check_mark: Modification de `LiassePdfBuilder` pour utiliser `PdfDocumentAdapter`.

- [x] **7. (Composite) Représenter les sociétés clientes.**
    - :heavy_check_mark: Création d'une structure de persistance JPA avec héritage (`SINGLE_TABLE`) pour le patron Composite.
    - :heavy_check_mark: Création de la classe abstraite `Societe` (le composant).
    - :heavy_check_mark: Création de `SocieteFeuille` (la feuille) et `SocieteComposite` (le composite).
    - :heavy_check_mark: Création du `SocieteRepository`, `SocieteService`, et `SocieteController` pour gérer la hiérarchie.
    - :heavy_check_mark: Ajout d'annotations `@JsonManagedReference` et `@JsonBackReference` pour gérer la sérialisation de la hiérarchie.
    - :heavy_check_mark: Test réussi de la création et de la récupération de la hiérarchie via `curl`.
    - :heavy_check_mark: Réorganisation des fichiers dans un sous-package `composite` dédié.

- [x] **9. (Iterator)** Retrouver séquentiellement les véhicules du catalogue.
    - :heavy_check_mark: Création des interfaces `Agregat` et `Iterateur`.
    - :heavy_check_mark: Création de l'agrégat concret `CatalogueVehicules`.
    - :heavy_check_mark: Création de l'itérateur concret `IterateurVehicule`.
    - :heavy_check_mark: Intégration dans `VehiculeService` et ajout d'un endpoint `GET /api/vehicules/catalogue` dans `VehiculeController`.

- [x] **10. (Template Method) Calculer le montant d’une commande.**
    - :heavy_check_mark: Refactorisation de la classe abstraite `Commande` pour en faire la classe Template.
    - :heavy_check_mark: Définition de la méthode `final calculerMontantTotal()` (la Template Method).
    - :heavy_check_mark: Définition de la méthode abstraite `calculerTaxes()` et du "hook" `calculerFrais()`.
    - :heavy_check_mark: Implémentation des étapes spécifiques dans les sous-classes `CommandeComptant` et `CommandeCredit`.
    - :heavy_check_mark: Mise à jour des `CommandeCreator` pour appeler la Template Method lors de la création.
    - :heavy_check_mark: Test réussi du calcul du montant pour différents pays et types de commandes.

- [x] **11. (Command)** Solder les véhicules restés en stock pendant une longue durée.
    - :heavy_check_mark: Création de l'interface `CommandePatron`.
    - :heavy_check_mark: Modification de l'entité `Vehicule` pour inclure un prix et une remise.
    - :heavy_check_mark: Création de la commande concrète `SoldeCommande`.
    - :heavy_check_mark: Création du service "Invoker" `SoldesManagerService`.
    - :heavy_check_mark: Intégration dans `VehiculeService` (le "Receiver") et `VehiculeController`.

## Annulé

- [ ] **6. (Bridge)** Implanter des formulaires HTML ou à l’aide de widgets. (Annulé car plus pertinent côté frontend).

## À Faire

- [ ] **8. (Decorator, Observer)** Afficher les véhicules du catalogues.
- [ ] **Frontend** : Implémenter l'interface utilisateur qui consommera l'API.