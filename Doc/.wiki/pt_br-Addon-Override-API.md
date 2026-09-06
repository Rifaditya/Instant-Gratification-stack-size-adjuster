# API de sobreposição para addons

## Visão geral

O Stack Size Adjuster oferece um mecanismo de extensão leve via `StackSizeManager.registerOverride`. Mods e complementos externos (ex: **Potion Stacker Addon**, **Stew Stacker Addon**) podem registrar regras personalizadas.

---

## 🛠️ Assinatura de registro da API

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### Parâmetros da função
- `Item`: A instância do item avaliado.
- `Integer`: O tamanho original de empilhamento do jogo.
- `Integer` (Retorno): O tamanho de empilhamento modificado (ou original se inalterado).

---

## 💻 Exemplo de implementação em addon

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // Permitir poções empilhadas até 16
            if (item instanceof PotionItem) {
                return 16;
            }
            // Permitir pérolas do Ender empilhadas até 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // Retornar tamanho original para seguir as GameRules
            return originalSize;
        });
    }
}
```

---

## 🔄 Ordem de execução

1. Se a sobreposição retornar um valor **diferente** de `originalSize`, esse valor é adotado imediatamente.
2. Se nenhuma sobreposição alterar o item, o `StackSizeManager` aplica as regras ativas (`items_64_limit`, `items_16_limit` ou `items_1_limit`).
