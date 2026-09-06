# 物品数量 GUI 渲染与字体缩放

## 概述

在原版 Minecraft 中，物品栏槽位上渲染的物品数量专为两位数以内的小数值（$\le 64$）设计。当堆叠数量达到 4 位、6 位或 9 位（例如 `1000000`）时，文本会超出 16x16 像素的槽位边界并遮挡相邻物品栏槽位。

---

## 🧮 动态字体等比缩小算法

`GuiGraphicsExtractorMixin` 拦截客户端槽位渲染并委托给 `ItemCountRenderer.renderItemCount`：

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

### 缩放比例公式

设 $W_{\text{text}}$ 为物品数量字符串的标准像素宽度，槽位最大允许宽度 $W_{\max} = 16.0\text{px}$：

$$\text{缩放因子 } S = \begin{cases} 1.0 & \text{若 } W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & \text{若 } W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ 文本缩放效果对比

| 物品数量 | 字符串长度 | 原生宽度 ($W_{\text{text}}$) | 应用缩放比例 ($S$) | 渲染效果 |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 字符 | $\sim 12\text{px}$ | $1.00$ | 原版标准字体大小 |
| `1000` | 4 字符 | $\sim 24\text{px}$ | $0.66$ | 动态缩小以完全贴合槽位 |
| `1000000` | 7 字符 | $\sim 42\text{px}$ | $0.38$ | 紧凑高精度渲染，绝不遮挡边缘 |
