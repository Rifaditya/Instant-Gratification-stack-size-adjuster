# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

Bienvenue dans la documentation encyclopédique officielle de **Instant Gratification: Stack Size Adjuster**. Ce mod Fabric pour Minecraft permet aux administrateurs de serveur et aux joueurs de personnaliser dynamiquement la taille des piles d'objets selon trois catégories naturelles (objets empilables par 64, par 16 et non empilables) jusqu'à des quantités extrêmes sans gonflement NBT, tout en fournissant une optimisation des drops et une protection contre les débordements.

> 📌 **Avertissement sur le code source du dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en cours de développement en avance sur les versions publiques de CurseForge et Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Répertoire des versions Minecraft

* [[Guide Minecraft 26.2|fr_fr-Minecraft-26.2-Guide]] — Installation officielle, dépendances et configuration pour Minecraft 26.2.
* [[Matrice de compatibilité des versions|fr_fr-Version-Compatibility]] — Versions prises en charge, vérification `ModVersionGuard` et limites de dépendances.

---

## 🎮 Guides pour joueurs & administrateurs

* [[Référence des GameRules dynamiques|fr_fr-Dynamic-GameRules-Reference]] — Règles de jeu (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[Limites d'empilement par catégorie|fr_fr-Category-Based-Stack-Limits]] — Fonctionnement de l'échelonnage des piles de 64, 16 et objets uniques.
* [[Optimisation des drops de conteneurs|fr_fr-Container-Drop-Optimization]] — Limites d'apparition d'entités, découpage des slots et prévention des lags lors du bris de coffres.
* [[Protection contre le débordement des grands coffres|fr_fr-Large-Chest-Overflow-Protection]] — Mathématiques de débordement d'entiers (limite sécurisée de $39\,768\,215$) et craft rapide en double précision.
* [[Configuration ModMenu et YACL|fr_fr-ModMenu-and-YACL-Configuration]] — Menu de configuration en jeu (`stack-size-adjuster.json`) et intégration d'écran YACL 3.9.5.
* [[Rendu du nombre d'objets dans l'interface|fr_fr-Item-Count-GUI-Rendering]] — Réduction dynamique de la taille de police pour les grands nombres.
* [[Intégration avec Item Clumps|fr_fr-Item-Clumps-Integration]] — Fusion d'entités au sol en synergie avec Item Clumps.
* [[Dépannage et FAQ|fr_fr-Troubleshooting-and-FAQ]] — Questions fréquentes, prévention des débordements et synchronisation serveur-client.

---

## 💻 Référence technique & développeurs

* [[Configuration développeur et compilation|fr_fr-Developer-Setup-and-Building]] — Environnement JDK 25, Gradle 9.3+, Loom 1.15+ et compilation.
* [[Architecture et structure des packages|fr_fr-Architecture-and-Package-Layout]] — Arborescence du système, organisation des packages et modèle de concurrence.
* [[Référence des Mixins et points d'injection|fr_fr-Mixin-Reference-and-Hooks]] — Points d'injection dans `Item`, `ItemStack`, `Container`, `GiveCommand` et `DataComponents`.
* [[API de remplacement pour addons|fr_fr-Addon-Override-API]] — Enregistrement de remplacements personnalisés via `StackSizeManager.registerOverride`.
* [[Protocole de synchronisation réseau|fr_fr-Network-Sync-Protocol]] — Paquet S2C `stack-size-adjuster:sync_limit` et actualisation des menus en temps réel.
* [[Traitement de la commande Give|fr_fr-Give-Command-Handling]] — `GiveCommandHelper` personnalisé gérant des quantités massives sans plantage.
* [[Profils de comportement et conditions|fr_fr-Behavior-Profiles-and-Conditions]] — Machine à états de synchronisation des règles de jeu.
* [[Guide d'intégration pour les mods consommateurs|fr_fr-Consumer-Mods-Integration-Guide]] — Guide d'intégration complet pour développeurs tiers.
* [[Performances et impact mémoire|fr_fr-Performance-and-Memory-Impact]] — Zéro gonflement NBT et benchmarks de mémoire vive.
* [[Progrès et badges|fr_fr-Advancements-and-Badges]] — Matrice des progrès et parité avec le jeu de base.

---

## 📜 Droits d'auteur & Licence

Développé par **Dasik (Rifaditya)** sous licence **GNU General Public License v3.0 (GPLv3)**.
