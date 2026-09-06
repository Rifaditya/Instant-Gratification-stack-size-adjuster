# Configuration développeur et compilation

## Prérequis de l'espace de travail

* **Kit de développement Java (JDK)** : JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Outil Gradle** : Gradle 9.3+ (lancé via `./gradlew --no-daemon`)
* **Plugin Loom** : Fabric Loom 1.15+
* **Minecraft ciblé** : 26.2

---

## 🛠️ Configuration du projet

Le fichier `gradle.properties` contient les propriétés du projet :

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Propriétés du mod
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# Dépendances
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

## 💻 Commandes de build Gradle

### Compiler le JAR de publication
```powershell
./gradlew build --no-daemon
```
Le fichier généré est placé sous :
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### Lancer les tests unitaires
```powershell
./gradlew test --no-daemon
```

---

## 📦 Application de la loi d'archivage

Conformément à la loi d'archivage, après `./gradlew build`, copiez immédiatement le fichier compilé vers :
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
