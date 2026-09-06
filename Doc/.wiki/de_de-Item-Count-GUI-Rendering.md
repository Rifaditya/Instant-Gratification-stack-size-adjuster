# GUI-Rendering der Gegenstandsanzahl

## Übersicht

In Vanilla Minecraft sind Stapelzahlen für zweistellige Werte ($\le 64$) formatiert. Erreichen Stapelgrößen 4, 6 oder 9 Stellen (z. B. `1000000`), überragt der Text das 16x16-Pixel-Raster des Slots.

---

## 🧮 Dynamischer Algorithmus zur Schriftenskalierung

`GuiGraphicsExtractorMixin` greift in die Slot-Darstellung ein und leitet an `ItemCountRenderer.renderItemCount` weiter:

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

### Skalierungsformel

Mit Textbreite $W_{\text{text}}$ und maximaler Slotbreite $W_{\max} = 16.0\text{px}$:

$$\text{Skalierungsfaktor } S = \begin{cases} 1.0 & \text{wenn } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{wenn } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ Textskalierungs-Vergleichstabelle

| Gegenstandsanzahl | Zeichenlänge | Standardbreite ($W_{\text{text}}$) | Skalierung ($S$) | Darstellungsverhalten |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 Zeichen | $\sim 12\text{px}$ | $1.00$ | Standard Vanilla-Schriftgröße |
| `1000` | 4 Zeichen | $\sim 24\text{px}$ | $0.66$ | Dynamisch verkleinert für Slot |
| `1000000` | 7 Zeichen | $\sim 42\text{px}$ | $0.38$ | Kompakte Hochpräzisions-Schrift |
