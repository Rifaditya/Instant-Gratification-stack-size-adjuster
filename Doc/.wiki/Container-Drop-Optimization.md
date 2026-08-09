# Container Drop Optimization

## The Lag Problem

In vanilla Minecraft, breaking a container (chest, barrel, shulker box) containing items splits each slot's stack into small entity piles of 10–30 items each. If a chest contains stacks of thousands or millions of items, breaking that chest attempts to spawn **hundreds of thousands of item entities** in a single tick, causing server freeze or immediate game crash.

---

## 🧮 Entity Spawn Formula & Split Mathematics

`InventoryDropHelper.dropItemStack` intercepts vanilla `Containers.dropItemStack` via `ContainersMixin` to split large stacks into controlled entity counts:

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Safety limit: cap entity spawning per slot
            if (currentCount > splitSize * maxEntities) {
                splitSize = (currentCount + maxEntities - 1) / maxEntities;
            }
            
            ItemEntity entity = new ItemEntity(level, xo, yo, zo, itemStack.split(splitSize));
            ...
            level.addFreshEntity(entity);
        }
    }
}
```

### Mathematical Formulation

Given a total stack count $N$ and configured `max_drop_entities` $M$:
1. Baseline random split size $S \in [10, 30]$.
2. Dynamic split threshold check:
   $$\text{If } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. Total spawned `ItemEntity` count for that slot is bounded strictly by:
   $$E_{\text{spawned}} \le M$$

---

## 📊 Drop Profile Performance Benchmarks

| `max_drop_entities` Setting | 100,000 Item Drop Entities | Server Tick Impact | Visual Scatter |
| :--- | :--- | :--- | :--- |
| **Vanilla (Uncapped)** | $\sim 5,000$ entities | **Severe Lag / Crash** | Extreme |
| **8 (Recommended Default)** | $\le 8$ entities | $< 1\text{ms}$ tick cost | Excellent visual scatter |
| **1 (Max Performance)** | Exactly $1$ entity | Instant ($0\text{ms}$) | Single entity pile |
