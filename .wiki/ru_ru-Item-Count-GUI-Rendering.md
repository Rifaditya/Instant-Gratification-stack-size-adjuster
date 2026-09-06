# Рендеринг количества предметов в интерфейсе

## Обзор

В ванильном Minecraft отображение количества предметов в слотах рассчитано на малые числа ($\le 64$). Когда количество знаков достигает 4, 6 или 9 (например, `1000000`), текст выходит за пределы слота 16x16 пикселей и перекрывает соседние ячейки.

---

## 🧮 Алгоритм динамического масштабирования шрифта

Хук `GuiGraphicsExtractorMixin` перехватывает отрисовку слота и передает управление в `ItemCountRenderer.renderItemCount`:

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

### Расчет масштаба

Пусть $W_{\text{text}}$ — ширина текста в пикселях, а максимальная допустимая ширина $W_{\max} = 16.0\text{px}$:

$$\text{Коэффициент масштабирования } S = \begin{cases} 1.0 & \text{если } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{если } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ Сравнение масштабирования текста

| Число предметов | Длина строки | Базовая ширина ($W_{\text{text}}$) | Примененный масштаб ($S$) | Поведение отрисовки |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 символа | $\sim 12\text{px}$ | $1.00$ | Стандартный ванильный размер |
| `1000` | 4 символа | $\sim 24\text{px}$ | $0.66$ | Динамически сжат под слот |
| `1000000` | 7 символов | $\sim 42\text{px}$ | $0.38$ | Компактный высокоточный шрифт |
