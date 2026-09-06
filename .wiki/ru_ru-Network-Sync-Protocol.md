# Протокол сетевой синхронизации

## Обзор

Чтобы гарантировать согласованность клиентских инвентарей без перезапуска сервера или переподключения игроков при изменении правил, реализован сетевой пакет `StackSizeLimitSyncPayload`.

---

## 📡 Спецификация пакета

* **Идентификатор**: `stack-size-adjuster:sync_limit`
* **Направление**: Сервер клиенту (S2C)
* **События отправки**: Вызывается при событии `ServerPlayConnectionEvents.JOIN` и при вызове `StackSizeManager.setLimits` на сервере.

### Сигнатура Record и кодек потока

```java
public record StackSizeLimitSyncPayload(int limit64, int limit16, int limit1) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<StackSizeLimitSyncPayload> TYPE = new CustomPacketPayload.Type<>(
        Identifier.fromNamespaceAndPath("stack-size-adjuster", "sync_limit")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, StackSizeLimitSyncPayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, StackSizeLimitSyncPayload::limit64,
        ByteBufCodecs.VAR_INT, StackSizeLimitSyncPayload::limit16,
        ByteBufCodecs.VAR_INT, StackSizeLimitSyncPayload::limit1,
        StackSizeLimitSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
```

---

## 🔄 Конечный автомат синхронизации на клиенте

```
[ Обновление GameRule на сервере ] ---> [ StackSizeManager.setLimits ]
                                                       |
                                                       v
                                      [ Отправка StackSizeLimitSyncPayload ]
                                                       |
                                                       v
[ Клиент: ClientPlayNetworking ] <----------------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ Принудительное обновление меню: broadcastFullState() ]
```

### Обновление меню инвентаря

При изменении лимитов метод `StackSizeManager.setLimits` обходит всех онлайн-игроков:
```java
for (ServerPlayer player : server.getPlayerList().getPlayers()) {
    ServerPlayNetworking.send(player, payload);
    if (player.containerMenu != null) {
        player.containerMenu.broadcastFullState();
    }
    if (player.inventoryMenu != null && player.containerMenu != player.inventoryMenu) {
        player.inventoryMenu.broadcastFullState();
    }
}
```
Это принудительно заставляет клиент обновить лимиты в открытых сундуках и контейнерах.
