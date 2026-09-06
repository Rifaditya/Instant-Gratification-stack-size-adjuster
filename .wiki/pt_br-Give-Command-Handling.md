# Processamento do comando Give

## Visão geral

No jogo original, o `/give` tem travas rígidas de segurança concebidas para pilhas pequenas (até 100 pilhas). Com pilhas na casa dos milhões, o `/give` original poderia causar exaustão de memória e gerar entidades em excesso.

---

## 🛠️ Lógica do GiveCommandHelper

O `GiveCommandMixin` intercepta `GiveCommand.giveItem` e repassa para `GiveCommandHelper.giveItem`:

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

## 🧮 Limite dinâmico com multiplicador 100x

Para evitar travamentos no servidor por digitação involuntária de valores astronômicos:

$$\text{maxAllowedCount} = \text{maxStackSize} \times 100$$

### Exemplos de limites dinâmicos

| Limite da pilha ($L$) | Máximo permitido no `/give` |
| :--- | :--- |
| **64** | $6.400$ |
| **128** | $12.800$ |
| **1.000** | $100.000$ |
| **1.000.000** | $100.000.000$ |
