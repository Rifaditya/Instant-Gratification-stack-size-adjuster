# アドオンオーバーライド API

## 概要

Stack Size Adjuster は `StackSizeManager.registerOverride` を通じて拡張機構を提供します。サードパーティ製 Mod やアドオン（例: **Potion Stacker Addon**、**Stew Stacker Addon**）は特定のアイテムに個別の上限を設定できます。

---

## 🛠️ API メソッドシグネチャ

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### 引数の説明
- `Item`: 対象となる `Item` インスタンス。
- `Integer`: アイテムの自然なバニラスタック数。
- `Integer`（戻り値）: 変更後のスタック上限値（変更しない場合は元の値を返却）。

---

## 💻 アドオン実装例

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // ポーションを 16 個までスタック可能にする
            if (item instanceof PotionItem) {
                return 16;
            }
            // エンダーパールを 64 個までスタック可能にする
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // 変更しないアイテムは元の値を返してグローバルルールに任せる
            return originalSize;
        });
    }
}
```

---

## 🔄 評価の優先順位

1. オーバーライド関数が `originalSize` と**異なる値**を返した場合、その値が直ちに採用されます。
2. どのオーバーライドも値を変更しなかった場合、`StackSizeManager` はワールドの GameRules（`items_64_limit`、`items_16_limit`、`items_1_limit`）を適用します。
