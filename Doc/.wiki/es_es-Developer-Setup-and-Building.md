# Configuración para desarrolladores y compilación

## Requisitos del entorno

* **Java Development Kit (JDK)**: JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Herramienta Gradle**: Gradle 9.3+ (ejecutado con `./gradlew --no-daemon`)
* **Plugin Loom**: Fabric Loom 1.15+
* **Versión objetivo de Minecraft**: 26.2

---

## 🛠️ Configuración del entorno

`gradle.properties` define las propiedades de compilación del proyecto:

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Propiedades del mod
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# Dependencias
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

## 💻 Comandos de compilación Gradle

### Limpiar y compilar el JAR de lanzamiento
```powershell
./gradlew build --no-daemon
```
El binario resultante se ubica en:
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### Ejecutar pruebas unitarias automatizadas
```powershell
./gradlew test --no-daemon
```

---

## 📦 Ejecución de la ley de archivado

Conforme a la ley de archivado, tras compilar con éxito, copia inmediatamente el JAR al directorio exterior:
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
