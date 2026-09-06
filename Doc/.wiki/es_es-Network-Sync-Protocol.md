# Protocolo de sincronización de red

## Resumen

Para asegurar una interacción fluida con el inventario sin requerir reiniciar el servidor o reconectar clientes al cambiar reglas, se implementa el paquete de red `StackSizeLimitSyncPayload`.

---

## 📡 Especificación del paquete de red

* **Identificador de carga útil**: `stack-size-adjuster:sync_limit`
* **Dirección**: Servidor a Cliente (S2C)
* **Activación**: En `ServerPlayConnectionEvents.JOIN` y cuando se llama a `StackSizeManager.setLimits` en el servidor.

### Firma del Record y Stream Codec

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

## 🔄 Máquina de estados de sincronización del cliente

```
[ Actualización de GameRule ] ---> [ StackSizeManager.setLimits ]
                                                 |
                                                 v
                              [ Envío de StackSizeLimitSyncPayload ]
                                                 |
                                                 v
[ Cliente: ClientPlayNetworking ] <--------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ Forzar actualización de menús: broadcastFullState() ]
```

### Actualización dinámica de menús de inventario

Cuando el servidor actualiza los límites, `StackSizeManager.setLimits` itera sobre todos los jugadores:
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
