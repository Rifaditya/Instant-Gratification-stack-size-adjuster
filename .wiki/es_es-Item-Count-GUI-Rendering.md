# Renderizado de cantidad de objetos en GUI

## Resumen

En Minecraft vanilla, los números de recuento en las ranuras del inventario están diseñados para números pequeños ($\le 64$). Cuando los números alcanzan 4, 6 o 9 dígitos (por ejemplo, `1000000`), el texto desborda los límites de 16x16 píxeles de la ranura.

---

## 🧮 Algoritmo de reducción dinámica de fuente

`GuiGraphicsExtractorMixin` intercepta el renderizado de ranuras y lo delega en `ItemCountRenderer.renderItemCount`:

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

### Formulación del factor de escala

Siendo $W_{\text{text}}$ el ancho en píxeles de la fuente del texto y $W_{\max} = 16.0\text{px}$:

$$\text{Factor de escala } S = \begin{cases} 1.0 & \text{si } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{si } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ Tabla comparativa de escala de texto

| Cantidad de objetos | Longitud de cadena | Ancho estándar ($W_{\text{text}}$) | Escala aplicada ($S$) | Comportamiento |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 caracteres | $\sim 12\text{px}$ | $1.00$ | Tamaño estándar vanilla |
| `1000` | 4 caracteres | $\sim 24\text{px}$ | $0.66$ | Reducción dinámica para caber en la ranura |
| `1000000` | 7 caracteres | $\sim 42\text{px}$ | $0.38$ | Ajuste compacto de alta precisión |
