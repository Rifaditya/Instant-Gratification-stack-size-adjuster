# Handhabung des Give-Befehls

## Übersicht

In Vanilla Minecraft besitzt der `/give`-Befehl Prüfungen für kleine Mengen (bis 100 Stacks). Bei modifizierten Stapeln in Millionenhöhe könnte der Befehl ohne Anpassung Speichererschöpfung oder extreme Drop-Mengen verursachen.

---

## 🛠️ GiveCommandHelper Logik

`GiveCommandMixin` delegiert die Ausführung an `GiveCommandHelper.giveItem`:

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

## 🧮 Dynamisches Limit mit 100x-Multiplikator

Um Server-Überlastungen durch versehentliche Eingaben zu verhindern:

$$\text{maxAllowedCount} = \text{maxStackSize} \times 100$$

### Beispiele

| Modifizierte Stapelgrenze ($L$) | Erlaubte Maximalmenge in `/give` |
| :--- | :--- |
| **64** | $6.400$ |
| **128** | $12.800$ |
| **1.000** | $100.000$ |
| **1.000.000** | $100.000.000$ |
