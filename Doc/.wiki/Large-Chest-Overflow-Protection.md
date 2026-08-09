# Large Chest Overflow Protection & Math Safety

## The Integer Overflow Problem

In Java and Minecraft, inventory stack calculations use 32-bit signed integers (`int`). The maximum representable value of a signed 32-bit integer is:
$$I_{\max} = 2^{31} - 1 = 2,147,483,647$$

When a **Large Chest** (54 inventory slots) contains maximum stacks of items, calculating the sum total of items inside the container sums across all 54 slots.

---

## 🧮 Safety Threshold Calculation

If every slot in a 54-slot Large Chest holds a stack of size $L$:
$$\text{Total Chest Items} = 54 \times L$$

To prevent $\text{Total Chest Items}$ from exceeding $I_{\max}$:
$$54 \times L \le 2,147,483,647$$
$$L \le \frac{2,147,483,647}{54} \approx 39,768,215.68$$

Thus, the **Absolute Safety Threshold** for stack limits is:
$$L_{\text{safe}} = 39,768,215$$

### Overflow Impact Table

| Stack Limit ($L$) | 54-Slot Chest Total Items | Integer Status | Risk |
| :--- | :--- | :--- | :--- |
| **128 (Default)** | $6,912$ | Safe ($< 2.14\text{B}$) | Zero risk |
| **1,000,000** | $54,000,000$ | Safe ($< 2.14\text{B}$) | Zero risk |
| **39,768,215** | $2,147,483,610$ | Safe ($< 2.14\text{B}$) | **Recommended Maximum Safe Cap** |
| **40,000,000** | $2,160,000,000$ | **Overflow!** (Wraps to $-2,134,967,296$) | Item deletion / container corruption |

---

## 🛠️ Double-Precision Quick-Crafting Math

In `AbstractContainerMenuMixin`, vanilla quick-crafting division math is overwritten to use 64-bit `double` precision floating point math to prevent precision loss and negative integer wrapping when dragging huge stack sizes:

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
