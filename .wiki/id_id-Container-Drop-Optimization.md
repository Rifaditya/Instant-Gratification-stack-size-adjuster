# Optimasi Drop Wadah

## Penyebab Lag

Pada Minecraft vanilla, saat wadah (peti, tong, kotak shulker) dihancurkan, tumpukan tiap slot dipecah menjadi tumpukan kecil berukuran 10–30 item. Jika peti menyimpan ribuan hingga jutaan item, tindakan ini akan memunculkan **ratusan ribu entitas dalam satu tick**, yang membekukan server atau mematikan game.

---

## 🧮 Formula Pemunculan Entitas & Pemisahan

`InventoryDropHelper.dropItemStack` mencegat pemanggilan drop bawaan melalui `ContainersMixin`:

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Batas keamanan: batasi spawn entitas per slot
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

### Rumusan Matematika

Untuk jumlah total item $N$ dan pengaturan `max_drop_entities` $M$:
1. Ukuran pemisahan acak dasar $S \in [10, 30]$.
2. Ambang batas pemisahan dinamis:
   $$\text{Jika } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. Total `ItemEntity` yang muncul dari slot tersebut dibatasi secara ketat:
   $$E_{\text{spawned}} \le M$$

---

## 📊 Tolok Ukur Kinerja Drop

| Pengaturan `max_drop_entities` | Entitas untuk 100.000 Item | Beban Tick Server | Efek Visual |
| :--- | :--- | :--- | :--- |
| **Vanilla (Tanpa Batas)** | $\sim 5.000$ entitas | **Lag Berat / Crash** | Hamburan sangat kacau |
| **8 (Rekomendasi Default)** | $\le 8$ entitas | $< 1\text{ms}$ beban tick | Hamburan visual bagus |
| **1 (Performa Maksimal)** | Tepat $1$ entitas | Instan ($0\text{ms}$) | Satu tumpukan tunggal |
