# VehiClub - Application de Vente de Véhicules en Ligne

Ce projet est une application web de vente de véhicules en ligne, conçue pour illustrer l'utilisation de divers patrons de conception (design patterns) dans une architecture 3-tiers.

## Architecture

L'application est basée sur une architecture 3-tiers classique :

1.  **Couche Présentation (Frontend)** : Une interface utilisateur (non encore implémentée) qui consommera l'API backend.
2.  **Couche Logique Métier (Backend API)** : Une API RESTful développée en Java avec Spring Boot, qui contient toute la logique métier de l'application.
3.  **Couche de Données (Database)** : Une base de données PostgreSQL pour la persistance des données.

L'ensemble de l'environnement de développement est géré à l'aide de Docker.

## Stack Technologique

*   **Backend** : Java 17, Spring Boot 3
*   **Base de Données** : PostgreSQL
*   **API** : RESTful avec JSON
*   **Build Tool** : Maven
*   **Containerisation** : Docker, Docker Compose

## Lancement du Projet

### Prérequis

*   JDK 17 ou supérieur
*   Docker et Docker Compose

### Étapes

1.  **Démarrer la base de données** :
    À la racine du projet, exécutez la commande suivante pour démarrer le conteneur PostgreSQL en arrière-plan.

    ```bash
    docker-compose up -d
    ```

    La base de données sera accessible sur le port `5433` de votre machine locale.

2.  **Lancer l'API Backend** :
    Naviguez dans le répertoire de l'API et utilisez le wrapper Maven pour lancer l'application.

    ```bash
    cd VehiClub-API
    ./mvnw spring-boot:run
    ```

    L'API sera alors disponible à l'adresse `http://localhost:8080`.

## Endpoints de l'API

### Créer un Véhicule

Crée un nouveau véhicule en spécifiant son nom et son type de motorisation.

*   **URL** : `/api/vehicules`
*   **Méthode** : `POST`
*   **Body** (exemple) :

    ```json
    {
        "nom": "Mon bolide",
        "type": "essence"
    }
    ```
    Les types valides sont : `essence`, `electrique`.

*   **Exemple avec `curl`** :

    ```bash
    curl -X POST http://localhost:8080/api/vehicules \
    -H "Content-Type: application/json" \
    -d '{"nom": "Ma Super Auto", "type": "electrique"}'
    ```
