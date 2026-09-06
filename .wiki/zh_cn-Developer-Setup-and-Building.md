# 开发者配置与构建指南

## 工作区环境要求

* **Java 开发工具包 (JDK)**：JDK 25（`org.gradle.java.home=E:/JDK25`）
* **Gradle 构建工具**：Gradle 9.3+（通过 `./gradlew --no-daemon` 运行）
* **Loom 插件**：Fabric Loom 1.15+
* **目标 Minecraft**：26.2

---

## 🛠️ 环境配置

项目的 `gradle.properties` 定义了各项构建属性：

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# 模组属性
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# 依赖版本
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

## 💻 Gradle 构建命令

### 清理并编译正式版发布 JAR
```powershell
./gradlew build --no-daemon
```
编译产物位于：
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### 执行自动化单元测试
```powershell
./gradlew test --no-daemon
```

---

## 📦 归档规范执行流程

根据**中央归档法案**要求，执行 `./gradlew build` 成功后，必须立即将生成的发布 JAR 备份至外层归档目录：
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
