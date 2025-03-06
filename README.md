## Instructions pour l'exécution

### Compilation et exécution

Le projet utilise Gradle pour la gestion des dépendances, la compilation et l'exécution. Les commandes suivantes permettent de compiler et d'exécuter le projet :

**Bash :**

```
./gradlew build # Compile le projet. 
./gradlew run # Exécute le projet. 
./gradlew test # Exécute tous les tests unitaires du projet.
```

**Windows :**

```
gradlew.bat build # Compiler 
gradlew.bat run # Exécuter 
gradlew.bat test # Tests
```

### Structure des fichiers et configuration

Le programme peut nécessiter des fichiers de configuration (par exemple, les fichiers TOML pour les dispositions, le repertoires contenants les fichiers textes du corpus). Vous pouvez spécifier le chemin absolu de ces fichiers lors de l'exécution du programme, ou les placer dans le répertoire `app` du projet et donner seulement leurs noms.

---

# Rapport de projet

## Compléments en Programmation Orientée Objet

**Projet :** Analyseur de texte et évaluateur de disposition clavier

---

## Introduction

Ce document présente le travail réalisé pour le projet "Analyseur de texte et optimisateur de disposition clavier". Le projet a été réalisé en Java (version 21), avec une attention particulière portée à la modularité et la programmation orientée objet (POO). Nous avons structuré le développement en deux parties principales :

1. **Analyseur de fréquence de suites de caractères (n-grammes) dans un corpus de texte.**
2. **Évaluateur de disposition clavier basé sur les données générées par l'analyseur.**

---

## Fonctionnalités Implémentées

### Partie 1 : Analyseur de fréquence de n-grammes

#### Objectif

L'objectif de cette partie est d'analyser des fichiers texte afin d'extraire les statistiques de fréquence des n-grammes (ungrammes, bigrammes et trigrammes) et de sauvegarder les résultats dans un fichier CSV.

#### Méthodologie

Pour optimiser les performances, chaque fichier texte du corpus est traité par un thread distinct, permettant ainsi une analyse en parallèle. Les résultats de chaque thread sont ensuite fusionnés dans trois `ConcurrentHashMap` partagées, une pour chaque type de n-gramme (ungrammes, bigrammes et trigrammes). L'utilisation de `ConcurrentHashMap` assure la sécurité des données lors des accès concurrents.

#### Résultat final

Les statistiques sont exportées dans un fichier CSV contenant les colonnes suivantes :

- **Taille** : Le nombre total de caractères du corpus analysé.
- **Type** : Le type de n-gramme (unigramme, bigramme ou trigramme).
- **N-gramme** : La séquence de caractères constituant le n-gramme.
- **Fréquence** : Le nombre d'occurrences du n-gramme dans le corpus.

**Exemple de sortie CSV :**

```
Taille,3195 
Type,N-gramme,Fréquence 
Unigramme,"!",12 
Bigramme,"ag",3 
Trigramme,"key",1
```


#### Réalisation technique

- **Lecture des fichiers :** La lecture de chaque fichier texte est effectuée ligne par ligne afin d'optimiser la gestion de la mémoire, notamment pour les fichiers volumineux.
- **Gestion du parallélisme :** L'API `java.util.concurrent` est utilisée pour la gestion des threads, avec l'emploi de `ConcurrentHashMap` pour le stockage thread-safe des résultats intermédiaires.
- **Tests unitaires :** Des tests unitaires ont été implémentés pour assurer la correction du calcul des fréquences de n-grammes, la gestion des fichiers et le bon fonctionnement du parallélisme. Ces tests couvrent la majorité des cas d'utilisation et permettent de détecter les régressions lors des modifications du code.
- **Javadoc :** Une documentation Javadoc a été écrite pour l'ensemble des classes et des méthodes, facilitant la compréhension et la maintenance du code.

---

### Partie 2 : Évaluateur de disposition clavier

#### Objectif

Cette partie a pour objectif d'évaluer une disposition de clavier en se basant sur les statistiques de fréquence des n-grammes obtenues lors de la première phase (l'analyseur) et en tenant compte de critères ergonomiques prédéfinis.

#### Méthodologie

L'évaluation d'une disposition clavier s'effectue selon les étapes suivantes :

1. **Lecture de la disposition et de la géométrie du clavier (format TOML) :** Nous utilisons le format TOML, et plus précisément le format Kalamine, pour définir la disposition et la géométrie du clavier. Ce choix est motivé par sa large compatibilité, assurant une portabilité maximale et s'intégrant bien avec l'environnement Java.
2. **Gestion des touches mortes (dead keys) et des touches mortes personnalisées (custom dead keys) :** Une attention particulière est accordée à la gestion des touches nécessitant des séquences complexes, comme celles impliquant la touche Maj (Shift) combinée avec d'autres touches (par exemple, Shift + ^ pour obtenir un accent circonflexe). Un fichier YAML est utilisé pour stocker et gérer l'ensemble des combinaisons de touches mortes possibles.
3. **Lecture des données d'analyse (fichier CSV) :** Le fichier CSV généré par l'analyseur de fréquence de n-grammes (Partie 1) est lu pour récupérer les données statistiques nécessaires à l'évaluation.
4. **Calcul de la note globale :** Un algorithme calcule une note globale pour la disposition clavier en utilisant les fréquences des n-grammes et en appliquant des pondérations à différents critères ergonomiques. Ces critères incluent :
   - **Équilibre des mains :** Distribution de la frappe entre la main gauche et la main droite.
   - **Roulements :** Séquences de frappe alternant entre les doigts et les mains pour minimiser la fatigue.
   - **Minimisation des mouvements latéraux et verticaux :** Distances parcourues par les doigts entre les touches.
   - **Autres critères pertinents** (par exemple, utilisation des doigts les plus forts, positionnement des touches fréquentes sur la rangée de repos).

#### Résultat final

Le programme fournit une évaluation numérique globale pour chaque disposition de clavier analysée, permettant ainsi de comparer objectivement différentes configurations.

#### Réalisation technique

- **Dépendances utilisées :**
  - `com.moandjiezana.toml:toml4j:0.7.2` : pour la lecture des fichiers TOML.
  - `org.yaml:snakeyaml:2.0` : pour la lecture des fichiers YAML (gestion des touches mortes).
  - `com.opencsv:opencsv:5.7.1` : pour la lecture des fichiers CSV (données d'analyse).
- **Tests unitaires :** Des tests unitaires ont été écrits pour vérifier la justesse du calcul des coûts de mouvement, la gestion des touches mortes et l'intégration des données provenant de l'analyseur de n-grammes.
- **Javadoc :** Une documentation Javadoc a été écrite pour l'ensemble des classes et des méthodes, facilitant la compréhension et la maintenance du code.

---

### Partie 3 (non réalisée) : Optimisation de disposition clavier

Pour des raisons de temps, la partie concernant l'optimisation des dispositions clavier à l'aide d'un algorithme génétique n'a pas été implémentée dans le cadre de ce projet. Cette partie aurait eu pour but de générer automatiquement des dispositions de clavier optimisées en fonction des données d'analyse et des critères ergonomiques définis.

---

## Conclusion

Malgré l'absence de la partie optimisation, le projet atteint ses objectifs principaux en fournissant :

- Une analyse efficace et performante des fréquences de n-grammes dans des corpus textuels.
- Une évaluation des dispositions de clavier existantes, basée sur des critères ergonomiques et les données d'analyse.

### Points forts

- **Modularité du code :** L'architecture du projet favorise la réutilisation et la maintenance du code grâce à une conception modulaire.
- **Parallélisation efficace dans la Partie 1 :** L'utilisation de threads et de structures de données concurrentes permet une analyse rapide et efficace des corpus, même volumineux.
- **Utilisation de bibliothèques standard et reconnues :** L'intégration de bibliothèques telles que toml4j, snakeyaml et opencsv simplifie les tâches courantes et assure la robustesse du code.
- **Méthode d'évaluation précise :** L'approche basée sur le calcul du coût des mouvements pondéré par la fréquence des n-grammes offre une évaluation plus fine et plus pertinente que l'utilisation de simples pondérations arbitraires.

### Limites et axes d'amélioration

- **Implémentation de la Partie 3 (optimiseur de disposition clavier) :** L'implémentation de l'algorithme génétique pour l'optimisation des dispositions constitue une extension majeure et prioritaire pour les développements futurs.
- **Amélioration de l'interface utilisateur :** Une interface graphique ou un fichier de sortie plus détaillé (par exemple, un rapport HTML) pourrait améliorer significativement l'expérience utilisateur en rendant les résultats plus accessibles et plus interprétables.
- **Gestion plus fine des critères ergonomiques :** L'ajout de critères ergonomiques plus spécifiques et la possibilité de les configurer plus finement permettraient une évaluation encore plus précise des dispositions.
