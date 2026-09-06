# ネットワーク同期プロトコル

## 概要

GameRules が変更された際、サーバーの再起動や再ログインを要求することなくインベントリを即座に最新状態にするため、カスタムパケット `StackSizeLimitSyncPayload` を実装しています。

---

## 📡 パケット仕様

* **識別子**: `stack-size-adjuster:sync_limit`
* **方向**: サーバーからクライアント (S2C)
* **送信契機**: `ServerPlayConnectionEvents.JOIN` および `StackSizeManager.setLimits` 呼び出し時。

### レコード構造とストリームコーデック

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

## 🔄 クライアント同期ステートマシン

```
[ サーバー GameRule 更新 ] ---> [ StackSizeManager.setLimits ]
                                              |
                                              v
                              [ StackSizeLimitSyncPayload 送信 ]
                                              |
                                              v
[ クライアント: ClientPlayNetworking ] <-------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ 画面強制再描画: broadcastFullState() ]
```

### インベントリメニューの動的更新

制限が更新されると、`StackSizeManager.setLimits` は全オンラインプレイヤーを処理します：
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
