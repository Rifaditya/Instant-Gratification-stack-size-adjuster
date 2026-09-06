# Renderização da quantidade de itens na GUI

## Visão geral

No Minecraft original, a exibição numérica de itens nos slots é formatada para valores pequenos ($\le 64$). Quando a quantidade atinge 4, 6 ou 9 dígitos (ex: `1000000`), o texto ultrapassa o slot de 16x16 pixels.

---

## 🧮 Algoritmo de redução dinâmica de fonte

O mixin `GuiGraphicsExtractorMixin` intercepta a renderização do slot e repassa para `ItemCountRenderer.renderItemCount`:

```java
public class ItemCountRenderer {
    public static void renderItemCount(GuiGraphicsExtractor graphics, Font font, ItemStack itemStack, int x, int y, String countText) {
        if (itemStack.getCount() != 1 || countText != null) {
            String amount = countText == null ? String.valueOf(itemStack.getCount()) : countText;
            int textWidth = font.width(amount);
            
            float maxAllowedWidth = 16.0f;
            if (textWidth > maxAllowedWidth) {
                float scale = maxAllowedWidth / textWidth;
                
                Matrix3x2fStack pose = graphics.pose();
                pose.pushMatrix();
                
                float targetRight = x + 17;
                float targetBottom = y + 9;
                
                pose.translate(targetRight, targetBottom);
                pose.scale(scale, scale);
                
                graphics.text(font, amount, -textWidth, 0, -1, true);
                
                pose.popMatrix();
            } else {
                graphics.text(font, amount, x + 17 - textWidth, y + 9, -1, true);
            }
        }
    }
}
```

### Cálculo do fator de escala

Sendo $W_{\text{text}}$ a largura da fonte em pixels e $W_{\max} = 16.0\text{px}$:

$$\text{Fator de escala } S = \begin{cases} 1.0 & \text{se } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{se } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ Tabela de comparação de escala de texto

| Quantidade | Caracteres | Largura padrão ($W_{\text{text}}$) | Escala ($S$) | Comportamento visual |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 caracteres | $\sim 12\text{px}$ | $1.00$ | Tamanho original do jogo |
| `1000` | 4 caracteres | $\sim 24\text{px}$ | $0.66$ | Reduzido para caber no slot |
| `1000000` | 7 caracteres | $\sim 42\text{px}$ | $0.38$ | Compacto e de alta precisão |
