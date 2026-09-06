# Guía de integración para mods consumidores

## Resumen

Esta guía explica cómo los desarrolladores de mods y addons pueden integrarse con **Stack Size Adjuster** para establecer límites especiales.

---

## 🛠️ Guía paso a paso

### Paso 1: Añadir dependencia en `fabric.mod.json`

Añade `stack-size-adjuster` al bloque `depends` o `suggests`:

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### Paso 2: Registrar una anulación de tamaño de pila

En el `ModInitializer` de tu mod:

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Registrar regla personalizada para pociones arrojadizas
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // Límite de 8 para pociones arrojadizas
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // Permitir que los tótems se apilen hasta 16
            }
            return originalSize; // Devolver tamaño original para el resto
        });
    }
}
```

---

### Paso 3: Consultar límites activos por código

Para consultar el límite efectivo de un objeto en el código:

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
