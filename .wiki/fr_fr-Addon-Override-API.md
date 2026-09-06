# API de remplacement pour addons

## Vue d'ensemble

Stack Size Adjuster propose une méthode d'extension via `StackSizeManager.registerOverride`. Les mods tiers et addons (ex. **Potion Stacker Addon**, **Stew Stacker Addon**) peuvent enregistrer des règles spécifiques pour certains objets.

---

## 🛠️ Signature de l'API

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### Paramètres de fonction
- `Item` : L'objet analysé.
- `Integer` : La taille de pile vanilla initiale.
- `Integer` (Retour) : La nouvelle limite de pile (ou valeur originale).

---

## 💻 Exemple d'intégration d'un addon

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // Permettre aux potions de s'empiler par 16
            if (item instanceof PotionItem) {
                return 16;
            }
            // Permettre aux perles de l'Ender de s'empiler par 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // Renvoyer la taille initiale pour déléguer aux GameRules
            return originalSize;
        });
    }
}
```

---

## 🔄 Ordre de priorité

1. Si un remplacement renvoie une valeur **différente** de `originalSize`, elle est immédiatement appliquée.
2. Si aucun remplacement ne modifie l'objet, `StackSizeManager` applique les GameRules en vigueur (`items_64_limit`, `items_16_limit`, `items_1_limit`).
