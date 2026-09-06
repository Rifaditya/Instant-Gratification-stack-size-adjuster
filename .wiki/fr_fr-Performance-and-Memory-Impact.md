# Performances et impact mémoire

## Vue d'ensemble

Stack Size Adjuster a été conçu pour un rendement optimal, sans surcharge mémoire ni gonflement NBT.

---

## ⚡ Optimisations de performance fondamentales

1. **Variables primitives** : Les limites résident dans des champs `volatile int` au sein de `StackSizeManager`, garantissant des lectures $O(1)$ sans verrou.
2. **Aucune inflation NBT** : Contrairement aux mods injectant des balises personnalisées, Stack Size Adjuster cible directement les codecs vanilla (`DataComponents.MAX_STACK_SIZE` et `ExtraCodecs.intRange`). Les mondes sauvegardés restent 100 % identiques au jeu de base.
3. **Zéro écouteur de tick** : Aucun code n'est exécuté dans les boucles de tick (`EndTick`, `WorldTick`).
4. **Itérations thread-safe** : Les règles d'addons sont maintenues dans une `CopyOnWriteArrayList` sécurisée.

---

## 📊 Mesures de performance

| Indicateur | Valeur observée | Règle technique |
| :--- | :--- | :--- |
| **Mémoire Heap** | $< 50\text{ Ko}$ | Zéro création d'objets lors des requêtes de pile |
| **Impact sur les sauvegardes** | $+0\text{ Octet}$ | Modification des codecs de composants natifs |
| **Surcoût sur le tick (MSPT)** | $0.00\text{ ms}$ | Lectures directes de types primitifs |
| **Bris de conteneur (MSPT)** | $< 0.50\text{ ms}$ | Découpage des entités via `InventoryDropHelper` |
