# Addon-Override-API

## Übersicht

Stack Size Adjuster bietet mit `StackSizeManager.registerOverride` eine Erweiterungsschnittstelle. Drittanbieter-Mods (z. B. **Potion Stacker Addon**, **Stew Stacker Addon**) können gegenstands- oder kategoriespezifische Overrides registrieren.

---

## 🛠️ API-Registrierungssignatur

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### Parameter:
- `Item`: Der geprüfte Gegenstand.
- `Integer`: Die natürliche Vanilla-Stapelgröße.
- `Integer` (Rückgabe): Die modifizierte Stapelgröße (oder Ursprungswert).

---

## 💻 Beispiel-Implementierung eines Addons

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // Tränke bis 16 stapeln
            if (item instanceof PotionItem) {
                return 16;
            }
            // Enderperlen bis 64 stapeln
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // Ursprungswert für globale GameRules beibehalten
            return originalSize;
        });
    }
}
```

---

## 🔄 Ausführungsreihenfolge

1. Gibt ein Override einen von `originalSize` **abweichenden** Wert zurück, wird dieser sofort übernommen.
2. Ändert kein Override den Wert, prüft `StackSizeManager` die aktiven GameRules (`items_64_limit`, `items_16_limit`, `items_1_limit`).
