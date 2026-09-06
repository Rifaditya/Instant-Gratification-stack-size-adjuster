# バージョン互換性マトリックス

> 📌 **リポジトリソースコードに関する免責事項**: この Wiki のドキュメントは**リポジトリ内の現在のソースコードの状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

---

## 📊 互換性マトリックス

| 対象 Minecraft | Mod バージョン | ビルド状態 | 対象 DasikLibrary | 依存関係の境界 |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **アクティブ / 現行版** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ 依存関係の境界とルール

**1 Jar 1 Version** の原則に基づき、`fabric.mod.json` の依存関係記述には開放型の下限を設定しています：

```json
"depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": "${minecraft_dependency}",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": "*",
    "item_clumps": ">=1.0.18+26.2"
}
```

### バージョン識別ルール：
1. **レガシー方式の完全排除**: 26.x リリースにおいて過去の `1.21.x` 形式の番号は使用しません。
2. **開放型の下限指定**: `minecraft >=26.2-` により、パッチリリースとのシームレスな互換性を確保します。
3. **クラスパス検証**: 起動時に `ModVersionGuard.checkClass` を実行します。

---

## 📦 検証済み過去アーカイブ

過去にコンパイルされたリリースバイナリは、リポジトリの `Archive Jar of all versions/` ディレクトリに恒久的に保管されています：

- `stack-size-adjuster-1.4.16+26.2.jar`（現行リリース）
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar`（26.2 初回リリース）
