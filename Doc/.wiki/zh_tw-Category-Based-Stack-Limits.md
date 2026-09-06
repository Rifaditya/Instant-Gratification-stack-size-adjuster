# 基於類別的堆疊限制

## 系統概述

Minecraft 原版透過 `DataComponents.MAX_STACK_SIZE` 將物品劃分為三個主要的自然堆疊等級：
1. **64 堆疊**：建築方塊、礦物資源、常規物品（例如鵝卵石、泥土、鐵錠）。
2. **16 堆疊**：終界珍珠、雪球、鐵桶、雞蛋、告示牌。
3. **1 堆疊（不可堆疊）**：工具、武器、盔甲、藥水、鞍、礦車。

---

## 🧮 堆疊上限判定流程圖

```
                 +--------------------------------+
                 |       物品堆疊上限計算流程        |
                 +--------------------------------+
                                  |
                                  v
                    [ 檢查已註冊的覆蓋函式 ]
                   (例如 Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
            已符合覆蓋規則?                       無符合覆蓋
                 |                                 |
                 v                                 v
           返回自訂上限值                     檢查原版自然預設屬性
                                            (DataComponents)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                自然 >= 64                    自然 >= 16                    自然 == 1
                     |                             |                             |
                     v                             v                             v
           返回 `items_64_limit`         返回 `items_16_limit`         返回 `items_1_limit`
```

---

## 💻 核心 Java 計算邏輯

堆疊上限計算由 `StackSizeManager.getModifiedStackSize` 處理：

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // 應用來自附屬模組（例如 Potion Stacker）的已註冊覆蓋邏輯
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

## 🛠️ 類別上限推薦值

| 目標類別 | 預設值 | 推薦安全上限 | 效能概況 |
| :--- | :--- | :--- | :--- |
| **64 堆疊** | `128` | $39,768,215$ | 極高效。可平滑支援數百萬堆疊。 |
| **16 堆疊** | `32` | $39,768,215$ | 珍珠與雞蛋的極佳平滑縮放。 |
| **1 堆疊** | `1` | $39,768,215$ | 允許工具與藥水堆疊。附魔與耐久損耗安全合併。 |
