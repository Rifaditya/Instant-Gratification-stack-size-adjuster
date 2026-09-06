# Pengaturan Pengembang & Kompilasi

## Kebutuhan Ruang Kerja

* **Java Development Kit (JDK)**: JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Gradle Tooling**: Gradle 9.3+ (melalui `./gradlew --no-daemon`)
* **Plugin Loom**: Fabric Loom 1.15+
* **Target Minecraft**: 26.2

---

## 🛠️ Konfigurasi Proyek

File `gradle.properties` mendefinisikan konfigurasi build:

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Properti Mod
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# Dependensi
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

## 💻 Perintah Build Gradle

### Kompilasi Rilis JAR
```powershell
./gradlew build --no-daemon
```
File biner hasil kompilasi tersimpan di:
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### Menjalankan Pengujian Unit
```powershell
./gradlew test --no-daemon
```

---

## 📦 Prosedur Pengarsipan

Sesuai aturan pengarsipan, setelah kompilasi berhasil, segera salin file JAR ke:
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
