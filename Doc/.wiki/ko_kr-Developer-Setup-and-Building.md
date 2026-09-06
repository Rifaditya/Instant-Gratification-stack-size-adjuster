# 개발자 설정 및 빌드 가이드

## 작업 환경 요구 사항

* **Java Development Kit (JDK)**: JDK 25 (`org.gradle.java.home=E:/JDK25`)
* **Gradle 빌드 도구**: Gradle 9.3+ (`./gradlew --no-daemon` 실행)
* **Loom 플러그인**: Fabric Loom 1.15+
* **대상 Minecraft**: 26.2

---

## 🛠️ 환경 구성

`gradle.properties`에 빌드 속성이 정의되어 있습니다:

```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25

# 모드 속성
mod_name=Stack Size Adjuster
mod_version=1.4.16+26.2
maven_group=net.instantgratification
archives_base_name=stack-size-adjuster

# 종속성
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

## 💻 Gradle 빌드 명령어

### 릴리스 JAR 빌드
```powershell
./gradlew build --no-daemon
```
출력 바이너리는 다음 경로에 생성됩니다:
`build/libs/stack-size-adjuster-1.4.16+26.2.jar`

### 자동화 단위 테스트 실행
```powershell
./gradlew test --no-daemon
```

---

## 📦 아카이빙 규정 실행

중앙 아카이빙 법규에 따라, `./gradlew build` 성공 즉시 생성된 JAR을 아카이브 디렉터리로 복사해야 합니다:
`Archive Jar of all versions/stack-size-adjuster-1.4.16+26.2.jar`
