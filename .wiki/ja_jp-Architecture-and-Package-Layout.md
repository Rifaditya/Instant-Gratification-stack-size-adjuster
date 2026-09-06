# アーキテクチャとパッケージ構成

## 単一責任設計 (1 File, 1 Purpose)

Stack Size Adjuster は**単一責任の原則**を徹底しています。各クラスは、ネットワーク同期、設定管理、ドロップ処理、描画処理のいずれか 1 つの目的に特化しています。

---

## 🌳 パッケージ階層ツリー

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Fabric メインエントリポイント & GameRules 登録
├── StackSizeAdjusterFabricClient.java # クライアントエントリポイント & S2C パケット受信
├── config/
│   ├── ModMenuIntegration.java        # ModMenu API 統合
│   ├── StackSizeConfig.java           # JSON 設定モデル (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # YACL 3.9.5 リフレクション画面ビルダー
├── mixin/
│   ├── AbstractContainerMenuMixin.java# 倍精度クイッククラフト計算
│   ├── ContainerMixin.java            # コンテナスロット制限の上書き (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Containers.dropItemStack のフック
│   ├── DataComponentsMixin.java       # DataComponents MAX_STACK_SIZE コーデック拡張
│   ├── GiveCommandMixin.java          # /give コマンドのフック
│   ├── ItemInstanceMixin.java         # ItemInstance.getMaxStackSize フック
│   ├── ItemMixin.java                 # Item.getDefaultMaxStackSize フック
│   ├── ItemStackMixin.java            # ItemStack.getMaxStackSize リダイレクト
│   ├── ItemStackTemplateMixin.java    # ItemStackTemplate コーデックリダイレクト
│   ├── MinecraftServerMixin.java      # サーバー GameRule 変更リスナー
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # アイテムカウント描画縮小フック
├── network/
│   └── StackSizeLimitSyncPayload.java # S2C パケットレコード (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # 100 倍セーフティ倍率付き /give 処理
    ├── InventoryDropHelper.java       # コンテナ破壊時のドロップ数制御
    ├── ItemCountRenderer.java         # 動的フォントマトリックス縮小レンダラー
    ├── ModVersionGuard.java           # 実行時クラス検証チェック
    └── StackSizeManager.java          # スレッドセーフな制限マネージャー & レジストリ
```

---

## 🔒 並行処理モデル

* **読み取り処理**: `StackSizeManager.getModifiedStackSize` は `volatile int` 変数と `CopyOnWriteArrayList` によりロックフリーで高速に実行されます。
* **サーバー・クライアント同期**: パケットはサーバー主スレッドでディスパッチされ、クライアント主スレッドで `context.client().execute(...)` により安全に適用されます。
