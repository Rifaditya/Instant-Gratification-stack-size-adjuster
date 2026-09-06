# API de anulación para complementos

## Resumen

Stack Size Adjuster proporciona un mecanismo de extensión mediante `StackSizeManager.registerOverride`. Los mods y complementos de terceros (como **Potion Stacker Addon** o **Stew Stacker Addon**) pueden registrar funciones de anulación para objetos específicos.

---

## 🛠️ Firma de registro de la API

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### Parámetros de función
- `Item`: La instancia de `Item` que se está evaluando.
- `Integer`: El tamaño de apilamiento natural vanilla del objeto.
- `Integer` (Valor devuelto): El límite modificado (o el valor original si no se altera).

---

## 💻 Ejemplo de implementación en un addon

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // Permitir que las pociones se apilen hasta 16
            if (item instanceof PotionItem) {
                return 16;
            }
            // Permitir que las perlas de Ender se apilen hasta 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // Devolver tamaño original para seguir la regla global
            return originalSize;
        });
    }
}
```

---

## 🔄 Orden de ejecución de anulaciones

1. Si una función de anulación devuelve un valor **diferente** de `originalSize`, ese valor se aplica de inmediato.
2. Si ninguna anulación modifica el objeto, `StackSizeManager` recurre a las GameRules activas (`items_64_limit`, `items_16_limit` o `items_1_limit`).
