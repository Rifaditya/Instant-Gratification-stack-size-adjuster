# 附屬覆蓋 API

## 概述

Stack Size Adjuster 透過 `StackSizeManager.registerOverride` 提供了輕量高效的內部擴充機制。第三方模組與集合附屬（例如 **Potion Stacker Addon**、**Stew Stacker Addon**）可以註冊特定物品或自訂類別的覆蓋函式，繞過全域 GameRule 類別限制。

---

## 🛠️ API 註冊方法簽章

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### 函式參數說明
- `Item`：當前正在計算堆疊上限的目標 `Item` 實例。
- `Integer`：該物品的原版原生自然堆疊上限。
- `Integer`（回傳值）：計算後的新堆疊上限（若不處理此物品則回傳原生數值）。

---

## 💻 附屬模組實作範例

以下為附屬模組如何為特定物品註冊自訂堆疊上限的程式碼範例：

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // 允許藥水最多堆疊至 16，無視全域 1 堆疊遊戲規則限制
            if (item instanceof PotionItem) {
                return 16;
            }
            // 允許終界珍珠堆疊至 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // 回傳原生數值以交由全域 GameRules 處理
            return originalSize;
        });
    }
}
```

---

## 🔄 覆蓋規則執行順序

1. 如果某個覆蓋函式回傳了**不同於** `originalSize` 的數值，系統將直接採用該修改值並立即回傳。
2. 如果沒有任何已註冊的覆蓋函式對該物品做出修改，`StackSizeManager` 將回退到檢查世界當前生效的 GameRules（`items_64_limit`、`items_16_limit` 或 `items_1_limit`）。
