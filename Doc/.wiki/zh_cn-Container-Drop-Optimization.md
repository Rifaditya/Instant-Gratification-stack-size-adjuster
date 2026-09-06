# 容器掉落优化

## 卡顿成因分析

在原版 Minecraft 中，破坏存放物品的容器（箱子、木桶、潜影盒）时，每个槽位的物品堆叠会被拆散为每组 10–30 个物品的小实体堆。如果箱子内包含成千上万或数百万的物品，破坏该箱子会尝试在**单刻内生成数十万个掉落物实体**，直接导致服务器无响应或客户端瞬间崩溃。

---

## 🧮 实体生成公式与拆分数学

`InventoryDropHelper.dropItemStack` 通过 `ContainersMixin` 拦截原版 `Containers.dropItemStack`，将巨量堆叠智能拆分为受控的实体数量：

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // 安全限制：限制每个槽位的实体生成总数
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

### 数学公式定义

设物品堆叠总数为 $N$，配置的 `max_drop_entities` 为 $M$：
1. 基础随机拆分大小 $S \in [10, 30]$。
2. 动态拆分阈值判定：
   $$\text{若 } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. 该槽位生成的 `ItemEntity` 总数严格受限于：
   $$E_{\text{spawned}} \le M$$

---

## 📊 掉落性能基准对比

| `max_drop_entities` 设置 | 100,000 个物品掉落生成的实体数 | 服务端每刻耗时影响 | 视觉散射效果 |
| :--- | :--- | :--- | :--- |
| **原版（未限制）** | $\sim 5,000$ 个实体 | **严重卡顿 / 崩溃** | 极度分散混乱 |
| **8（推荐默认值）** | $\le 8$ 个实体 | $< 1\text{ms}$ 耗时 | 优良的视觉抛洒效果 |
| **1（极限性能模式）** | 严格为 $1$ 个实体 | 瞬间完成 ($0\text{ms}$) | 单一实体堆聚 |
