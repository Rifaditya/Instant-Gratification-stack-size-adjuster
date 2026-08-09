# Network Sync Protocol

## Overview

To guarantee seamless client-side inventory interaction without requiring server restarts or player reconnects when GameRules change, Stack Size Adjuster implements a custom Fabric networking payload `StackSizeLimitSyncPayload`.

---

## 📡 Packet Payload Specification

* **Payload Identifier**: `stack-size-adjuster:sync_limit`
* **Direction**: Server to Client (S2C)
* **Execution**: Triggered on `ServerPlayConnectionEvents.JOIN` and whenever `StackSizeManager.setLimits` is called on the server.

### Record Signature & Stream Codec

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

## 🔄 Client Synchronization State Machine

```
[ Server GameRule Update ] ---> [ StackSizeManager.setLimits ]
                                              |
                                              v
                              [ Send StackSizeLimitSyncPayload ]
                                              |
                                              v
[ Client ClientPlayNetworking ] <-------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ Force Menu Refresh: broadcastFullState() ]
```

### Dynamic Inventory Menu Refresh

When the server updates limits, `StackSizeManager.setLimits` iterates over all online players:
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
This forces the client's inventory and open container menus to refresh stack limits in real-time.
