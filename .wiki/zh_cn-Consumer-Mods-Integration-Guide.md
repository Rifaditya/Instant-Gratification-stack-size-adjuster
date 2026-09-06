# 消费者模组整合指南

## 概述

本指南详细介绍第三方模组开发者与集合附属模组如何与 **Stack Size Adjuster** 进行深度集成，以定义专属堆叠上限或查询当前生效的类别数值。

---

## 🛠️ 逐步整合操作指南

### 第 1 步：在 `fabric.mod.json` 中添加依赖声明

将 `stack-size-adjuster` 添加至模组元数据的 `depends` 或 `suggests` 列表中：

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### 第 2 步：注册自定义堆叠上限覆盖规则

在模组的 `ModInitializer` 中调用 `StackSizeManager.registerOverride`：

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // 为喷溅药水注册专属堆叠规则
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // 喷溅药水上限设为 8
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // 允许不死图腾堆叠至 16
            }
            return originalSize; // 未修改的物品继续采用全局 GameRules 上限
        });
    }
}
```

---

### 第 3 步：以编程方式查询当前生效的堆叠上限

如需在代码中动态获取特定物品堆当前的生效堆叠上限：

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
