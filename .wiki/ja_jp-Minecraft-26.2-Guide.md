# Minecraft 26.2 ガイド

| 属性 | 仕様 |
| :--- | :--- |
| **対象 Minecraft バージョン** | 26.2（安定版リリース） |
| **Mod バージョン** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*`（26.2 互換） |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **識別子フォーマット** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## 概要

Minecraft 26.2 向けの **Stack Size Adjuster** は、不条理なアイテムスタック制限を取り除くために設計されています。通常 64、16、または 1 個までしかスタックできないアイテムの最大スタック制限を、プレイヤーや管理者が個別にカスタマイズできます。

### 26.2 における主な特徴：
1. **動的なカテゴリースケーリング**: GameRules を通じて 64 スタック、16 スタック、および単一アイテムの上限を調整。
2. **コンテナドロップのラグ防止**: チェスト破壊時にスロットごとにスポーンするエンティティ数を制限（`max_drop_entities`）。
3. **整数オーバーフロー保護**: 54 スロットのラージチェストにおいて、32 ビット符号付き整数の上限（$21.4\text{ 億}$）を超えないよう、$39,768,215$ の安全制限を推奨。
4. **リアルタイムなサーバー・クライアント同期**: カスタム S2C パケット `stack-size-adjuster:sync_limit` により、再接続不要でインベントリを瞬時に更新。

---

## インストールとセットアップ

1. **Fabric Loader 0.19.1+** および **Java 25** がインストールされていることを確認します。
2. `stack-size-adjuster-1.4.16+26.2.jar` を `.minecraft/mods` フォルダに配置します。
3. 必須となる前提 Mod を導入します：
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. ゲームまたはサーバーを起動します。

---

## 実行時バージョンの保護 (ModVersionGuard)

Mod の初期化時に、`ModVersionGuard` はクラスパスの解決を検証します：
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
互換性のないローダーや不一致な Minecraft 環境が検出された場合、ログに詳細を出力して安全に停止します。
