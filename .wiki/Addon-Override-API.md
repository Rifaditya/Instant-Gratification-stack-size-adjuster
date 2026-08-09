# Addon Override API

## Overview

Stack Size Adjuster provides an internal extension mechanism via `StackSizeManager.registerOverride`. Third-party mods and collection addons (e.g. **Potion Stacker Addon**, **Stew Stacker Addon**) can register item-specific or category-specific override functions to bypass global GameRule category limits.

---

## 🛠️ API Registration Signature

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### Function Parameters
- `Item`: The target `Item` instance being checked.
- `Integer`: The original natural stack size of the item.
- `Integer` (Return Value): The modified stack size limit (or original value if unhandled).

---

## 💻 Example Addon Implementation

Below is an example of how an addon mod registers custom stack sizes for specific item types:

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // Allow potions to stack up to 16 regardless of global 1-stack limit
            if (item instanceof PotionItem) {
                return 16;
            }
            // Allow Ender Pearls to stack up to 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // Return original size to delegate back to global GameRule limits
            return originalSize;
        });
    }
}
```

---

## 🔄 Override Execution Order

1. If an override function returns a value **different** from `originalSize`, that modified value is returned immediately.
2. If no overrides change the stack size, `StackSizeManager` falls back to checking the active world GameRules (`items_64_limit`, `items_16_limit`, or `items_1_limit`).
