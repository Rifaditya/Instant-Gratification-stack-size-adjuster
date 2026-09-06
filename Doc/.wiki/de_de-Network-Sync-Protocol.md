# Netzwerksynchronisationsprotokoll

## Übersicht

Um ohne Server-Neustarts oder Reconnects konsistente Inventare bei GameRule-Änderungen zu gewährleisten, implementiert die Mod das Payload `StackSizeLimitSyncPayload`.

---

## 📡 Paket-Spezifikation

* **Bezeichner**: `stack-size-adjuster:sync_limit`
* **Richtung**: Server an Client (S2C)
* **Auslösung**: Bei `ServerPlayConnectionEvents.JOIN` und Aufrufen von `StackSizeManager.setLimits`.

### Record-Signatur & Stream-Codec

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

## 🔄 Client-Zustandsautomat

```
[ Server GameRule Update ] ---> [ StackSizeManager.setLimits ]
                                              |
                                              v
                              [ Sende StackSizeLimitSyncPayload ]
                                              |
                                              v
[ Client ClientPlayNetworking ] <-------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ Menü-Aktualisierung: broadcastFullState() ]
```

### Dynamische Inventar-Aktualisierung

Beim Aktualisieren der Limits iteriert `StackSizeManager.setLimits` über alle Spieler:
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
