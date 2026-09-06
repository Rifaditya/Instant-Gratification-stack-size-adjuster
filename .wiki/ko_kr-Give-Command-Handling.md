# Give 명령어 처리

## 개요

바닐라 Minecraft의 `/give` 명령어는 작은 스택 크기에 맞춰 최대 100세트까지만 지급되도록 하드코딩되어 있습니다. 스택 크기가 수백만 개로 설정된 상태에서 바닐라 명령어를 그대로 사용하면 메모리 고갈이나 과도한 엔티티 생성을 초래할 수 있습니다.

---

## 🛠️ GiveCommandHelper 로직

`GiveCommandMixin`은 `GiveCommand.giveItem`을 가로채어 `GiveCommandHelper.giveItem`으로 위임합니다:

```java
public class GiveCommandHelper {
    public static int giveItem(CommandSourceStack source, ItemInput input, Collection<ServerPlayer> players, int count) throws CommandSyntaxException {
        ItemStack prototypeItemStack = input.createItemStack(1);
        int maxStackSize = prototypeItemStack.getMaxStackSize();
        long maxAllowedCountLong = (long) maxStackSize * 100;
        
        if (count > maxAllowedCountLong) {
            int displayCount = (int) Math.min(maxAllowedCountLong, Integer.MAX_VALUE);
            source.sendFailure(Component.translatable("commands.give.failed.toomanyitems", displayCount, prototypeItemStack.getDisplayName()));
            return 0;
        }
        
        for (ServerPlayer player : players) {
            int remaining = count;
            while (remaining > 0) {
                ItemEntity drop;
                int size = Math.min(maxStackSize, remaining);
                remaining -= size;
                ItemStack copyToDrop = prototypeItemStack.copyWithCount(size);
                boolean added = player.getInventory().add(copyToDrop);
                if (!added || !copyToDrop.isEmpty()) {
                    drop = player.drop(copyToDrop, false);
                    if (drop == null) continue;
                    drop.setNoPickUpDelay();
                    drop.setTarget(player.getUUID());
                    continue;
                }
                drop = player.drop(prototypeItemStack.copy(), false);
                if (drop != null) {
                    drop.makeFakeItem();
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f,
                    ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7f + 1.0f) * 2.0f);
                player.containerMenu.broadcastChanges();
            }
        }
        ...
    }
}
```

---

## 🧮 100배 안전 승수 동적 상한

실수로 천문학적인 숫자를 입력하여 서버가 멈추는 것을 방지합니다:

$$\text{maxAllowedCount} = \text{maxStackSize} \times 100$$

### 동적 상한 예시

| 수정된 스택 제한 ($L$) | `/give` 가능한 최대 수량 |
| :--- | :--- |
| **64** | $6,400$ |
| **128** | $12,800$ |
| **1,000** | $100,000$ |
| **1,000,000** | $100,000,000$ |
