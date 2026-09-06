# Integrationsleitfaden für Mod-Entwickler

## Übersicht

Dieser Leitfaden beschreibt, wie Drittanbieter-Mods und Addons mit **Stack Size Adjuster** interagieren können.

---

## 🛠️ Schritt-für-Schritt-Anleitung

### Schritt 1: Abhängigkeit in `fabric.mod.json` eintragen

Fügen Sie `stack-size-adjuster` zu `depends` oder `suggests` hinzu:

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### Schritt 2: Eigenen Stack-Size-Override registrieren

In Ihrem `ModInitializer`:

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Eigene Stapelregeln für Wurftränke
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // Wurftränke bis 8 stapeln
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // Totems bis 16 stapeln
            }
            return originalSize; // Unberührte Items behalten GameRule-Limits
        });
    }
}
```

---

### Schritt 3: Aktives Limit abfragen

Um das aktuell wirksame Stapellimit für ein Item im Code abzufragen:

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
