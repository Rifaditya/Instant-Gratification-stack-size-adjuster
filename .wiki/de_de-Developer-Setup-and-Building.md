# Entwickler-Setup & Build-Anleitung

## Arbeitsbereich-Anforderungen

* **Java Development Kit (JDK)**: JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Gradle Tooling**: Gradle 9.3+ (über `./gradlew --no-daemon`)
* **Loom Plugin**: Fabric Loom 1.15+
* **Ziel-Minecraft**: 26.2

---

## 🛠️ Umgebungskonfiguration

`gradle.properties` definiert die Build-Eigenschaften:

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Mod-Eigenschaften
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# Abhängigkeiten
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

## 💻 Gradle-Build-Befehle

### Release-JAR kompilieren
```powershell
./gradlew build --no-daemon
```
Die Ausgabedatei liegt unter:
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### Automatisierte Tests ausführen
```powershell
./gradlew test --no-daemon
```

---

## 📦 Archivierungsrichtlinie

Nach erfolgreichem `./gradlew build` muss die erzeugte JAR-Datei sofort in das Archiv kopiert werden:
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
