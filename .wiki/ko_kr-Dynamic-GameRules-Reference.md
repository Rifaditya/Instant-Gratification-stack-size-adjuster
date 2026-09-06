# 동적 게임 규칙 참조

## 개요

Stack Size Adjuster는 **DasikLibrary**의 `DynamicGameRuleManager`를 통해 네임스페이스가 지정된 게임 규칙을 등록합니다. 모든 규칙은 `stack-size-adjuster:stack-size-adjuster` 카테고리에 속합니다.

---

## 📋 게임 규칙 디렉터리

| 게임 규칙 키 | 타입 | 기본값 | 설정 범위 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ ~ } 2,147,483,647$ | 기본적으로 64개 쌓이는 아이템의 스택 한도. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ ~ } 2,147,483,647$ | 기본적으로 16개 쌓이는 아이템의 스택 한도. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ ~ } 2,147,483,647$ | 기본적으로 스택 불가능한 단일 아이템의 스택 한도. |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ ~ } 64$ | 보관함 파괴 시 슬롯당 스폰되는 최대 아이템 엔티티 수. |

---

## 💻 인게임 명령어

### 게임 규칙 값 확인
```text
/gamerule stack-size-adjuster:items_64_limit
```

### 게임 규칙 값 변경
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 양방향 구성 동기화

월드가 생성되거나 로드될 때:
1. `StackSizeConfig`(`config/stack-size-adjuster.json`)는 **새로 생성된 월드**에 대해서만 초기 템플릿 값을 설정합니다.
2. **기존 월드**에서는 게임 내에서 변경 시 `StackSizeManager`에 실시간으로 반영됩니다.
3. 규칙 변경 시 `MinecraftServerMixin.onGameRuleChanged`가 호출됩니다:
   ```java
   @Inject(method = "onGameRuleChanged", at = @At("TAIL"))
   private <T> void onGameRuleChanged(GameRule<T> rule, T value, CallbackInfo ci) {
       Identifier ruleId = rule.getIdentifier();
       if (ruleId != null && ruleId.getNamespace().equals("stack-size-adjuster")) {
           if (value instanceof Integer intVal) {
               StackSizeManager.setLimit(ruleId.getPath(), intVal, (MinecraftServer) (Object) this);
           }
       }
   }
   ```
4. 서버는 모든 플레이어에게 `StackSizeLimitSyncPayload`를 전송하고 `player.containerMenu.broadcastFullState()`를 실행합니다.
