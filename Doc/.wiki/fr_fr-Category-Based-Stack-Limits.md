# Limites d'empilement par catégorie

## Fonctionnement du système

Minecraft classe nativement les objets en trois échelons via `DataComponents.MAX_STACK_SIZE` :
1. **Empilables par 64** : Blocs de construction, ressources, lingots, pierres.
2. **Empilables par 16** : Perles de l'Ender, boules de neige, seaux, œufs, panneaux.
3. **Objets uniques (non empilables)** : Outils, armes, armures, potions, selles, wagonnets.

---

## 🧮 Organigramme de calcul de taille de pile

```
                 +--------------------------------+
                 |    Calcul de taille de pile    |
                 +--------------------------------+
                                  |
                                  v
                  [ Vérifier remplacements actifs ]
                  (ex. Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
         Remplacement trouvé ?            Aucun remplacement
                 |                                 |
                 v                                 v
         Renvoyer limite modifiée         Vérifier valeur vanilla
                                             (DataComponents)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                Vanilla >= 64                 Vanilla >= 16                 Vanilla == 1
                     |                             |                             |
                     v                             v                             v
           Renvoyer `items_64_limit`     Renvoyer `items_16_limit`     Renvoyer `items_1_limit`
```

---

## 💻 Logique Java exécutée

Le calcul s'opère dans `StackSizeManager.getModifiedStackSize` :

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // Appliquer les remplacements d'addons (ex. Potion Stacker)
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ Recommandations par catégorie

| Catégorie | Valeur par défaut | Maximum recommandé | Profil de performance |
| :--- | :--- | :--- | :--- |
| **Empilables par 64** | `128` | $39\,768\,215$ | Excellente. Des millions d'objets sans saccades. |
| **Empilables par 16** | `32` | $39\,768\,215$ | Échelonnage idéal pour perles et œufs. |
| **Objets uniques** | `1` | $39\,768\,215$ | Permet d'empiler outils et potions. |
