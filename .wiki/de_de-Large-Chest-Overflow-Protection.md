# Überlaufschutz für große Truhen

## Das Problem des Ganzzahl-Überlaufs

In Java und Minecraft nutzen Inventare vorzeichenbehaftete 32-Bit-Ganzzahlen (`int`). Der Maximalwert beträgt:
$$I_{\max} = 2^{31} - 1 = 2.147.483.647$$

Wenn eine **große Truhe** (54 Slots) randvoll mit maximalen Stapeln gefüllt ist, summiert die Gesamtzählung über alle 54 Plätze.

---

## 🧮 Berechnung der Sicherheitsgrenze

Wenn jeder Slot der 54-Slot-Truhe einen Stapel der Größe $L$ fasst:
$$\text{Gesamtanzahl Truhe} = 54 \times L$$

Um ein Überschreiten von $I_{\max}$ zu verhindern:
$$54 \times L \le 2.147.483.647$$
$$L \le \frac{2.147.483.647}{54} \approx 39.768.215,68$$

Die **absolute Sicherheitsgrenze** für Stapel beträgt:
$$L_{\text{safe}} = 39.768.215$$

### Überlauf-Auswirkungstabelle

| Stapellimit ($L$) | Gesamtgegenstände (54 Slots) | Integer-Status | Risiko |
| :--- | :--- | :--- | :--- |
| **128 (Standard)** | $6.912$ | Sicher ($< 2,14\text{ Mrd.}$) | Kein Risiko |
| **1.000.000** | $54.000.000$ | Sicher ($< 2,14\text{ Mrd.}$) | Kein Risiko |
| **39.768.215** | $2.147.483.610$ | Sicher ($< 2,14\text{ Mrd.}$) | **Empfohlenes Maximum** |
| **40.000.000** | $2.160.000.000$ | **Überlauf!** (Springt auf $-2.134.967.296$) | Item-Verlust / Beschädigung |

---

## 🛠️ Quick-Crafting mit doppelter Genauigkeit

In `AbstractContainerMenuMixin` wird die Division beim Verteilen von Gegenständen auf 64-Bit `double` umgestellt, um negative Zahlen bei großen Mengen zu verhindern:

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
