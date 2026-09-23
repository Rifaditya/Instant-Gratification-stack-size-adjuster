package net.instantgratification.stacksizeadjuster;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.instantgratification.stacksizeadjuster.network.StackSizeLimitSyncPayload;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;

// Verified against: ClientModInitializer.java (26.2+)
public class StackSizeAdjusterFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(StackSizeLimitSyncPayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                StackSizeManager.setClientLimits(payload.limit64(), payload.limit16(), payload.limit1());
            });
        });
    }
}
