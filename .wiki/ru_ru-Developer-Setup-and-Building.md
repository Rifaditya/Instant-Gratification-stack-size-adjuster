# Настройка среды разработки и сборка

## Требования к окружению

* **Java Development Kit (JDK)**: JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Сборщик Gradle**: Gradle 9.3+ (через `./gradlew --no-daemon`)
* **Плагин Loom**: Fabric Loom 1.15+
* **Целевой Minecraft**: 26.2

---

## 🛠️ Конфигурация окружения

Файл `gradle.properties` содержит настройки проекта:

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Свойства мода
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# Зависимости
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

## 💻 Команды сборки Gradle

### Очистка и сборка релизного JAR
```powershell
./gradlew build --no-daemon
```
Скомпилированный файл помещается в:
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### Запуск автоматических тестов
```powershell
./gradlew test --no-daemon
```

---

## 📦 Закон о централизованном архивировании

Согласно правилу архивирования, после `./gradlew build` обязательно скопируйте скомпилированный JAR в каталог архива:
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
