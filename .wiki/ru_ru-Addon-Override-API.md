# API переопределения для аддонов

## Обзор

Stack Size Adjuster предоставляет механизм расширения через `StackSizeManager.registerOverride`. Сторонние моды и аддоны (например, **Potion Stacker Addon**, **Stew Stacker Addon**) могут регистрировать собственные функции переопределения для конкретных предметов или категорий.

---

## 🛠️ Сигнатура регистрации API

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### Параметры функции:
- `Item`: Целевой экземпляр предмета.
- `Integer`: Исходный ванильный размер стака.
- `Integer` (Возврат): Измененный лимит стака (или исходный, если предмет не обрабатывается).

---

## 💻 Пример реализации аддона

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // Разрешить зельям стаковаться до 16
            if (item instanceof PotionItem) {
                return 16;
            }
            // Разрешить жемчугу Края стаковаться до 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // Вернуть исходный размер для передачи в глобальные правила
            return originalSize;
        });
    }
}
```

---

## 🔄 Порядок выполнения переопределений

1. Если функция переопределения возвращает значение, **отличное** от `originalSize`, оно применяется немедленно.
2. Если ни одно переопределение не затронуло предмет, `StackSizeManager` проверяет активные правила мира (`items_64_limit`, `items_16_limit` или `items_1_limit`).
