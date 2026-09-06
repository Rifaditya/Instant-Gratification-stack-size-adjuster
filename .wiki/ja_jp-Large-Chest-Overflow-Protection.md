# ラージチェストオーバーフロー保護

## 整数オーバーフローの危険性

Java および Minecraft では、インベントリのスタック計算に 32 ビット符号付き整数（`int`）を使用しています。表現可能な最大値は以下の通りです：
$$I_{\max} = 2^{31} - 1 = 2,147,483,647$$

54 スロットを持つ**ラージチェスト**に限界値までアイテムが詰め込まれた場合、コンテナ全体の合計数を求める際に 54 スロット分が加算されます。

---

## 🧮 安全しきい値の計算

54 スロットの各枠にサイズ $L$ のスタックが入っている場合：
$$\text{チェスト内総アイテム数} = 54 \times L$$

合計が $I_{\max}$ を超えないための条件：
$$54 \times L \le 2,147,483,647$$
$$L \le \frac{2,147,483,647}{54} \approx 39,768,215.68$$

したがって、**絶対的な安全上限値**は以下となります：
$$L_{\text{safe}} = 39,768,215$$

### オーバーフロー影響一覧表

| スタック制限 ($L$) | 54 スロット合計アイテム数 | 整数状態 | リスク |
| :--- | :--- | :--- | :--- |
| **128（デフォルト）** | $6,912$ | 安全 ($< 21.4\text{ 億}$) | リスクなし |
| **1,000,000** | $54,000,000$ | 安全 ($< 21.4\text{ 億}$) | リスクなし |
| **39,768,215** | $2,147,483,610$ | 安全 ($< 21.4\text{ 億}$) | **推奨される最大安全上限** |
| **40,000,000** | $2,160,000,000$ | **オーバーフロー発生！**（$-2,134,967,296$ へ反転） | アイテム消滅 / コンテナ破損 |

---

## 🛠️ 倍精度クイッククラフト計算

`AbstractContainerMenuMixin` において、ドラッグ時の分配計算を 64 ビット `double` 精度に書き換えることで、巨大スタック操作時の計算誤差や負数への反転を防止しています：

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
