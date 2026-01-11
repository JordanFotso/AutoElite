# VehiClub API - Backend

Cette API RESTful est le cœur de l'application VehiClub. Elle gère toute la logique métier, de la gestion des véhicules à la prise de commandes, en passant par la gestion des clients. L'API est développée en Java avec le framework Spring Boot et utilise une base de données PostgreSQL pour la persistance des données.

## Architecture & Patrons de Conception

L'API est conçue autour d'une architecture en couches, en mettant un accent particulier sur l'utilisation des **patrons de conception (Design Patterns)** pour résoudre des problèmes de conception courants et assurer la flexibilité et la maintenabilité du code.

### Patrons de Conception Implémentés

- **Abstract Factory**: Utilisé pour créer des familles d'objets liés sans spécifier leurs classes concrètes. Dans VehiClub, il permet de créer des véhicules avec différents types de motorisations (`EssenceFactory`, `ElectriqueFactory`).
- **Builder**: Utilisé pour construire des objets complexes étape par étape. Il est utilisé pour construire la `Liasse` de documents (PDF ou HTML) pour un véhicule.
- **Factory Method**: Utilisé pour créer des objets en laissant les sous-classes décider du type d'objet à créer. Il est utilisé pour créer les différents types de `Commande` (`CommandeComptant`, `CommandeCredit`).
- **Singleton**: Assure qu'une classe n'a qu'une seule instance et fournit un point d'accès global à cette instance. Il est utilisé pour gérer la `LiasseVierge` de documents.
- **Adapter**: Permet à des interfaces incompatibles de fonctionner ensemble. Il est utilisé pour adapter un service externe de génération de PDF à notre interface `Document`.
- **Composite**: Utilisé pour traiter un groupe d'objets de la même manière qu'un objet unique. Il est implémenté pour représenter les sociétés clientes et leurs filiales de manière hiérarchique.
- **Iterator**: Fournit un moyen de parcourir les éléments d'une collection sans exposer sa représentation interne. Il est utilisé pour parcourir le `CatalogueVehicules`.
- **Template Method**: Définit le squelette d'un algorithme dans une méthode, en reportant la définition de certaines étapes aux sous-classes. Il est utilisé dans la classe `Commande` pour définir l'algorithme de calcul du montant total, tout en laissant les sous-classes implémenter le calcul des taxes spécifiques.
- **Command**: Encapsule une requête en tant qu'objet, ce qui permet de paramétrer des clients avec différentes requêtes. Il est utilisé pour solder les véhicules (appliquer une remise).

## Structure des Services

- `VehiculeService`: Gère la logique métier liée aux véhicules, aux commandes, et aux liasses de documents.
- `SocieteService`: Gère la logique métier pour les sociétés clientes (patron Composite).
- `SoldesManagerService`: Orchestre l'exécution des commandes de soldes (patron Command).

## Documentation de l'API (Endpoints)

Voici une liste des principaux endpoints de l'API.

### Véhicules

- `POST /api/vehicules`: Crée un nouveau véhicule.
  - **Body**: `{ "nom": "string", "type": "essence" | "electrique" }`
- `GET /api/vehicules/catalogue`: Récupère la liste de tous les véhicules.
- `GET /api/vehicules/{id}/liasse`: Génère la liasse de documents pour un véhicule.
  - **Query Param**: `format=pdf` ou `format=html`
- `PUT /api/vehicules/{id}/prix`: Définit le prix d'un véhicule.
  - **Body**: `{ "prix": double }`
- `POST /api/vehicules/{id}/solder`: Applique une remise (solde) sur un véhicule.
  - **Body**: `{ "pourcentage": double }`

### Commandes

- `POST /api/commandes`: Crée une nouvelle commande.
  - **Body**: `{ "vehiculeId": long, "typeCommande": "comptant" | "credit", "montantInitial": double, "paysLivraison": "string" }`
- `GET /api/commandes/{id}`: Récupère une commande par son ID.
- `PUT /api/commandes/{id}/valider`: Valide une commande.
- `PUT /api/commandes/{id}/approuver-credit`: Approuve le crédit pour une commande de type crédit.

### Sociétés (Clients)

- `POST /api/societes/feuille`: Crée une société individuelle.
  - **Body**: `{ "nom": "string" }`
- `POST /api/societes/composite`: Crée une société composite (groupe).
  - **Body**: `{ "nom": "string" }`
- `POST /api/societes/{parentId}/filiales/feuille`: Ajoute une société individuelle comme filiale.
- `POST /api/societes/{parentId}/filiales/composite`: Ajoute une société composite comme filiale.
- `GET /api/societes/roots`: Récupère toutes les sociétés racines (sans parent).
- `GET /api/societes/{id}`: Récupère une société et sa hiérarchie de filiales.

## Lancement du Projet

### Prérequis

- JDK 17 ou supérieur
- Docker et Docker Compose

### Étapes

1.  **Démarrer la base de données**:
    À la racine du projet, exécutez la commande suivante pour démarrer le conteneur PostgreSQL en arrière-plan.

    ```bash
    docker-compose up -d
    ```

    La base de données sera accessible sur le port `5433` de votre machine locale.

2.  **Lancer l'API Backend**:
    Dans ce répertoire (`VehiClub-API`), utilisez le wrapper Maven pour lancer l'application.

    ```bash
    ./mvnw spring-boot:run
    ```

    L'API sera alors disponible à l'adresse `http://localhost:8080`.
