# 아이템 수량 GUI 렌더링

## 개요

바닐라 Minecraft의 슬롯 수량 텍스트는 2자리 이하의 작은 수($\le 64$)에 맞춰져 있습니다. 수량이 4자리, 6자리, 9자리(예: `1000000`)에 도달하면 16x16 픽셀 슬롯 경계를 벗어나 인접 슬롯을 가립니다.

---

## 🧮 동적 폰트 축소 알고리즘

`GuiGraphicsExtractorMixin`이 슬롯 렌더링을 가로채어 `ItemCountRenderer.renderItemCount`로 전달합니다:

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

### 스케일 팩터 수식

문자열 폭을 $W_{\text{text}}$, 슬롯 허용 폭을 $W_{\max} = 16.0\text{px}$라 할 때:

$$\text{스케일 팩터 } S = \begin{cases} 1.0 & W_{\text{text}} \le 16.0 \\ \frac{16.0}{W_{\text{text}}} & W_{\text{text}} > 16.0 \end{cases}$$

---

## 🖼️ 텍스트 스케일링 비교표

| 아이템 수량 | 문자열 길이 | 기본 폭 ($W_{\text{text}}$) | 적용 스케일 ($S$) | 렌더링 결과 |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2자 | $\sim 12\text{px}$ | $1.00$ | 표준 바닐라 폰트 크기 |
| `1000` | 4자 | $\sim 24\text{px}$ | $0.66$ | 슬롯에 맞게 자동 축소 |
| `1000000` | 7자 | $\sim 42\text{px}$ | $0.38$ | 깔끔한 고정밀 소형 렌더링 |
