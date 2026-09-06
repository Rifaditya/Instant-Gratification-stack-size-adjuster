# 動的ゲームルールリファレンス

## 概要

Stack Size Adjuster は、**DasikLibrary** の `DynamicGameRuleManager` を介して名前空間付きの GameRules を登録します。すべてのルールは `stack-size-adjuster:stack-size-adjuster` カテゴリに属します。

---

## 📋 ゲームルール一覧

| ゲームルールキー | タイプ | デフォルト値 | 設定可能範囲 | 説明 |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ ～ } 2,147,483,647$ | 自然に 64 個スタックできるアイテムのスタック上限。 |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ ～ } 2,147,483,647$ | 自然に 16 個スタックできるアイテムのスタック上限。 |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ ～ } 2,147,483,647$ | 自然にスタックできない単一アイテムのスタック上限。 |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ ～ } 64$ | コンテナ破壊時にスロットごとにスポーンする最大エンティティ数。 |

---

## 💻 ゲーム内コマンド

### ルールの現在値を確認
```text
/gamerule stack-size-adjuster:items_64_limit
```

### ルール値を変更
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 双方向設定同期の仕組み

ワールド生成時またはロード時：
1. `StackSizeConfig`（グローバル設定 `config/stack-size-adjuster.json`）は**新規作成されたワールド**に対してのみデフォルト値を設定します。
2. **既存のワールド**では、ゲーム内コマンドや画面からルールを変更すると、`StackSizeManager` がリアルタイムに更新されます。
3. ルールの変更により `MinecraftServerMixin.onGameRuleChanged` が呼び出されます：
   ```java
   @Inject(method = "onGameRuleChanged", at = @At("TAIL"))
   private <T> void onGameRuleChanged(GameRule<T> rule, T value, CallbackInfo ci) {
       Identifier ruleId = rule.getIdentifier();
       if (ruleId != null && ruleId.getNamespace().equals("stack-size-adjuster")) {
           if (value instanceof Integer intVal) {
               StackSizeManager.setLimit(ruleId.getPath(), intVal, (MinecraftServer) (Object) this);
           }
       }
   }
   ```
4. サーバーは全プレイヤーへ `StackSizeLimitSyncPayload` をブロードキャストし、`player.containerMenu.broadcastFullState()` を実行してインベントリを更新します。
