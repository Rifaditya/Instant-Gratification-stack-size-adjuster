# Category-Based Stack Limits

## System Overview

Minecraft naturally categorizes items into three primary stack tiers via `DataComponents.MAX_STACK_SIZE`:
1. **64-Stackable**: Building blocks, resources, common items (e.g. Cobblestone, Dirt, Iron Ingot).
2. **16-Stackable**: Ender pearls, snowballs, buckets, eggs, signboards.
3. **1-Stackable (Unstackable)**: Tools, weapons, armor, potions, saddles, minecarts.

---

## 🧮 Stack Size Decision Flowchart

```
                 +--------------------------------+
                 |  Item Stack Size Calculation   |
                 +--------------------------------+
                                  |
                                  v
                  [ Check Registered Overrides ]
                  (e.g., Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
           Override Found?                 No Override
                 |                                 |
                 v                                 v
        Return Custom Limit           Inspect Natural Default
                                       (DataComponents)
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

    // Apply registered overrides from addons (e.g. Potion Stacker)
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
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
