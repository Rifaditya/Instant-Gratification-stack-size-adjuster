# コンシューマ Mod 統合ガイド

## 概要

このガイドでは、サードパーティ製 Mod が **Stack Size Adjuster** と連携する方法について説明します。

---

## 🛠️ 統合手順

### ステップ 1: `fabric.mod.json` への依存関係追加

`depends` または `suggests` に追加します：

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### ステップ 2: オーバーライド関数の登録

Mod の `ModInitializer` 内で呼び出します：

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // スプラッシュポーションに対するカスタムルール
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // スプラッシュポーションは 8 個まで
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // 不死のトーテムは 16 個まで
            }
            return originalSize; // その他はデフォルトのルールを適用
        });
    }
}
```

---

### ステップ 3: プログラムから制限値を取得

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.ItemStack;

public class StackQueryUtil {
    public static int getEffectiveLimit(ItemStack stack) {
        int original = stack.getMaxStackSize();
        return StackSizeManager.getModifiedStackSize(stack.getItem(), original);
    }
}
```
