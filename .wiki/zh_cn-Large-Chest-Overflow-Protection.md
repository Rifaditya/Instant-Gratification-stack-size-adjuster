# 大型箱子防溢出保护与数学安全

## 整数溢出问题

在 Java 和 Minecraft 底层，物品栏堆叠计算使用 32 位有符号整数（`int`）。32 位有符号整数能表示的最大数值为：
$$I_{\max} = 2^{31} - 1 = 2,147,483,647$$

当一个拥有 54 个槽位的**大箱子**装满达到上限的物品时，计算容器内部的物品总数需要对全部 54 个槽位进行求和。

---

## 🧮 安全阈值计算

如果 54 槽位大箱子中的每个槽位都容纳上限为 $L$ 的堆叠：
$$\text{箱子总物品数} = 54 \times L$$

为保证 $\text{箱子总物品数}$ 不超过 $I_{\max}$：
$$54 \times L \le 2,147,483,647$$
$$L \le \frac{2,147,483,647}{54} \approx 39,768,215.68$$

因此，堆叠限制的**绝对安全阈值**为：
$$L_{\text{safe}} = 39,768,215$$

### 溢出影响对照表

| 堆叠上限 ($L$) | 54 槽位大箱子总物品数 | 整数状态 | 风险等级 |
| :--- | :--- | :--- | :--- |
| **128（默认值）** | $6,912$ | 安全 ($< 21.4\text{ 亿}$) | 零风险 |
| **1,000,000** | $54,000,000$ | 安全 ($< 21.4\text{ 亿}$) | 零风险 |
| **39,768,215** | $2,147,483,610$ | 安全 ($< 21.4\text{ 亿}$) | **推荐最高安全上限** |
| **40,000,000** | $2,160,000,000$ | **发生溢出！**（回绕至 $-2,134,967,296$） | 物品消失 / 容器数据损坏 |

---

## 🛠️ 双精度快速合成数学计算

在 `AbstractContainerMenuMixin` 中，原版的快速合成除法运算被重写为采用 64 位 `double` 双精度浮点数学，防止在拖动超大堆叠时发生精度丢失和负数回绕：

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
