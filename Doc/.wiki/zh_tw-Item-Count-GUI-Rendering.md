# 物品數量 GUI 渲染與字型縮放

## 概述

在原版 Minecraft 中，物品欄槽位上渲染的物品數量專為兩位數以內的小數值（$\le 64$）設計。當堆疊數量達到 4 位、6 位或 9 位（例如 `1000000`）時，文字會超出 16x16 像素的槽位邊界並遮擋相鄰物品欄槽位。

---

## 🧮 動態字型等比縮小演算法

`GuiGraphicsExtractorMixin` 攔截客戶端槽位渲染並委託給 `ItemCountRenderer.renderItemCount`：

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

### 縮放比例公式

設 $W_{\text{text}}$ 為物品數量字串的標準像素寬度，槽位最大允許寬度 $W_{\max} = 16.0\text{px}$：

$$\text{縮放因子 } S = \begin{cases} 1.0 & \text{若 } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{若 } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ 文字縮放效果對比

| 物品數量 | 字串長度 | 原生寬度 ($W_{\text{text}}$) | 應用縮放比例 ($S$) | 渲染效果 |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 字元 | $\sim 12\text{px}$ | $1.00$ | 原版標準字型大小 |
| `1000` | 4 字元 | $\sim 24\text{px}$ | $0.66$ | 動態縮小以完全貼合槽位 |
| `1000000` | 7 字元 | $\sim 42\text{px}$ | $0.38$ | 緊湊高精度渲染，絕不遮擋邊緣 |
