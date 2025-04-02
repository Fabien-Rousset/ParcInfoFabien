# ParcInfo - Système de Gestion de Parc Informatique

## À propos du projet
ParcInfo est une application web développée avec Spring Boot pour la gestion efficace d'un parc informatique. Cette application permet de suivre et gérer l'ensemble des équipements informatiques, des utilisateurs et des périphériques au sein d'une organisation.

## Technologies utilisées
- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- Thymeleaf
- H2 Database
- Lombok

## Équipe de développement
Notre équipe est composée de 4 membres spécialisés :

- **Fabien** - Scrum Master & Responsable Partie Ordinateur
  - Gestion du développement des fonctionnalités liées aux ordinateurs
  - Coordination de l'équipe et méthodologie agile

- **Kenza** - Responsable Objets Nomades & Tests
  - Développement des fonctionnalités pour les appareils mobiles
  - Assurance qualité et tests

- **Benjamin** - Responsable Périphériques & Développement
  - Gestion des périphériques informatiques
  - Coordination technique du développement

- **Lucas** - Développeur Principal & Responsable Personne et Appareil
  - Développement des fonctionnalités principales
  - Gestion des utilisateurs et des relations personne-appareil

## Prérequis
- Java 21
- Maven
- IDE (IntelliJ IDEA recommandé)

## Installation
1. Cloner le repository
```bash
git clone [URL_DU_REPO]
```

2. Se déplacer dans le répertoire du projet
```bash
cd parcinfo
```

3. Compiler le projet
```bash
mvn clean install
```

4. Lancer l'application
```bash
mvn spring-boot:run
```

L'application sera accessible à l'adresse : `http://localhost:8080`

## Structure du projet
```
parcinfo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/parcinfo/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── ParcinfoApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## Contribution
Pour contribuer au projet :
1. Créer une branche pour votre fonctionnalité
2. Commit vos changements
3. Créer une Pull Request

## Licence
Ce projet est sous licence [MIT] (licence libre). Voir le fichier `LICENSE` pour plus de détails. 