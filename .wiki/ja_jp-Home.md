# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

**Instant Gratification: Stack Size Adjuster** の公式百科事典ドキュメントへようこそ。この Minecraft Fabric MOD は、サーバー管理者およびプレイヤーが、3 つの自然なカテゴリ（64 スタック、16 スタック、スタック不可アイテム）にわたるアイテムスタック制限を NBT 肥大化なしで動的に極限値までカスタマイズできるようにし、コンテナドロップの最適化とオーバーフロー保護機能を提供します。

> 📌 **リポジトリソースコードに関する免責事項**: この Wiki のドキュメントは**リポジトリ内の現在のソースコードの状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Minecraft バージョンディレクトリ

* [[Minecraft 26.2 ガイド|ja_jp-Minecraft-26.2-Guide]] — Minecraft 26.2 向けの公式インストール、依存関係、セットアップ手順。
* [[バージョン互換性マトリックス|ja_jp-Version-Compatibility]] — サポートされているバージョン、`ModVersionGuard` チェック、依存関係の境界。

---

## 🎮 プレイヤー & 管理者ガイド

* [[動的ゲームルールリファレンス|ja_jp-Dynamic-GameRules-Reference]] — ゲーム内 GameRules（`items_64_limit`、`items_16_limit`、`items_1_limit`、`max_drop_entities`）。
* [[カテゴリ別スタック制限|ja_jp-Category-Based-Stack-Limits]] — 64、16、および単一アイテムのスケーリング仕様。
* [[コンテナドロップ最適化|ja_jp-Container-Drop-Optimization]] — エンティティスポーン制限、スロット分割計算、コンテナ破壊時のラグ防止。
* [[ラージチェストオーバーフロー保護|ja_jp-Large-Chest-Overflow-Protection]] — 整数オーバーフロー計算（安全上限 $39,768,215$）と倍精度クイッククラフトロジック。
* [[ModMenu および YACL 設定|ja_jp-ModMenu-and-YACL-Configuration]] — メインメニュー GUI 設定（`stack-size-adjuster.json`）と YACL 3.9.5 画面統合。
* [[アイテムカウント GUI レンダリング|ja_jp-Item-Count-GUI-Rendering]] — 複数桁のスタック数に対する動的フォント縮小表示。
* [[Item Clumps 連携|ja_jp-Item-Clumps-Integration]] — Item Clumps との地面上エンティティ結合シナジー。
* [[トラブルシューティングと FAQ|ja_jp-Troubleshooting-and-FAQ]] — よくある質問、オーバーフロー防止、サーバー・クライアント間の同期。

---

## 💻 開発者 & 技術リファレンス

* [[開発環境のセットアップとビルド|ja_jp-Developer-Setup-and-Building]] — JDK 25 環境、Gradle 9.3+、Loom 1.15+、コンパイルワークフロー。
* [[アーキテクチャとパッケージ構成|ja_jp-Architecture-and-Package-Layout]] — システムアーキテクチャツリー、パッケージ構造、スレッドセーフモデル。
* [[Mixin リファレンスとフック|ja_jp-Mixin-Reference-and-Hooks]] — `Item`、`ItemStack`、`Container`、`GiveCommand`、`DataComponents` への注入ポイント。
* [[アドオンオーバーライド API|ja_jp-Addon-Override-API]] — `StackSizeManager.registerOverride` を使用したカスタム制限の登録。
* [[ネットワーク同期プロトコル|ja_jp-Network-Sync-Protocol]] — S2C パケット `stack-size-adjuster:sync_limit` とクライアントメニューの即時更新。
* [[Give コマンドの処理|ja_jp-Give-Command-Handling]] — クラッシュを防ぎながら大量のスタック数を処理するカスタム `GiveCommandHelper`。
* [[動作プロファイルと条件|ja_jp-Behavior-Profiles-and-Conditions]] — 動的 GameRule 同期ステートマシン。
* [[コンシューマ Mod 統合ガイド|ja_jp-Consumer-Mods-Integration-Guide]] — サードパーティ製アドオン開発者向けの統合ガイド。
* [[パフォーマンスとメモリへの影響|ja_jp-Performance-and-Memory-Impact]] — NBT 肥大化ゼロとメモリ消費量ベンチマーク。
* [[進捗とバッジ|ja_jp-Advancements-and-Badges]] — 進捗マトリックスとバニラとの整合性。

---

## 📜 著作権と帰属

**Dasik (Rifaditya)** により **GNU General Public License v3.0 (GPLv3)** のもとで開発されています。
