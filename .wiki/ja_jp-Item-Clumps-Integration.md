# Item Clumps 連携

## 概要

Stack Size Adjuster は **Item Clumps**（`item_clumps >=1.0.18+26.2`）と連携して機能します。Stack Size Adjuster がインベントリ内のスタック上限とコンテナ破壊時のドロップ数を制御し、Item Clumps が地面上のドロップアイテムの結合を担当します。

---

## 🤝 連携メカニズムの概要

```
[ コンテナ破壊イベント ]
           |
           v
[ Stack Size Adjuster: InventoryDropHelper ]
アイテムを制限されたエンティティ数に分割（例: スロットあたり最大 8 個）
           |
           v
[ 地面上にアイテムが出現 ]
           |
           v
[ Item Clumps Mod: 地面エンティティの結合 ]
周囲 3.5 ブロック以内をスキャンし、アイテムを 1 つのエンティティに合体
           |
           v
[ 大量アイテムを含む単一エンティティ ]（ラグなし！）
```

---

## 📊 機能分担マトリックス

| 担当レイヤー | 担当モジュール | メリット |
| :--- | :--- | :--- |
| **インベントリスタック制限** | **Stack Size Adjuster** | チェストやプレイヤー内の 64/16/1 制限を動的に拡張 |
| **コンテナ破壊時のスポーン数** | **Stack Size Adjuster** | 破壊時に何千ものエンティティが出現するのを防止 |
| **地面アイテムの結合** | **Item Clumps** | 散らばったドロップアイテムを 1 つに統合 |
| **アイテム拾得処理** | **Minecraft コア & Mixins** | 結合された大量アイテムを拡張スロットへ直接取得 |

---

## ⚙️ 推奨ゲームルール設定

**Item Clumps** と併用する場合、`max_drop_entities` を **8**（または超軽量化のために **1**）に設定することをおすすめします：
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
