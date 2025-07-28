# Application de Gestion de Cabinet Médical (JavaFX)

Une application de bureau complète pour la gestion des patients et de leurs consultations, développée avec JavaFX, Maven, et une base de données MySQL.

## Fonctionnalités

- **Gestion des Patients :** Ajouter, modifier, supprimer et rechercher des patients.
- **Gestion des Consultations :** Ajouter, modifier et supprimer des consultations liées à des patients existants.
- **Validation des Données :** Messages d'erreur clairs pour les champs vides, les dates futures, et autres contraintes.
- **Interface Réactive :** La liste des patients dans l'onglet des consultations se met à jour automatiquement.
- **Architecture Robuste :** Le projet suit le modèle de conception MVC avec une séparation claire des couches (DAO, Service, Controller).

## Technologies Utilisées

- Java 17
- JavaFX 17.0.11
- Maven
- MySQL (via MySQL Connector/J 8.0.33)

---

## Comment Lancer le Projet

### Prérequis

1.  **Installer MySQL :** Assurez-vous que MySQL est installé et en cours d'exécution sur votre machine.
2.  **Créer la Base de Données :** Exécutez le script SQL suivant pour créer la base de données et les tables nécessaires :
    ```sql
    CREATE DATABASE IF NOT EXISTS DBCABINET;
    USE DBCABINET;
    CREATE TABLE PATIENTS (
        ID_PATIENT BIGINT AUTO_INCREMENT PRIMARY KEY,
        NOM VARCHAR(255) NOT NULL,
        PRENOM VARCHAR(255) NOT NULL,
        TEL VARCHAR(50)
    );
    CREATE TABLE CONSULTATIONS (
        ID_CONSULTATION BIGINT AUTO_INCREMENT PRIMARY KEY,
        DATE_CONSULTATION DATE,
        DESCRIPTION TEXT,
        ID_PATIENT BIGINT,
        FOREIGN KEY (ID_PATIENT) REFERENCES PATIENTS(ID_PATIENT) ON DELETE CASCADE
    );
    ```

### Lancement

1.  Clonez ce dépôt.
2.  Ouvrez le projet dans IntelliJ IDEA.
3.  Attendez que Maven synchronise les dépendances (définies dans `pom.xml`).
4.  Modifiez les informations de connexion à la base de données dans le fichier `src/main/java/ma/chakiri/gestionconsultation/dao/DBConnection.java` si nécessaire (le mot de passe par défaut pour `root` est vide).
5.  Exécutez la méthode `main` dans le fichier `src/main/java/ma/chakiri/gestionconsultation/Main.java`.
