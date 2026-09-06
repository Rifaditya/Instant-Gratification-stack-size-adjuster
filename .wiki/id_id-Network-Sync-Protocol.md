# Protokol Sinkronisasi Jaringan

## Gambaran Umum

Agar interaksi inventaris klien tetap mulus tanpa memerlukan restart server atau reconnect saat GameRules diperbarui, diterapkan paket jaringan `StackSizeLimitSyncPayload`.

---

## 📡 Spesifikasi Paket

* **Pengenal**: `stack-size-adjuster:sync_limit`
* **Arah**: Server ke Klien (S2C)
* **Pemicu**: Dikirim saat `ServerPlayConnectionEvents.JOIN` dan setiap pemanggilan `StackSizeManager.setLimits`.

### Record & Stream Codec

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

## 🔄 Mesin Status Sinkronisasi Klien

```
[ Pembaruan GameRule Server ] ---> [ StackSizeManager.setLimits ]
                                                 |
                                                 v
                                [ Kirim StackSizeLimitSyncPayload ]
                                                 |
                                                 v
[ Klien: ClientPlayNetworking ] <----------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ Segarkan Menu: broadcastFullState() ]
```

### Pembaruan Menu Inventaris Dinamis

Saat batas diperbarui, `StackSizeManager.setLimits` menjelajahi semua pemain online:
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
