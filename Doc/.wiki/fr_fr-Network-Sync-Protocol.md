# Protocole de synchronisation réseau

## Vue d'ensemble

Pour assurer une manipulation d'inventaire sans déconnexion lors d'un changement de règle, le mod envoie le paquet réseau `StackSizeLimitSyncPayload`.

---

## 📡 Spécification du paquet

* **Identifiant** : `stack-size-adjuster:sync_limit`
* **Sens** : Serveur vers Client (S2C)
* **Déclenchement** : Lors de `ServerPlayConnectionEvents.JOIN` et de chaque appel à `StackSizeManager.setLimits`.

### Signature du Record et Codec de flux

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

## 🔄 Machine à états de synchronisation

```
[ Modification GameRule ] ---> [ StackSizeManager.setLimits ]
                                              |
                                              v
                              [ Envoi StackSizeLimitSyncPayload ]
                                              |
                                              v
[ Client: ClientPlayNetworking ] <------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ Actualisation forcée des menus: broadcastFullState() ]
```

### Actualisation des menus d'inventaire

Lors de l'ajustement des limites, `StackSizeManager.setLimits` parcourt les joueurs :
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
