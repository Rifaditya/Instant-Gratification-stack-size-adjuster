# Minecraft 26.2 가이드

| 속성 | 세부 사양 |
| :--- | :--- |
| **대상 Minecraft 버전** | 26.2 (안정 릴리스) |
| **모드 버전** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*` (26.2 호환) |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **식별자 형식** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## 개요

Minecraft 26.2용 **Stack Size Adjuster**는 엄격한 아이템 스택 제한을 제거하기 위해 제작되었습니다. 자연적으로 64개, 16개 또는 1개까지만 쌓이는 아이템에 대해 사용자 정의 최대 스택 한도를 설정할 수 있습니다.

### 26.2 핵심 기능:
1. **동적 카테고리 스케일링**: GameRules를 통해 64개, 16개 및 단일 도구/포션의 상한을 개별 제어.
2. **보관함 파괴 렉 방지**: 상자 파괴 시 슬롯당 스폰되는 최대 아이템 엔티티 수 제한(`max_drop_entities`).
3. **정수 오버플로 방지**: 54슬롯 대형 상자에서 32비트 부호 있는 정수 한계($21.4\text{억}$)를 넘지 않도록 $39,768,215$의 안전 상한 권장.
4. **실시간 서버-클라이언트 동기화**: 커스텀 S2C 패킷 `stack-size-adjuster:sync_limit`으로 재접속 없이 인벤토리 즉시 갱신.

---

## 설치 및 설정

1. **Fabric Loader 0.19.1+** 및 **Java 25**가 설치되어 있는지 확인합니다.
2. `stack-size-adjuster-1.4.16+26.2.jar` 파일을 `.minecraft/mods` 폴더에 넣습니다.
3. 필수 종속성 모드를 확인합니다:
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. 게임 또는 서버를 실행합니다.

---

## 런타임 버전 가드 (ModVersionGuard)

모드 초기화 중 `ModVersionGuard`가 클래스패스를 검증합니다:
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
호환되지 않는 환경이 감지되면 안전하게 중단되고 로그에 기록됩니다.
