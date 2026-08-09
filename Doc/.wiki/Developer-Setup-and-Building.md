# Developer Setup & Building

## Workspace Requirements

* **Java Development Kit (JDK)**: JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Gradle Tooling**: Gradle 9.3+ (executed via `./gradlew --no-daemon`)
* **Loom Plugin**: Fabric Loom 1.15+
* **Target Minecraft**: 26.2

---

## 🛠️ Environment Configuration

`gradle.properties` defines build properties for the project:

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Mod Properties
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# Dependencies
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

## 💻 Gradle Build Commands

### Clean & Compile Release JAR
```powershell
./gradlew build --no-daemon
```
The compiled output binary is placed at:
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### Execute Automated Unit Tests
```powershell
./gradlew test --no-daemon
```

---

## 📦 Archiving Law Execution

Under the **Archive Centralization Law**, immediately following `./gradlew build`, copy the generated release JAR into the outer archive directory:
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
