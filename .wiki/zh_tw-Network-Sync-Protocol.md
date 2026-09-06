# 網路同步協定

## 概述

為保證當管理員更改 GameRules 時，客戶端無需重新啟動伺服器或重新連線即可獲得無縫的物品欄互動體驗，Stack Size Adjuster 實作了自訂 Fabric 網路封包 `StackSizeLimitSyncPayload`。

---

## 📡 封包規範

* **封包識別碼**：`stack-size-adjuster:sync_limit`
* **傳輸方向**：伺服端至客戶端 (S2C)
* **發送時機**：在 `ServerPlayConnectionEvents.JOIN` 玩家加入事件以及伺服端呼叫 `StackSizeManager.setLimits` 時觸發。

### Record 結構體與串流編解碼器 (Stream Codec)

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

## 🔄 客戶端同步狀態機

```
[ 伺服端 GameRule 更新 ] ---> [ StackSizeManager.setLimits ]
                                              |
                                              v
                              [ 發送 StackSizeLimitSyncPayload ]
                                              |
                                              v
[ 客戶端 ClientPlayNetworking ] <--------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ 強制重新整理介面: broadcastFullState() ]
```

### 動態物品欄選單狀態重新整理

當伺服端更新堆疊限制時，`StackSizeManager.setLimits` 會遍歷所有線上玩家：
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
此操作可促使客戶端已開啟的箱子容器與物品欄介面即時回應最新堆疊上限。
