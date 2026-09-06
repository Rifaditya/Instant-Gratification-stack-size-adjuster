# Panduan Integrasi Mod Konsumen

## Gambaran Umum

Panduan ini menerangkan cara pengembang addon mengintegrasikan mod mereka dengan **Stack Size Adjuster**.

---

## 🛠️ Panduan Langkah demi Langkah

### Langkah 1: Tambahkan Dependensi di `fabric.mod.json`

Tambahkan `stack-size-adjuster` ke blok `depends` atau `suggests`:

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### Langkah 2: Daftarkan Override Batas Tumpukan

Dalam `ModInitializer` Anda:

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Aturan tumpukan ramuan lempar
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // Ramuan lempar hingga 8
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // Totem hingga 16
            }
            return originalSize; // Item lain mengikuti GameRules umum
        });
    }
}
```

---

### Langkah 3: Mengambil Batas Aktif secara Terprogram

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
