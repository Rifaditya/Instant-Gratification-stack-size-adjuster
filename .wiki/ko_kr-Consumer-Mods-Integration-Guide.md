# 소비자 모드 통합 가이드

## 개요

이 문서는 서드파티 모드 개발자가 **Stack Size Adjuster**와 상호 작용하는 방법을 안내합니다.

---

## 🛠️ 단계별 통합 가이드

### 1단계: `fabric.mod.json`에 종속성 추가

`depends` 또는 `suggests` 항목에 추가합니다:

```json
"depends": {
    "stack-size-adjuster": ">=1.4.16+26.2"
}
```

---

### 2단계: 커스텀 스택 오버라이드 등록

모드의 `ModInitializer`에서 호출합니다:

```java
package com.example.addon;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;

public class ExampleAddonMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // 투척용 포션 전용 스택 규칙 등록
        StackSizeManager.registerOverride((item, originalSize) -> {
            if (item instanceof SplashPotionItem) {
                return 8; // 투척용 포션은 8개까지 중첩
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                return 16; // 불사의 토템은 16개까지 중첩
            }
            return originalSize; // 나머지는 전역 규칙 유지
        });
    }
}
```

---

### 3단계: 현재 적용된 제한값 코드 조회

```java
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.world.item.ItemStack;

public class StackQueryUtil {
    public static int getEffectiveLimit(ItemStack stack) {
        int original = stack.getMaxStackSize();
        return StackSizeManager.getModifiedStackSize(stack.getItem(), original);
    }
}
```
