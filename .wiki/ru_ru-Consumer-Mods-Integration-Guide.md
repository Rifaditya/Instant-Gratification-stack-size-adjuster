# Руководство по интеграции сторонних модов

## Обзор

В данном руководстве описывается, как разработчики сторонних модов и аддонов могут интегрироваться со **Stack Size Adjuster** для задания собственных лимитов стаков.

---

## 🛠️ Пошаговое руководство

### Шаг 1: Добавление зависимости в `fabric.mod.json`

Добавьте `stack-size-adjuster` в блок `depends` или `suggests`:

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### Шаг 2: Регистрация функции переопределения

В вашем `ModInitializer` вызовите `StackSizeManager.registerOverride`:

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Настройка размера стака для взрывных зелий
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // Лимит 8 для взрывных зелий
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // Стаковать тотемы бессмертия до 16
            }
            return originalSize; // Остальные предметы используют стандартные правила
        });
    }
}
```

---

### Шаг 3: Программный запрос активного лимита

Для получения текущего лимита стака в коде:

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
