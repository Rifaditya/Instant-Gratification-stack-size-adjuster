# 네트워크 동기화 프로토콜

## 개요

관리자가 GameRules를 변경했을 때 서버 재부팅이나 재접속 없이도 클라이언트 인벤토리가 완벽히 동기화되도록 커스텀 패킷 `StackSizeLimitSyncPayload`를 지원합니다.

---

## 📡 패킷 사양

* **식별자**: `stack-size-adjuster:sync_limit`
* **방향**: 서버에서 클라이언트 (S2C)
* **전송 시점**: `ServerPlayConnectionEvents.JOIN` 발생 시 및 서버에서 `StackSizeManager.setLimits` 호출 시.

### 레코드 및 스트림 코덱

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

## 🔄 클라이언트 동기화 상태 머신

```
[ 서버 GameRule 수정 ] ---> [ StackSizeManager.setLimits ]
                                           |
                                           v
                           [ StackSizeLimitSyncPayload 전송 ]
                                           |
                                           v
[ 클라이언트: ClientPlayNetworking ] <------+
                |
                v
[ StackSizeManager.setClientLimits ]
                |
                v
[ 화면 강제 갱신: broadcastFullState() ]
```

### 인벤토리 메뉴 실시간 갱신

제한이 업데이트되면 `StackSizeManager.setLimits`가 모든 접속자를 순회합니다:
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
