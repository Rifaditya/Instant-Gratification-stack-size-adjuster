# Penanganan Perintah Give

## Gambaran Umum

Pada Minecraft asli, perintah `/give` memiliki pengaman yang dirancang untuk tumpukan kecil (maksimal 100 tumpukan). Jika batas diatur dalam jumlah jutaan tanpa penanganan khusus, pemanggilan perintah ini dapat memicu kehabisan memori.

---

## 🛠️ Logika GiveCommandHelper

`GiveCommandMixin` mengalihkan `GiveCommand.giveItem` ke `GiveCommandHelper.giveItem`:

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

## 🧮 Batas Dinamis Pengali 100x

Untuk mencegah kesalahan pengetikan angka ekstrem:

$$\text{maxAllowedCount} = \text{maxStackSize} \times 100$$

### Contoh Batas Maksimum

| Batas Tumpukan Terpasang ($L$) | Jumlah Item Maksimum di `/give` |
| :--- | :--- |
| **64** | $6.400$ |
| **128** | $12.800$ |
| **1.000** | $100.000$ |
| **1.000.000** | $100.000.000$ |
