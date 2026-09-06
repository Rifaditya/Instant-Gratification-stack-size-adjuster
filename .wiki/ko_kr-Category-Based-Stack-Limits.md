# 카테고리 기반 스택 제한

## 시스템 개요

Minecraft는 `DataComponents.MAX_STACK_SIZE`를 통해 아이템을 3단계로 분류합니다:
1. **64 스택**: 건축 블록, 광물 자원, 일반 재료 (조약돌, 흙, 철괴 등).
2. **16 스택**: 엔더 진주, 눈덩이, 양동이, 달걀, 표지판.
3. **1 스택 (스택 불가)**: 도구, 무기, 방어구, 포션, 안장, 보트.

---

## 🧮 스택 크기 결정 순서도

```
                 +--------------------------------+
                 |     아이템 스택 크기 계산       |
                 +--------------------------------+
                                  |
                                  v
                    [ 등록된 재정의 규칙 확인 ]
                     (예: Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
         재정의 규칙 있음                   재정의 규칙 없음
                 |                                 |
                 v                                 v
        커스텀 한도값 반환                  바닐라 기본값 확인
                                            (DataComponents)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                 기본 >= 64                    기본 >= 16                    기본 == 1
                     |                             |                             |
                     v                             v                             v
           `items_64_limit` 반환         `items_16_limit` 반환         `items_1_limit` 반환
```

---

## 💻 핵심 Java 연산 로직

스택 크기 계산은 `StackSizeManager.getModifiedStackSize`가 담당합니다:

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // 애드온 등에서 등록한 재정의 규칙 적용
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ 카테고리별 권장 설정

| 카테고리 | 기본값 | 권장 최대 안전 한도 | 성능 프로필 |
| :--- | :--- | :--- | :--- |
| **64 스택** | `128` | $39,768,215$ | 높은 효율성. 수백만 개 단위도 부드럽게 작동. |
| **16 스택** | `32` | $39,768,215$ | 진주와 달걀에 이상적인 스케일링. |
| **스택 불가** | `1` | $39,768,215$ | 도구 및 포션 중첩 허용. |
