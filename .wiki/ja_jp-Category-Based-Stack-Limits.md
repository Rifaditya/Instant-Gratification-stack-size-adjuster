# カテゴリ別スタック制限

## システム概要

Minecraft は `DataComponents.MAX_STACK_SIZE` を通じてアイテムを 3 つの階層に分類しています：
1. **64 スタック**: 建築ブロック、鉱石資源、一般アイテム（丸石、土、鉄インゴット等）。
2. **16 スタック**: エンダーパール、雪玉、バケツ、卵、看板。
3. **1 スタック（非スタック）**: ツール、武器、防具、ポーション、鞍、トロッコ。

---

## 🧮 スタックサイズ決定フローチャート

```
                 +--------------------------------+
                 |    アイテムスタックサイズ計算    |
                 +--------------------------------+
                                  |
                                  v
                  [ 登録済みオーバーライドの確認 ]
                  (例: Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
         オーバーライドあり?                オーバーライドなし
                 |                                 |
                 v                                 v
        カスタム上限値を返す               バニラのデフォルト値を検査
                                            (DataComponents)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                 自然値 >= 64                  自然値 >= 16                  自然値 == 1
                     |                             |                             |
                     v                             v                             v
           `items_64_limit` を返す       `items_16_limit` を返す       `items_1_limit` を返す
```

---

## 💻 Java 実装ロジック

スタック計算は `StackSizeManager.getModifiedStackSize` により処理されます：

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // アドオン等から登録されたオーバーライドを適用
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ カテゴリ制限の推奨設定

| 対象カテゴリ | デフォルト値 | 推奨安全上限 | パフォーマンス特性 |
| :--- | :--- | :--- | :--- |
| **64 スタック** | `128` | $39,768,215$ | 非常に高速。数百万個の制限でも快適に動作。 |
| **16 スタック** | `32` | $39,768,215$ | パールや卵のスムーズな拡張。 |
| **非スタック** | `1` | $39,768,215$ | ツールやポーションのスタックを許可。 |
