# 附属覆盖 API

## 概述

Stack Size Adjuster 通过 `StackSizeManager.registerOverride` 提供了轻量高效的内部扩展机制。第三方模组与集合附属（例如 **Potion Stacker Addon**、**Stew Stacker Addon**）可以注册特定物品或自定义类别的覆盖函数，绕过全局 GameRule 类别限制。

---

## 🛠️ API 注册方法签名

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### 函数参数说明
- `Item`：当前正在计算堆叠上限的目标 `Item` 实例。
- `Integer`：该物品的原版原生自然堆叠上限。
- `Integer`（返回值）：计算后的新堆叠上限（若不处理此物品则返回原生数值）。

---

## 💻 附属模组实现示例

以下为附属模组如何为特定物品注册自定义堆叠上限的代码示例：

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // 允许药水最多堆叠至 16，无视全局 1 堆叠游戏规则限制
            if (item instanceof PotionItem) {
                return 16;
            }
            // 允许末影珍珠堆叠至 64
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // 返回原生数值以交由全局 GameRules 处理
            return originalSize;
        });
    }
}
```

---

## 🔄 覆盖规则执行顺序

1. 如果某个覆盖函数返回了**不同于** `originalSize` 的数值，系统将直接采用该修改值并立即返回。
2. 如果没有任何已注册的覆盖函数对该物品做出修改，`StackSizeManager` 将回退到检查世界当前生效的 GameRules（`items_64_limit`、`items_16_limit` 或 `items_1_limit`）。
