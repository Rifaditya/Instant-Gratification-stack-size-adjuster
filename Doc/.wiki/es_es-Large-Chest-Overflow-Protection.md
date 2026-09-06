# Protección contra desbordamiento de cofres grandes

## El problema del desbordamiento de enteros

En Java y Minecraft, los cálculos de inventario utilizan enteros de 32 bits con signo (`int`). El valor máximo representable es:
$$I_{\max} = 2^{31} - 1 = 2,147,483,647$$

Cuando un **cofre grande** (54 ranuras de inventario) contiene pilas máximas de objetos, el cálculo del total suma las 54 ranuras.

---

## 🧮 Cálculo del umbral de seguridad

Si cada una de las 54 ranuras contiene una pila de tamaño $L$:
$$\text{Total objetos en cofre} = 54 \times L$$

Para evitar que supere $I_{\max}$:
$$54 \times L \le 2,147,483,647$$
$$L \le \frac{2,147,483,647}{54} \approx 39,768,215.68$$

Por tanto, el **umbral absoluto de seguridad** es:
$$L_{\text{safe}} = 39,768,215$$

### Tabla de impacto del desbordamiento

| Límite de pila ($L$) | Total objetos en cofre de 54 | Estado del entero | Riesgo |
| :--- | :--- | :--- | :--- |
| **128 (Predeterminado)** | $6,912$ | Seguro ($< 2.14\text{B}$) | Cero riesgo |
| **1,000,000** | $54,000,000$ | Seguro ($< 2.14\text{B}$) | Cero riesgo |
| **39,768,215** | $2,147,483,610$ | Seguro ($< 2.14\text{B}$) | **Máximo seguro recomendado** |
| **40,000,000** | $2,160,000,000$ | **¡Desbordamiento!** (Pasa a $-2,134,967,296$) | Pérdida de objetos / Corrupción |

---

## 🛠️ Matemáticas de crafteo rápido de doble precisión

En `AbstractContainerMenuMixin`, la división de crafteo rápido se reescribe para utilizar precisión de 64 bits (`double`), evitando la pérdida de precisión y valores negativos al arrastrar grandes pilas:

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
