# 開発環境のセットアップとビルド

## 開発環境要件

* **Java Development Kit (JDK)**: JDK 25（`org.gradle.java.home=E:/JDK25`）
* **Gradle ツール**: Gradle 9.3+（`./gradlew --no-daemon` 経由）
* **Loom プラグイン**: Fabric Loom 1.15+
* **対象 Minecraft**: 26.2

---

## 🛠️ 環境設定

`gradle.properties` にビルド設定が定義されています：

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Mod プロパティ
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# 依存関係
minecraft_version=26.2
minecraft_dependency=>=26.2-
parchment_minecraft_version=26.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.150.1+26.2
fabric_loader_version=0.19.1

# DasikLibrary
dasik_library_version=1.8.3
```

---

## 💻 Gradle ビルドコマンド

### リリース用 JAR のビルド
```powershell
./gradlew build --no-daemon
```
出力成果物は以下に生成されます：
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### 単体テストの実行
```powershell
./gradlew test --no-daemon
```

---

## 📦 アーカイブ保存規定

中央アーカイブ規則に従い、`./gradlew build` 完了後、直ちに生成物を保管ディレクトリへコピーしてください：
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
