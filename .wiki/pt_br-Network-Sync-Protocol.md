# Protocolo de sincronização de rede

## Visão geral

Para permitir uma experiência fluida de inventário sem necessidade de reiniciar o servidor ou reconectar jogadores, implementa-se o pacote `StackSizeLimitSyncPayload`.

---

## 📡 Especificação do pacote de rede

* **Identificador de carga útil**: `stack-size-adjuster:sync_limit`
* **Direção**: Servidor para Cliente (S2C)
* **Envio**: No evento `ServerPlayConnectionEvents.JOIN` e ao invocar `StackSizeManager.setLimits`.

### Estrutura Record e Stream Codec

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

## 🔄 Máquina de estados de sincronização

```
[ Atualização de GameRule ] ---> [ StackSizeManager.setLimits ]
                                                |
                                                v
                                [ Enviar StackSizeLimitSyncPayload ]
                                                |
                                                v
[ Cliente: ClientPlayNetworking ] <-------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ Forçar atualização de telas: broadcastFullState() ]
```

### Atualização dinâmica de telas de inventário

Ao atualizar os limites, `StackSizeManager.setLimits` itera pelos jogadores conectados:
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
