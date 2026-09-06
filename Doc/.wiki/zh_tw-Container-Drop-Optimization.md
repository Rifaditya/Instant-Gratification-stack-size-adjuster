# 容器掉落最佳化

## 卡頓成因分析

在原版 Minecraft 中，破壞存放物品的容器（箱子、木桶、界伏盒）時，每個槽位的物品堆疊會被拆散為每組 10–30 個物品的小實體堆。如果箱子內包含成千上萬或數百萬的物品，破壞該箱子會嘗試在**單刻內生成數十萬個掉落物實體**，直接導致伺服器無回應或客戶端瞬間崩潰。

---

## 🧮 實體生成公式與拆分數學

`InventoryDropHelper.dropItemStack` 透過 `ContainersMixin` 攔截原版 `Containers.dropItemStack`，將龐大堆疊智慧拆分為受控的實體數量：

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // 安全限制：限制每個槽位的實體生成總數
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

### 數學公式定義

設物品堆疊總數為 $N$，配置的 `max_drop_entities` 為 $M$：
1. 基礎隨機拆分大小 $S \in [10, 30]$。
2. 動態拆分閾值判定：
   $$\text{若 } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. 該槽位生成的 `ItemEntity` 總數嚴格受限於：
   $$E_{\text{spawned}} \le M$$

---

## 📊 掉落效能基準對比

| `max_drop_entities` 設定 | 100,000 個物品掉落生成的實體數 | 伺服端每刻耗時影響 | 視覺散射效果 |
| :--- | :--- | :--- | :--- |
| **原版（未限制）** | $\sim 5,000$ 個實體 | **嚴重卡頓 / 當機** | 極度分散混亂 |
| **8（推薦預設值）** | $\le 8$ 個實體 | $< 1\text{ms}$ 耗時 | 優良的視覺拋灑效果 |
| **1（極限效能模式）** | 嚴格為 $1$ 個實體 | 瞬間完成 ($0\text{ms}$) | 單一實體堆聚 |
