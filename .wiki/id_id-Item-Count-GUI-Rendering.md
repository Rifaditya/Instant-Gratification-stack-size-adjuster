# Rendering GUI Jumlah Item

## Gambaran Umum

Pada Minecraft standar, teks jumlah item di slot inventaris disesuaikan untuk angka kecil ($\le 64$). Saat tumpukan item mencapai 4, 6, atau 9 digit (contohnya `1000000`), teks akan meluber keluar dari batas slot 16x16 piksel.

---

## 🧮 Algoritma Pengecilan Font Dinamis

`GuiGraphicsExtractorMixin` mengalihkan rendering slot ke `ItemCountRenderer.renderItemCount`:

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

### Perhitungan Skala

Dengan lebar teks $W_{\text{text}}$ dan batas lebar slot $W_{\max} = 16.0\text{px}$:

$$\text{Faktor Skala } S = \begin{cases} 1.0 & \text{jika } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{jika } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ Tabel Perbandingan Skala Teks

| Jumlah Item | Panjang Karakter | Lebar Standar ($W_{\text{text}}$) | Skala Terapan ($S$) | Perilaku Tampilan |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 karakter | $\sim 12\text{px}$ | $1.00$ | Ukuran font standar vanilla |
| `1000` | 4 karakter | $\sim 24\text{px}$ | $0.66$ | Dikecilkan dinamis agar pas di slot |
| `1000000` | 7 karakter | $\sim 42\text{px}$ | $0.38$ | Tampilan presisi tinggi yang rapi |
