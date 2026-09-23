// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;

public class StackSizeAdjusterFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(StackSizeAdjusterFabric.SYNC_LIMIT_ID, (client, handler, buf, sender) -> {
            int l64 = buf.readVarInt();
            int l16 = buf.readVarInt();
            int l1 = buf.readVarInt();
            client.execute(() -> {
                StackSizeManager.setClientLimits(l64, l16, l1);
                if (client.player != null && client.player.containerMenu != null) {
                    client.player.containerMenu.broadcastFullState();
                }
            });
        });
    }
}
