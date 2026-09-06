# 動作プロファイルと条件

## 概要

Stack Size Adjuster は、ステートマシン構造により設定ファイル、動的ゲームルール、ネットワークパケットの連携を行っています。

---

## 🔄 ライフサイクル図

```text
               +----------------------------------+
               |     サーバー起動 / ワールド作成     |
               +----------------------------------+
                                |
                                v
               [ 基本設定テンプレートの読み込み ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ 動的 GameRules の登録           ]
               (`items_64_limit`, `items_16_limit` 等)
                                |
                +---------------+---------------+
                |                               |
           新規ワールド                      既存のワールド
                |                               |
                v                               v
        テンプレート値を適用              `level.dat` から
                                         ルールを読み込み
                |                               |
                +---------------+---------------+
                                |
                                v
               [ StackSizeManager の初期化       ]
                                |
                                v
               [ プレイヤー参加 / ルール編集時   ]
                                |
                                v
               [ S2C パケット送信: sync_limit    ]
                                |
                                v
               [ クライアント画面の強制更新      ]
```

---

## ⚙️ トリガーとイベントハンドラ

1. **サーバー起動完了 (`ServerLifecycleEvents.SERVER_STARTED`)**:
   - 設定ファイルを再読み込み。
   - 新規ワールドの場合は設定値を GameRules に反映。
   - `StackSizeManager` の値を初期化。
2. **プレイヤー接続 (`ServerPlayConnectionEvents.JOIN`)**:
   - 参加したプレイヤーへ `StackSizeLimitSyncPayload` を送信。
3. **ゲーム内ルールの変更 (`MinecraftServerMixin.onGameRuleChanged`)**:
   - `stack-size-adjuster:*` ルールの変更を検知。
   - `StackSizeManager` を更新。
   - 全プレイヤーに同期パケットを送信。
