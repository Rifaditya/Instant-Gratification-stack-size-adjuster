# Consumer Mods Integration Guide

## Overview

This guide details how third-party mod developers and collection addons can integrate with **Stack Size Adjuster** to define specialized stack limits or inspect active categories.

---

## 🛠️ Step-by-Step Addon Integration Guide

### Step 1: Add Mod Dependency in `fabric.mod.json`

Add `stack-size-adjuster` to your mod's `depends` or `suggests` block:

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### Step 2: Register a Custom Stack Size Override

In your mod's `ModInitializer`, call `StackSizeManager.registerOverride`:

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Register custom stack size rules for splash potions
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // Custom limit of 8 for splash potions
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // Allow totems to stack up to 16
            }
            return originalSize; // Unmodified items retain category GameRule limits
        });
    }
}
```

---

### Step 3: Query Active Stack Size Limits Programmatically

To query the currently active limit for a specific item programmatically:

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
