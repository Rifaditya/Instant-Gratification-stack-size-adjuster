# Give 命令处理

## 概述

在原版 Minecraft 中，`/give` 命令内嵌了针对小堆叠数量硬编码的安全检查（单次最多给予 100 组物品）。当堆叠上限调整至数千或数百万时，原版 `/give` 可能引发内存耗尽或瞬间生成成千上万个掉落物实体。

---

## 🛠️ 核心命令辅助类逻辑

`GiveCommandMixin` 拦截 `GiveCommand.giveItem` 并将执行委托给 `GiveCommandHelper.giveItem`：

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

## 🧮 100 倍安全乘数动态上限

为防止玩家误输入超大数值（例如 `/give @p diamond 2000000000`）导致服务器卡死，`GiveCommandHelper` 实施了基于当前堆叠上限的动态限制：

$$\text{maxAllowedCount} = \text{maxStackSize} \times 100$$

### 动态限制示例

| 当前修改后的堆叠上限 ($L$) | `/give` 允许的最大物品数量 |
| :--- | :--- |
| **64** | $6,400$ |
| **128** | $12,800$ |
| **1,000** | $100,000$ |
| **1,000,000** | $100,000,000$ |
