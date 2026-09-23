# Category-Based Stack Limits

## System Overview

Minecraft naturally categorizes items into three primary stack tiers via `DataComponents.MAX_STACK_SIZE` (in 1.21+ / 26.x) or `Item.getMaxStackSize` (in 1.20.1):
1. **64-Stackable**: Building blocks, resources, common items (e.g. Cobblestone, Dirt, Iron Ingot).
2. **16-Stackable**: Ender pearls, snowballs, buckets, eggs, signboards.
3. **1-Stackable (Unstackable)**: Tools, weapons, armor, potions, saddles, minecarts.

---

## 🏷️ Conventional Tag Exemption (`#c:stack_size_exempt`)

Items tagged under `#c:stack_size_exempt` (via conventional data packs or mod integrations) bypass dynamic stack size scaling entirely and maintain their vanilla natural limits. This allows modpack authors to protect custom items, backpacks, or fragile containers from scaling.

---

## 🧮 Stack Size Decision Flowchart

```
                 +--------------------------------+
                 |  Item Stack Size Calculation   |
                 +--------------------------------+
                                  |
                                  v
                [ #c:stack_size_exempt Tag? ] -----> Yes ----> Return Natural Default
                                  |
                                  v No
                   [ Check Registered Overrides ]
                   (e.g., Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
           Override Found?                 No Override
                 |                                 |
                 v                                 v
        Return Custom Limit           Inspect Natural Default
                                       (DataComponents or Item)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                Natural >= 64                 Natural >= 16                 Natural == 1
                     |                             |                             |
                     v                             v                             v
           Return `items_64_limit`       Return `items_16_limit`       Return `items_1_limit`
```

---

## 💻 Ground-Truth Java Logic

Stack size calculation is handled by `StackSizeManager.getModifiedStackSize`:

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // 0. Check Conventional Tag exemption (#c:stack_size_exempt)
    if (item != null && item.builtInRegistryHolder().is(C_STACK_SIZE_EXEMPT)) {
        return original;
    }

    // 1. Check custom overrides from addons (e.g. Stew Stacker, Potion Stacker)
    for (CustomStackSizeOverride override : CUSTOM_OVERRIDES) {
        int customSize = override.getCustomStackSize(item, original);
        if (customSize >= 0) {
            return customSize;
        }
    }

    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        int size = override.apply(item, original);
        if (size != original) {
            return size;
        }
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ Category Limit Recommendations

| Target Category | Default | Recommended Max | Performance Profile |
| :--- | :--- | :--- | :--- |
| **64-Stackable** | `128` | $39,768,215$ | High efficiency. Multi-million limits supported smoothly. |
| **16-Stackable** | `32` | $39,768,215$ | Smooth scaling for pearls and eggs. |
| **1-Stackable** | `1` | $39,768,215$ | Allows tools/potions to stack. Enchants & damage merge safely. |
