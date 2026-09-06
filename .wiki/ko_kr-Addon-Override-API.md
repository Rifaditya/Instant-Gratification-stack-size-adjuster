# 애드온 재정의 API

## 개요

Stack Size Adjuster는 `StackSizeManager.registerOverride`를 통해 가벼운 확장 메커니즘을 제공합니다. 서드파티 모드 및 애드온(예: **Potion Stacker Addon**, **Stew Stacker Addon**)은 특정 아이템에 대한 전용 규칙을 등록할 수 있습니다.

---

## 🛠️ API 등록 메서드 시그니처

```java
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;
import java.util.function.BiFunction;

public class StackSizeManager {
    public static void registerOverride(BiFunction<Item, Integer, Integer> override);
}
```

### 매개변수 설명
- `Item`: 검사 대상 `Item` 인스턴스.
- `Integer`: 해당 아이템의 원래 바닐라 자연 스택 크기.
- `Integer` (반환값): 수정된 스택 크기 한도 (변경하지 않을 경우 원본 값).

---

## 💻 애드온 구현 예제

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public class CustomAddonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        StackSizeManager.registerOverride((item, originalSize) -> {
            // 포션류를 최대 16개까지 스택 허용
            if (item instanceof PotionItem) {
                return 16;
            }
            // 엔더 진주를 64개까지 스택 허용
            if (item == Items.ENDER_PEARL) {
                return 64;
            }
            // 수정하지 않을 아이템은 원본 값을 반환하여 전역 규칙에 위임
            return originalSize;
        });
    }
}
```

---

## 🔄 실행 우선순위

1. 재정의 함수가 `originalSize`와 **다른 값**을 반환하면 그 값이 즉시 적용됩니다.
2. 어떠한 재정의 함수도 값을 변경하지 않은 경우, `StackSizeManager`는 월드의 활성 GameRules(`items_64_limit`, `items_16_limit`, `items_1_limit`)를 확인합니다.
