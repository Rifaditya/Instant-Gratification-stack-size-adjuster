# Rendu du nombre d'objets dans l'interface

## Vue d'ensemble

Dans Minecraft de base, la police des nombres de pile est taillée pour deux chiffres ($\le 64$). Lorsque les piles atteignent 4, 6 ou 9 chiffres (ex. `1000000`), le texte déborde du cadre 16x16 pixels du slot.

---

## 🧮 Algorithme de réduction dynamique de police

`GuiGraphicsExtractorMixin` intercepte l'affichage et délègue à `ItemCountRenderer.renderItemCount` :

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

### Calcul de l'échelle

Pour une largeur en pixels $W_{\text{text}}$ et une largeur maximale $W_{\max} = 16.0\text{px}$ :

$$\text{Facteur d'échelle } S = \begin{cases} 1.0 & \text{si } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{si } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ Tableau comparatif de mise à l'échelle

| Quantité d'objets | Nombre de caractères | Largeur standard ($W_{\text{text}}$) | Échelle ($S$) | Comportement visuel |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 caractères | $\sim 12\text{px}$ | $1.00$ | Taille standard vanilla |
| `1000` | 4 caractères | $\sim 24\text{px}$ | $0.66$ | Réduction pour rester dans le slot |
| `1000000` | 7 caractères | $\sim 42\text{px}$ | $0.38$ | Affichage compact haute précision |
