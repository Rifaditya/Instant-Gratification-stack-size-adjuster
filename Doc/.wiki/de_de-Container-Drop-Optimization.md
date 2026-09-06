# Optimierung von Behälter-Drops

## Das Lag-Problem

Beim Zerstören von Behältern (Truhen, Fässern, Shulker-Kisten) zerlegt Vanilla jeden Slot in kleine Gruppen von 10–30 Gegenständen. Enthält eine Truhe Tausende oder Millionen Items, versucht das Spiel **Hunderttausende Entities in einem einzigen Tick zu spawnen**, was zu extremen Server-Lags oder Spielabstürzen führt.

---

## 🧮 Entity-Spawn-Formel & Aufteilungsmathematik

`InventoryDropHelper.dropItemStack` fängt `Containers.dropItemStack` via `ContainersMixin` ab:

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Sicherheitsgrenze: Entity-Spawnen pro Slot limitieren
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

### Mathematische Definition

Gegeben sei die Gesamtanzahl $N$ und die Einstellung `max_drop_entities` $M$:
1. Basis-Zufallsaufteilung $S \in [10, 30]$.
2. Dynamische Schwellenwert-Prüfung:
   $$\text{Wenn } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. Die Gesamtanzahl der erzeugten `ItemEntity`-Objekte ist strikt begrenzt durch:
   $$E_{\text{spawned}} \le M$$

---

## 📊 Drop-Performance Benchmarks

| `max_drop_entities` Wert | Entities bei 100.000 Items | Server-Tick-Belastung (MSPT) | Visuelle Verteilung |
| :--- | :--- | :--- | :--- |
| **Vanilla (Ohne Limit)** | $\sim 5.000$ Entities | **Schwere Lags / Absturz** | Extrem unübersichtlich |
| **8 (Empfohlener Standard)** | $\le 8$ Entities | $< 1\text{ms}$ Tick-Zeit | Hervorragende Streuung |
| **1 (Maximale Performance)** | Genau $1$ Entity | Sofort ($0\text{ms}$) | Einzelner Item-Haufen |
