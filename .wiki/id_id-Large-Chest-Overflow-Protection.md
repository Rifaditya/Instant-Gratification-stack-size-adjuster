# Perlindungan Luapan Peti Besar

## Masalah Luapan Integer

Di Java dan Minecraft, penghitungan inventaris menggunakan integer bertanda 32-bit (`int`). Nilai maksimum yang dapat ditampung adalah:
$$I_{\max} = 2^{31} - 1 = 2.147.483.647$$

Ketika sebuah **Peti Besar** (54 slot) terisi penuh dengan tumpukan maksimum, perhitungan total item menjumlahkan seluruh 54 slot tersebut.

---

## 🧮 Perhitungan Ambang Batas Aman

Jika setiap slot dari 54 slot berisi tumpukan berukuran $L$:
$$\text{Total Item di Peti} = 54 \times L$$

Agar total tidak melebihi $I_{\max}$:
$$54 \times L \le 2.147.483.647$$
$$L \le \frac{2.147.483.647}{54} \approx 39.768.215,68$$

Dengan demikian, **Ambang Batas Keamanan Mutlak** adalah:
$$L_{\text{safe}} = 39.768.215$$

### Tabel Dampak Luapan

| Batas Tumpukan ($L$) | Total Item di Peti 54 Slot | Status Integer | Risiko |
| :--- | :--- | :--- | :--- |
| **128 (Default)** | $6.912$ | Aman ($< 2,14\text{ Miliar}$) | Tanpa risiko |
| **1.000.000** | $54.000.000$ | Aman ($< 2,14\text{ Miliar}$) | Tanpa risiko |
| **39.768.215** | $2.147.483.610$ | Aman ($< 2,14\text{ Miliar}$) | **Batas Maksimal Aman yang Disarankan** |
| **40.000.000** | $2.160.000.000$ | **Meluap!** (Berbalik ke $-2.134.967.296$) | Item hilang / Data peti rusak |

---

## 🛠️ Crafting Cepat Presisi Ganda

Dalam `AbstractContainerMenuMixin`, pembagian crafting cepat diubah menggunakan perhitungan floating point 64-bit (`double`) untuk mencegah angka negatif:

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
