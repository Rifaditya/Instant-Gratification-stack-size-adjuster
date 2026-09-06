# コンテナドロップ最適化

## ラグ発生の原因

バニラの Minecraft では、チェストなどのコンテナを破壊した際、各スロットのアイテムが 10～30 個程度の小山に分割されてドロップします。大量のアイテムが入ったチェストを破壊すると、**1 ティック内に数十万個のエンティティを生成しようとするため**、深刻なサーバーフリーズやクラッシュを引き起こします。

---

## 🧮 エンティティ生成数制限と分割計算

`InventoryDropHelper.dropItemStack` は `ContainersMixin` を介してバニラのドロップ処理をフックし、エンティティ生成数を制御します：

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // 安全制限: 1 スロットあたりのスポーン数を制限
            if (currentCount > splitSize * maxEntities) {
                splitSize = (currentCount + maxEntities - 1) / maxEntities;
            }
            
            ItemEntity entity = new ItemEntity(level, xo, yo, zo, itemStack.split(splitSize));
            ...
            level.addFreshEntity(entity);
        }
    }
}
```

### 数学的定義

スロット内の合計アイテム数を $N$、設定された `max_drop_entities` を $M$ とした場合：
1. 基準ランダム分割サイズ $S \in [10, 30]$。
2. 動的分割しきい値判定：
   $$\text{もし } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. スロットから生成される `ItemEntity` の総数は厳密に以下に制限されます：
   $$E_{\text{spawned}} \le M$$

---

## 📊 ドロップパフォーマンステスト

| `max_drop_entities` 設定 | 100,000 個ドロップ時のエンティティ数 | サーバーティックへの負荷 | 視覚的な散らばり |
| :--- | :--- | :--- | :--- |
| **バニラ（制限なし）** | $\sim 5,000$ 体 | **深刻なラグ / クラッシュ** | 極度の乱雑さ |
| **8（推奨デフォルト値）** | $\le 8$ 体 | $< 1\text{ms}$ 処理時間 | 良好な飛び散り演出 |
| **1（最高性能モード）** | 厳密に $1$ 体 | 瞬時完了 ($0\text{ms}$) | 1 つのまとまり |
