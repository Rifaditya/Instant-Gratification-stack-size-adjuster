# 取用端模組整合指南

## 概述

本指南詳細介紹第三方模組開發者與集合附屬模組如何與 **Stack Size Adjuster** 進行深度整合，以定義專屬堆疊上限或查詢當前生效的類別數值。

---

## 🛠️ 逐步整合操作指南

### 第 1 步：在 `fabric.mod.json` 中新增依賴聲明

將 `stack-size-adjuster` 新增至模組元資料的 `depends` 或 `suggests` 列表中：

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### 第 2 步：註冊自訂堆疊上限覆蓋規則

在模組的 `ModInitializer` 中呼叫 `StackSizeManager.registerOverride`：

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // 為飛濺藥水註冊專屬堆疊規則
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // 飛濺藥水上限設為 8
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // 允許不死圖騰堆疊至 16
            }
            return originalSize; // 未修改的物品繼續採用全域 GameRules 上限
        });
    }
}
```

---

### 第 3 步：以程式方式查詢當前生效的堆疊上限

如需在程式碼中動態取得特定物品堆當前的生效堆疊上限：

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
