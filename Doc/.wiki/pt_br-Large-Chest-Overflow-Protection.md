# Proteção contra transbordamento de baús grandes

## O problema do estouro de inteiros

Em Java e no Minecraft, cálculos de inventário utilizam inteiros com sinal de 32 bits (`int`). O valor máximo que pode ser representado é:
$$I_{\max} = 2^{31} - 1 = 2.147.483.647$$

Quando um **baú duplo** (54 slots) fica repleto de pilhas máximas, a contagem total soma todos os 54 slots.

---

## 🧮 Cálculo do limiar de segurança

Se cada um dos 54 slots contiver uma pilha de tamanho $L$:
$$\text{Total no baú} = 54 \times L$$

Para garantir que o total não ultrapasse $I_{\max}$:
$$54 \times L \le 2.147.483.647$$
$$L \le \frac{2.147.483.647}{54} \approx 39.768.215,68$$

Portanto, o **limiar de segurança absoluta** é:
$$L_{\text{safe}} = 39.768.215$$

### Tabela de impacto do transbordamento

| Limite de pilha ($L$) | Total no baú de 54 slots | Estado do inteiro | Risco |
| :--- | :--- | :--- | :--- |
| **128 (Padrão)** | $6.912$ | Seguro ($< 2,14\text{B}$) | Sem risco |
| **1.000.000** | $54.000.000$ | Seguro ($< 2,14\text{B}$) | Sem risco |
| **39.768.215** | $2.147.483.610$ | Seguro ($< 2,14\text{B}$) | **Teto máximo recomendado** |
| **40.000.000** | $2.160.000.000$ | **Transbordou!** (Muda para $-2.134.967.296$) | Itens sumindo / Corrupção |

---

## 🛠️ Criação rápida em ponto flutuante de dupla precisão

No `AbstractContainerMenuMixin`, o cálculo de divisão da criação rápida foi reescrito em precisão de 64 bits (`double`) para evitar valores negativos:

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
