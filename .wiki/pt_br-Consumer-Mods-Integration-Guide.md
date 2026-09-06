# Guia de integração para mods consumidores

## Visão geral

Este documento descreve como outros mods podem se integrar com o **Stack Size Adjuster**.

---

## 🛠️ Passo a passo de integração

### Passo 1: Inserir dependência no `fabric.mod.json`

Adicione `stack-size-adjuster` no bloco `depends` ou `suggests`:

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### Passo 2: Registrar sobreposição de tamanho de pilha

No seu `ModInitializer`:

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Regra para poções arremessáveis
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // Poções arremessáveis até 8
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // Tótens empilhados até 16
            }
            return originalSize; // Itens sem alteração mantêm as regras
        });
    }
}
```

---

### Passo 3: Consultar limite ativo via código

Para consultar o limite vigente de um item programaticamente:

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
