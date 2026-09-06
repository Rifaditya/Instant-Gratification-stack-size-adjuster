# Give コマンドの処理

## 概要

バニラの `/give` コマンドには、小さなスタック数を前提とした安全上限（最大 100 スタック）が組み込まれています。スタック上限が数百万に設定された場合、標準の `/give` はメモリ不足や大量のドロップエンティティを引き起こす可能性があります。

---

## 🛠️ GiveCommandHelper の実装

`GiveCommandMixin` は `GiveCommand.giveItem` をフックし、`GiveCommandHelper.giveItem` に処理を委譲します：

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

## 🧮 100 倍セーフティリミット

意図しない巨大な数値入力によるサーバー停止を防ぐため、以下の動的上限を設けています：

$$\text{maxAllowedCount} = \text{maxStackSize} \times 100$$

### 設定値ごとの上限例

| 設定されたスタック上限 ($L$) | `/give` 可能な最大個数 |
| :--- | :--- |
| **64** | $6,400$ |
| **128** | $12,800$ |
| **1,000** | $100,000$ |
| **1,000,000** | $100,000,000$ |
