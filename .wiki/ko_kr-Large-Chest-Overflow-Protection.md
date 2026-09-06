# 대형 상자 오버플로 방지

## 정수 오버플로 위험성

Java와 Minecraft의 인벤토리 연산은 32비트 부호 있는 정수(`int`)를 사용합니다. 표현 가능한 최댓값은 다음과 같습니다:
$$I_{\max} = 2^{31} - 1 = 2,147,483,647$$

54칸의 **대형 상자**에 최대 스택 아이템이 가득 찼을 때, 내부 아이템 합산은 54개 슬롯 전체를 더하게 됩니다.

---

## 🧮 안전 한계 계산

54칸 상자의 각 슬롯에 크기 $L$의 스택이 들어 있는 경우:
$$\text{상자 내 총 아이템} = 54 \times L$$

$I_{\max}$를 초과하지 않기 위한 조건:
$$54 \times L \le 2,147,483,647$$
$$L \le \frac{2,147,483,647}{54} \approx 39,768,215.68$$

따라서 **절대적인 안전 한계치**는 다음과 같습니다:
$$L_{\text{safe}} = 39,768,215$$

### 오버플로 영향 표

| 스택 제한 ($L$) | 54칸 상자 총 아이템 수 | 정수 상태 | 위험도 |
| :--- | :--- | :--- | :--- |
| **128 (기본값)** | $6,912$ | 안전 ($< 21.4\text{억}$) | 위험 없음 |
| **1,000,000** | $54,000,000$ | 안전 ($< 21.4\text{억}$) | 위험 없음 |
| **39,768,215** | $2,147,483,610$ | 안전 ($< 21.4\text{억}$) | **권장 최대 안전 상한** |
| **40,000,000** | $2,160,000,000$ | **오버플로 발생!** (음수 $-2,134,967,296$로 반전) | 아이템 증발 / 상자 손상 |

---

## 🛠️ 배정밀도 빠른 제작 연산

`AbstractContainerMenuMixin`에서 빠른 제작 분할 나눗셈 연산을 64비트 `double` 부동 소수점으로 재작성하여 거대 수량 조작 시 음수 반전을 방지합니다:

```java
@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @Overwrite
    public static int getQuickCraftPlaceCount(int quickCraftSlotsSize, int quickCraftingType, ItemStack itemStack) {
        return switch (quickCraftingType) {
            case 0 -> (int) ((double) itemStack.getCount() / (double) quickCraftSlotsSize);
            case 1 -> 1;
            case 2 -> itemStack.getMaxStackSize();
            default -> itemStack.getCount();
        };
    }
}
```
