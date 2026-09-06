# 基于类别的堆叠限制

## 系统概述

Minecraft 原版通过 `DataComponents.MAX_STACK_SIZE` 将物品分为三个主要的自然堆叠等级：
1. **64 堆叠**：建筑方块、矿物资源、常规物品（例如圆石、泥土、铁锭）。
2. **16 堆叠**：末影珍珠、雪球、铁桶、鸡蛋、告示牌。
3. **1 堆叠（不可堆叠）**：工具、武器、盔甲、药水、鞍、矿车。

---

## 🧮 堆叠上限判定流程图

```
                 +--------------------------------+
                 |       物品堆叠上限计算流程        |
                 +--------------------------------+
                                  |
                                  v
                    [ 检查已注册的覆盖函数 ]
                   (例如 Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
            已匹配覆盖规则?                       无匹配覆盖
                 |                                 |
                 v                                 v
           返回自定义上限值                    检查原版自然默认属性
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

## 💻 核心 Java 计算逻辑

堆叠上限计算由 `StackSizeManager.getModifiedStackSize` 处理：

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // 应用来自附属模组（例如 Potion Stacker）的已注册覆盖逻辑
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

## 🛠️ 类别上限推荐值

| 目标类别 | 默认值 | 推荐安全上限 | 性能概况 |
| :--- | :--- | :--- | :--- |
| **64 堆叠** | `128` | $39,768,215$ | 极高效。可平滑支持数百万堆叠。 |
| **16 堆叠** | `32` | $39,768,215$ | 珍珠与鸡蛋的极佳平滑缩放。 |
| **1 堆叠** | `1` | $39,768,215$ | 允许工具与药水堆叠。附魔与耐久损耗安全合并。 |
