# Guide d'intégration pour les mods consommateurs

## Vue d'ensemble

Ce guide explique comment interfacer des mods tiers avec **Stack Size Adjuster**.

---

## 🛠️ Étapes d'intégration

### Étape 1 : Ajouter la dépendance dans `fabric.mod.json`

Ajoutez `stack-size-adjuster` dans le bloc `depends` ou `suggests` :

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### Étape 2 : Enregistrer une règle de remplacement

Dans votre `ModInitializer` :

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Règle spécifique pour les potions jetables
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // Plafond à 8 pour potions jetables
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // Totems empilables par 16
            }
            return originalSize; // Conserver les règles par défaut
        });
    }
}
```

---

### Étape 3 : Consulter la limite active d'un objet

Pour obtenir par programmation la limite d'un objet :

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.ItemStack;

public class StackQueryUtil {
    public static int getEffectiveLimit(ItemStack stack) {
        int original = stack.getMaxStackSize();
        return StackSizeManager.getModifiedStackSize(stack.getItem(), original);
    }
}
```
