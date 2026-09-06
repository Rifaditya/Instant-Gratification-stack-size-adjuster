# Configuração de desenvolvedor e compilação

## Requisitos do ambiente

* **Java Development Kit (JDK)**: JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Ferramenta Gradle**: Gradle 9.3+ (usando `./gradlew --no-daemon`)
* **Plugin Loom**: Fabric Loom 1.15+
* **Versão alvo do Minecraft**: 26.2

---

## 🛠️ Configuração do ambiente

O arquivo `gradle.properties` define as configurações do projeto:

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# Propriedades do mod
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# Dependências
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

## 💻 Comandos de compilação Gradle

### Compilar JAR de lançamento
```powershell
./gradlew build --no-daemon
```
O arquivo gerado fica em:
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### Executar testes automatizados
```powershell
./gradlew test --no-daemon
```

---

## 📦 Lei de arquivamento centralizado

De acordo com as regras do projeto, após compilar com sucesso, copie imediatamente o arquivo JAR para:
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
