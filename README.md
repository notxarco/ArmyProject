# Army Planner

## Description

**Army Planner** est une application Java qui permet de créer, gérer et visualiser une armée personnalisée dans le cadre de jeux de stratégie. L'utilisateur peut définir une armée, ajouter des groupes, y inclure différentes unités (infanterie ou véhicules), et calculer le coût total de l'armée en fonction des points maximums autorisés.

L'application assure également la validation des types d'unités et des rôles des véhicules tout en respectant les contraintes de points maximum.

---

## Fonctionnalités

- **Création d'une armée :** 
  - Définir le nom de l'armée.
  - Choisir une faction parmi les factions disponibles : Space Marines, Orks, Eldars, Tau, Necrons.
  - Spécifier un plafond de points maximum pour l'armée.

- **Gestion des groupes :**
  - Ajouter des groupes d'unités à l'armée.
  - Supprimer des groupes existants.

- **Gestion des unités :**
  - Ajouter de l'infanterie (ex. Soldats, Chefs) à un groupe avec un nom, un type, un coût unitaire et une quantité.
  - Ajouter des véhicules (ex. Transport, Attaque) avec un rôle, un coût unitaire, une capacité de transport (si applicable) et une quantité.
  - Supprimer des unités existantes d'un groupe.

- **Affichage détaillé de l'armée :**
  - Lister tous les groupes et les unités avec leurs coûts détaillés.
  - Calculer et afficher le coût total de l'armée en points.

- **Validation dynamique :**
  - Empêcher l'ajout d'unités ou de véhicules qui dépasseraient les points maximum de l'armée.
  - Vérifier la validité des types et des rôles des unités ajoutées.

---

## Exemple d'utilisation

### 1. Lancement de l'application

Lors du démarrage, l'utilisateur est invité à définir une armée :
Factions disponibles: Space Marines, Orks, Eldars, Tau, Necrons 
Nom de l'armée: Armée Alpha Faction: Space Marines Points maximum: 1000


### 2. Ajout d'un groupe et d'unités

**Ajout d'un groupe :**
Nom du groupe pour l'unité: Groupe A

Saisir 1.
Ajouter Infanterie Nom de l'infanterie: Soldats Coût de l'infanterie: 10 Type (Soldat/Lourd/Spécial/Chef): Soldat Nombre d'unités: 40


**Ajout de véhicules :**
Nom du groupe pour l'unité: Groupe A 2. Ajouter Véhicule Nom du véhicule: Rhino Coût du véhicule: 50 Rôle (Transport/Attaque): Transport Capacité de transport: 10 Nombre de véhicules: 2


### 3. Affichage de l'armée

En choisissant l'option d'affichage, voici un exemple de sortie :
Armée: Armée Alpha | Faction: Space Marines | Points Max: 1000 Groupe: Groupe A Infanterie : Soldat - Soldats (40 x 10 pts, total: 400 pts) Véhicule : Transport - Rhino (2 x 50 pts, capacité: 10, total: 100 pts) Points Totals: 500
