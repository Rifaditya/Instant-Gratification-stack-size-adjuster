# Developer Setup & Building

## Workspace Requirements by Minecraft Era

Under the **1 Jar 1 Version Policy**, Stack Size Adjuster maintains dedicated subprojects for each supported Minecraft version anchor:

| Version Subproject | Target Minecraft | Java Toolchain | Loom Plugin | Gradle Version |
| :--- | :--- | :--- | :--- | :--- |
| `Stack Size Adjuster v26.3` | **26.3** | **JDK 25** (`E:/JDK25`) | Fabric Loom 1.15+ | Gradle 9.3+ |
| `Stack Size Adjuster v26.2` | **26.2** | **JDK 25** (`E:/JDK25`) | Fabric Loom 1.15+ | Gradle 9.3+ |
| `Stack Size Adjuster v26.1.2` | **26.1.2** | **JDK 25** (`E:/JDK25`) | Fabric Loom 1.15+ | Gradle 9.3+ |
| `Stack Size Adjuster v1.21.11` | **1.21.11** | **JDK 21** (`E:/JDK21`) | Fabric Loom 1.11+ | Gradle 8.11+ |
| `Stack Size Adjuster v1.21.1` | **1.21.1** | **JDK 21** (`E:/JDK21`) | Fabric Loom 1.10.2 | Gradle 8.11+ |
| `Stack Size Adjuster v1.20.1` | **1.20.1** | **JDK 17** (`E:/JDK17`) | Fabric Loom 1.10.2 | Gradle 8.11+ |

---

## 🛠️ Environment Configuration

Each subproject contains its own isolated `gradle.properties` specifying version coordinates and dependencies.

### Compilation Commands

Navigate to the respective subproject directory or execute Gradle wrapper:

```powershell
# Compile release JAR for targeted subproject
./gradlew build --no-daemon

# Execute automated tests
./gradlew test --no-daemon
```

---

## 📦 Mandatory Universal Triple-Archive Law

Immediately following `./gradlew build`, the compiled release binary must be distributed to all 3 mandatory destinations:

1. **Mod Local Archive**: `<Mod Root>/Archive Jar of all versions/MC <Version>/`
2. **Central Hub Archive**: `minecraft-mod-release-hub/archives/Stack Size Adjuster/`
3. **External Vault Archive**: `D:\Minecraft Mod Archive Jars\Stack Size Adjuster\`
