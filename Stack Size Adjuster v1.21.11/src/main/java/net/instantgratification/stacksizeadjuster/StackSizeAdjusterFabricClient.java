// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.instantgratification.stacksizeadjuster.network.StackSizeLimitSyncPayload;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;

public class StackSizeAdjusterFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(StackSizeLimitSyncPayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                StackSizeManager.setClientLimits(payload.limit64(), payload.limit16(), payload.limit1());
                if (context.client().player != null && context.client().player.containerMenu != null) {
                    context.client().player.containerMenu.broadcastFullState();
                }
            });
        });
    }
}
