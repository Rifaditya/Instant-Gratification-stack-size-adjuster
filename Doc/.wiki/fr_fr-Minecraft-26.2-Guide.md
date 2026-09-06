# Guide Minecraft 26.2

| Attribut | Spécification |
| :--- | :--- |
| **Cible Minecraft** | 26.2 (Version stable) |
| **Version du mod** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*` (Toute version compatible 26.2) |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **Format d'identifiant** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## Vue d'ensemble

**Stack Size Adjuster** pour Minecraft 26.2 supprime les restrictions rigides de taille de pile. Il permet aux joueurs et administrateurs d'ajuster les plafonds de pile pour les objets s'empilant normalement par 64, 16 ou 1.

### Fonctionnalités phares en 26.2 :
1. **Échelonnage dynamique par catégorie** : Contrôle via GameRules des objets empilables par 64, 16 et outils/potions uniques.
2. **Prévention des lags de conteneurs** : Plafonne le nombre d'entités créées par slot lors de la destruction d'un conteneur (`max_drop_entities`).
3. **Protection anti-débordement d'entiers** : Évite de dépasser la limite des entiers signés 32 bits ($2,14\text{ milliards}$) dans les grands coffres de 54 slots grâce à un seuil sécurisé de $39\,768\,215$.
4. **Synchronisation serveur-client instantanée** : Paquet S2C `stack-size-adjuster:sync_limit` actualisant les menus sans reconnexion.

---

## Installation et configuration

1. Vérifiez l'installation de **Fabric Loader 0.19.1+** et de **Java 25**.
2. Déposez `stack-size-adjuster-1.4.16+26.2.jar` dans votre dossier `.minecraft/mods`.
3. Ajoutez les dépendances nécessaires :
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. Lancez votre jeu ou serveur.

---

## Garde de version à l'exécution (ModVersionGuard)

À l'initialisation, `ModVersionGuard` contrôle l'environnement :
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
En cas d'incompatibilité, le chargement est interrompu proprement avec un message explicite dans les logs.
