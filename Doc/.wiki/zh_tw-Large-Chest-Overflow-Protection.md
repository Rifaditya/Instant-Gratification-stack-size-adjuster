# 大型箱子防溢位保護與數學安全

## 整數溢位問題

在 Java 與 Minecraft 底層，物品欄堆疊計算使用 32 位元有號整數（`int`）。32 位元有號整數能表示的最大數值為：
$$I_{\max} = 2^{31} - 1 = 2,147,483,647$$

當一個擁有 54 個槽位的**大箱子**裝滿達到上限的物品時，計算容器內部的物品總數需要對全部 54 個槽位進行加總。

---

## 🧮 安全閾值計算

如果 54 槽位大箱子中的每個槽位都容納上限為 $L$ 的堆疊：
$$\text{箱子總物品數} = 54 \times L$$

為保證 $\text{箱子總物品數}$ 不超過 $I_{\max}$：
$$54 \times L \le 2,147,483,647$$
$$L \le \frac{2,147,483,647}{54} \approx 39,768,215.68$$

因此，堆疊限制的**絕對安全閾值**為：
$$L_{\text{safe}} = 39,768,215$$

### 溢位影響對照表

| 堆疊上限 ($L$) | 54 槽位大箱子總物品數 | 整數狀態 | 風險等級 |
| :--- | :--- | :--- | :--- |
| **128（預設值）** | $6,912$ | 安全 ($< 21.4\text{ 億}$) | 零風險 |
| **1,000,000** | $54,000,000$ | 安全 ($< 21.4\text{ 億}$) | 零風險 |
| **39,768,215** | $2,147,483,610$ | 安全 ($< 21.4\text{ 億}$) | **推薦最高安全上限** |
| **40,000,000** | $2,160,000,000$ | **發生溢位！**（回繞至 $-2,134,967,296$） | 物品消失 / 容器資料損壞 |

---

## 🛠️ 雙精度快速合成數學計算

在 `AbstractContainerMenuMixin` 中，原版的快速合成除法運算被重寫為採用 64 位元 `double` 雙精度浮點數學，防止在拖曳超大堆疊時發生精度丟失和負數回繞：

```java
@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @Overwrite
    public static int getQuickCraftPlaceCount(int quickCraftSlotsSize, int quickCraftingType, ItemStack itemStack) {
        return switch (quickCraftingType) {
            case 0 -> (int) ((double) itemStack.getCount() / (double) quickCraftSlotsSize);
            case 1 -> 1;
            case 2 -> itemStack.getMaxStackSize();
            default -> itemStack.getCount();
        };
    }
}
```
