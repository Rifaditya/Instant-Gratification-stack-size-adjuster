# アイテムカウント GUI レンダリング

## 概要

バニラの Minecraft では、インベントリスロット上に描画されるスタック数値は小さな桁（$\le 64$）を前提に設計されています。スタック数が 4 桁、6 桁、9 桁（例: `1000000`）に達すると、テキストが 16x16 ピクセルの枠をはみ出し、隣接スロットと重なってしまいます。

---

## 🧮 動的フォント縮小アルゴリズム

`GuiGraphicsExtractorMixin` はスロット描画をフックし、`ItemCountRenderer.renderItemCount` に委譲します：

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

### スケール係数の計算式

テキスト幅を $W_{\text{text}}$、スロットの最大許容幅を $W_{\max} = 16.0\text{px}$ とした場合：

$$\text{縮小スケール } S = \begin{cases} 1.0 & W_{\text{text}} \le 16.0 \text{ の場合} \\ \frac{16.0}{W_{\text{text}}} & W_{\text{text}} > 16.0 \text{ の場合} \end{cases}$$

---

## 🖼️ テキストスケーリング比較表

| アイテム数 | 文字列長 | 標準フォント幅 ($W_{\text{text}}$) | 適用スケール ($S$) | 表示挙動 |
| :--- | :--- | :--- | :--- | :--- |
| `64` | 2 文字 | $\sim 12\text{px}$ | $1.00$ | 通常のバニラフォントサイズ |
| `1000` | 4 文字 | $\sim 24\text{px}$ | $0.66$ | スロット内に綺麗に収まるよう自動縮小 |
| `1000000` | 7 文字 | $\sim 42\text{px}$ | $0.38$ | はみ出しを防ぐ高精度コンパクト表示 |
