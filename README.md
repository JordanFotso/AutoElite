# VehiClub - Application de Vente de Véhicules en Ligne

Ce projet est une application web de vente de véhicules en ligne, conçue pour illustrer l'utilisation de divers patrons de conception (Design Patterns) dans une architecture moderne.

## Objectif du Projet

L'objectif principal de ce projet est de servir de démonstration pratique pour l'implémentation de patrons de conception GoF (Gang of Four) dans une application réelle. Chaque patron est utilisé pour résoudre un problème de conception spécifique au sein de l'API backend.

## Architecture

L'application est divisée en deux sous-projets principaux :

- **`VehiClub-API` (Backend)**: Une API RESTful développée en Java avec Spring Boot. Elle contient toute la logique métier et l'implémentation des patrons de conception.
- **`VehiClub-View` (Frontend)**: Une interface utilisateur développée avec React (Vite) et TypeScript, qui consomme l'API backend.

L'environnement de développement est containerisé à l'aide de Docker pour la base de données.

## Stack Technologique

*   **Backend**: Java 17, Spring Boot, Maven
*   **Frontend**: React, TypeScript, Vite
*   **Base de Données**: PostgreSQL
*   **Containerisation**: Docker, Docker Compose

## Lancement du Projet

Pour lancer le projet, veuillez suivre les instructions détaillées dans les `README` de chaque sous-projet :

1.  **[Instructions pour le Backend (VehiClub-API)](VehiClub-API/README.md)**
2.  **[Instructions pour le Frontend (VehiClub-View)](VehiClub-View/README.md)**

## Aperçu de l'Application

Voici un aperçu de l'interface utilisateur cible.

### Page d'Accueil
![Page d'Accueil](docs/images/accueil.png)

### Catalogue
![Page du Catalogue](docs/images/catalogue.png)

### Détail d'un Véhicule
![Page de Détail d'un Véhicule](docs/images/detail_vehicule.png)