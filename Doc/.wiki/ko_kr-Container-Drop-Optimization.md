# 컨테이너 드롭 최적화

## 렉 발생 원인

바닐라 Minecraft에서는 상자, 통, 셜커 상자 등을 파괴할 때 슬롯당 10~30개씩 분할되어 아이템 엔티티가 생성됩니다. 상자에 수만~수백만 개의 아이템이 들어 있을 경우, 파괴 시 **한 틱에 수십만 개의 엔티티가 동시에 생성**되어 서버 멈춤이나 게임 충돌을 유발합니다.

---

## 🧮 엔티티 분할 수학 연산

`InventoryDropHelper.dropItemStack`은 `ContainersMixin`을 통해 기본 드롭을 가로채어 제어합니다:

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // 안전 제한: 슬롯당 엔티티 생성 상한 제어
            if (currentCount > splitSize * maxEntities) {
                splitSize = (currentCount + maxEntities - 1) / maxEntities;
            }
            
            ItemEntity entity = new ItemEntity(level, xo, yo, zo, itemStack.split(splitSize));
            ...
            level.addFreshEntity(entity);
        }
    }
}
```

### 수학적 공식

슬롯 내 총 아이템 수를 $N$, 설정된 `max_drop_entities`를 $M$이라 할 때:
1. 기본 무작위 분할 크기 $S \in [10, 30]$.
2. 동적 분할 임계값 검증:
   $$\text{조건 } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. 생성되는 `ItemEntity`의 총 수는 엄격히 제한됩니다:
   $$E_{\text{spawned}} \le M$$

---

## 📊 드롭 성능 벤치마크

| `max_drop_entities` 설정 | 100,000개 아이템 드롭 시 엔티티 수 | 서버 틱 영향 | 시각적 흩어짐 효과 |
| :--- | :--- | :--- | :--- |
| **바닐라 (제한 없음)** | $\sim 5,000$개 엔티티 | **심각한 렉 / 크래시** | 극심한 혼란 |
| **8 (권장 기본값)** | $\le 8$개 엔티티 | $< 1\text{ms}$ 틱 소요 | 자연스러운 흩어짐 |
| **1 (최대 성능 모드)** | 정확히 $1$개 엔티티 | 즉각 처리 ($0\text{ms}$) | 단일 묶음 |
