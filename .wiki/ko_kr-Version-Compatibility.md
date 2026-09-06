# 버전 호환성 매트릭스

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 최신 미출시 커밋 또는 개발 중인 기능을 포함할 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

## 📊 호환성 매트릭스

| 대상 Minecraft | 모드 버전 | 빌드 상태 | 대상 DasikLibrary | 종속성 경계 |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **활성 / 최신** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ 종속성 경계 및 규칙

**1 Jar 1 Version** 원칙에 따라 `fabric.mod.json`은 개방형 하한선을 설정합니다:

```json
"depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": "${minecraft_dependency}",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": "*",
    "item_clumps": ">=1.0.18+26.2"
}
```

### 버전 규칙:
1. **레거시 체계 배제**: 26.x 버전에서 구형 `1.21.x` 번호를 일체 유지하지 않습니다.
2. **개방형 하한선**: `minecraft >=26.2-`를 통해 마이너 패치 버전 간 원활한 호환성을 보장합니다.
3. **클래스패스 확인**: 초기화 시 `ModVersionGuard.checkClass`가 실행됩니다.

---

## 📦 검증된 과거 아카이브

과거 컴파일된 릴리스 파일은 저장소의 `Archive Jar of all versions/` 폴더에 영구 보관됩니다:

- `stack-size-adjuster-1.4.16+26.2.jar` (현재 릴리스)
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar` (26.2 초기 릴리스)
