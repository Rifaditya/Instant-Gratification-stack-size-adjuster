# 開發者配置與構建指南

## 工作區環境要求

* **Java 開發套件 (JDK)**：JDK 25（`org.gradle.java.home=E:/JDK25`）
* **Gradle 構建工具**：Gradle 9.3+（透過 `./gradlew --no-daemon` 執行）
* **Loom 外掛**：Fabric Loom 1.15+
* **目標 Minecraft**：26.2

---

## 🛠️ 環境配置

專案的 `gradle.properties` 定義了各項構建屬性：

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# 模組屬性
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# 依賴版本
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

## 💻 Gradle 構建指令

### 清理並編譯正式版發布 JAR
```powershell
./gradlew build --no-daemon
```
編譯產物位於：
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### 執行自動化單元測試
```powershell
./gradlew test --no-daemon
```

---

## 📦 歸檔規範執行流程

根據**中央歸檔法案**要求，執行 `./gradlew build` 成功後，必須立即將產生的發布 JAR 備份至外層歸檔目錄：
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
