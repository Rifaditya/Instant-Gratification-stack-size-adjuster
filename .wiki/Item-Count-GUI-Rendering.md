# Item Count GUI Rendering & Font Scaling

## Overview

In vanilla Minecraft, item count numbers rendered on inventory slots are formatted for small numbers ($\le 64$). When stack numbers reach 4, 6, or 9 digits (e.g. `1000000`), the rendered text overflows the 16x16 pixel slot bounds and overlaps neighboring inventory slots.

---

## 🧮 Dynamic Font Scale-Down Algorithm

`GuiGraphicsExtractorMixin` intercepts client slot rendering and delegates to `ItemCountRenderer.renderItemCount`:

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

### Scale Factor Formulation

Let $W_{\text{text}}$ be the font pixel width of the item count string, and $W_{\text{max}} = 16.0\text{px}$:

$$\text{Scale Factor } S = \begin{cases} 1.0 & \text{if } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{if } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ Text Scale Comparison Table

| Item Count | String Length | Standard Width ($W_{\text{text}}$) | Applied Scale ($S$) | Rendering Behavior |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 chars | $\sim 12\text{px}$ | $1.00$ | Standard Vanilla font size |
| `1000` | 4 chars | $\sim 24\text{px}$ | $0.66$ | Dynamically scaled to fit slot |
| `1000000` | 7 chars | $\sim 42\text{px}$ | $0.38$ | Compact high-precision fit |
