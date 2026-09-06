# 网络同步协议

## 概述

为保证当管理员更改 GameRules 时，客户端无需重启服务器或重新连接即可获得无缝的物品栏交互体验，Stack Size Adjuster 实现了自定义 Fabric 网络数据包 `StackSizeLimitSyncPayload`。

---

## 📡 数据包规范

* **数据包标识符**：`stack-size-adjuster:sync_limit`
* **传输方向**：服务端至客户端 (S2C)
* **发送时机**：在 `ServerPlayConnectionEvents.JOIN` 玩家加入事件以及服务端调用 `StackSizeManager.setLimits` 时触发。

### Record 结构体与流编解码器 (Stream Codec)

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

## 🔄 客户端同步状态机

```
[ 服务端 GameRule 更新 ] ---> [ StackSizeManager.setLimits ]
                                              |
                                              v
                              [ 发送 StackSizeLimitSyncPayload ]
                                              |
                                              v
[ 客户端 ClientPlayNetworking ] <--------------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ 强制刷新界面: broadcastFullState() ]
```

### 动态物品栏菜单状态刷新

当服务端更新堆叠限制时，`StackSizeManager.setLimits` 会遍历所有在线玩家：
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
此操作可促使客户端已打开的箱子容器与物品栏界面实时响应最新堆叠上限。
