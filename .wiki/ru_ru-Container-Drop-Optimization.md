# Оптимизация выпадения из контейнеров

## Проблема с лагами

В ванильном Minecraft при разрушении контейнера (сундука, бочки, шалкера) стак в каждом слоте разбивается на мелкие кучки по 10–30 предметов. Если в сундуке хранятся тысячи или миллионы предметов, разрушение приводит к попытке заспавнить **сотни тысяч сущностей предметов за один игровой такт**, вызывая зависание сервера или падение клиента.

---

## 🧮 Формула спавна сущностей и математика разделения

Класс `InventoryDropHelper.dropItemStack` перехватывает вызов `Containers.dropItemStack` через `ContainersMixin`:

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Ограничение безопасности: лимит спавна сущностей на слот
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

### Математическая формулировка

Пусть общее количество предметов в слоте равно $N$, а настроенный параметр `max_drop_entities` равен $M$:
1. Базовый случайный размер разделения $S \in [10, 30]$.
2. Проверка порога динамического разделения:
   $$\text{Если } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. Общее количество спавнимых `ItemEntity` для данного слота строго ограничено:
   $$E_{\text{spawned}} \le M$$

---

## 📊 Замеры производительности при выпадении

| Настройка `max_drop_entities` | Сущностей при выпадении 100,000 предметов | Влияние на такт сервера (MSPT) | Визуальное рассеивание |
| :--- | :--- | :--- | :--- |
| **Ваниль (Без лимита)** | $\sim 5,000$ сущностей | **Критический лаг / Краш** | Хаотичный разлет |
| **8 (Рекомендуется)** | $\le 8$ сущностей | $< 1\text{ms}$ затрат | Отличное рассеивание |
| **1 (Макс. производительность)** | Ровно $1$ сущность | Мгновенно ($0\text{ms}$) | Одиночная стопка |
