# Give 指令處理

## 概述

在原版 Minecraft 中，`/give` 指令內嵌了針對小堆疊數量寫死的安全檢查（單次最多給予 100 組物品）。當堆疊上限調整至數千或數百萬時，原版 `/give` 可能引發記憶體耗盡或瞬間生成成千上萬個掉落物實體。

---

## 🛠️ 核心指令輔助類別邏輯

`GiveCommandMixin` 攔截 `GiveCommand.giveItem` 並將執行委託給 `GiveCommandHelper.giveItem`：

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

## 🧮 100 倍安全乘數動態上限

為防止玩家誤輸入超大數值（例如 `/give @p diamond 2000000000`）導致伺服器卡死，`GiveCommandHelper` 實施了基於當前堆疊上限的動態限制：

$$\text{maxAllowedCount} = \text{maxStackSize} \times 100$$

### 動態限制範例

| 當前修改後的堆疊上限 ($L$) | `/give` 允許的最大物品數量 |
| :--- | :--- |
| **64** | $6,400$ |
| **128** | $12,800$ |
| **1,000** | $100,000$ |
| **1,000,000** | $100,000,000$ |
