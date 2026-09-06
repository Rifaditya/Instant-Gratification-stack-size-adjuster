# API Override Pengaya

## Gambaran Umum

Stack Size Adjuster menyediakan mekanisme ekspansi via `StackSizeManager.registerOverride`. Pengembang addon pihak ketiga (seperti **Potion Stacker Addon** atau **Stew Stacker Addon**) dapat mendaftarkan aturan khusus per item.

---

## 🛠️ Tanda Tangan Registrasi API

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### Parameter:
- `Item`: Objek item yang sedang diperiksa.
- `Integer`: Batas tumpukan asli vanilla.
- `Integer` (Nilai Balik): Batas tumpukan yang diubah (atau nilai awal jika tidak diubah).

---

## 💻 Contoh Implementasi Addon

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // Izinkan ramuan bertumpuk hingga 16
            if (item instanceof PotionItem) {
                return 16;
            }
            // Izinkan ender pearl bertumpuk hingga 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // Kembalikan nilai asli agar ditangani oleh GameRules global
            return originalSize;
        });
    }
}
```

---

## 🔄 Urutan Prioritas

1. Jika fungsi override mengembalikan angka yang **berbeda** dari `originalSize`, nilai baru tersebut segera digunakan.
2. Jika tidak ada override yang mengubah item tersebut, `StackSizeManager` akan memeriksa GameRules yang aktif (`items_64_limit`, `items_16_limit`, atau `items_1_limit`).
