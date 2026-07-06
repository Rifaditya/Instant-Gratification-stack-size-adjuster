package net.instantgratification.stacksizeadjuster.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.instantgratification.stacksizeadjuster.network.StackSizeLimitSyncPayload;

public class StackSizeManager {
    private static volatile int limit64 = 64;
    private static volatile int limit16 = 16;
    private static volatile int limit1 = 1;

    public static int getLimit64() {
        return limit64;
    }

    public static int getLimit16() {
        return limit16;
    }

    public static int getLimit1() {
        return limit1;
    }

    public static int getModifiedStackSize(int original) {
        if (original <= 0) {
            return original;
        }
        if (original >= 64) {
            return limit64;
        } else if (original >= 16) {
            if (limit16 == 64) return 64;
            if (limit16 == 16) return original;
            return 1;
        } else {
            if (limit1 == 64) return 64;
            if (limit1 == 16) return 16;
            return original;
        }
    }

    public static void setLimits(int l64, int l16, int l1, MinecraftServer server) {
        boolean changed = (l64 != limit64 || l16 != limit16 || l1 != limit1);
        if (changed) {
            limit64 = l64;
            limit16 = l16;
            limit1 = l1;
            if (server != null) {
                StackSizeLimitSyncPayload payload = new StackSizeLimitSyncPayload(l64, l16, l1);
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    ServerPlayNetworking.send(player, payload);
                    
                    // Force refresh client's container and inventory menus dynamically
                    if (player.containerMenu != null) {
                        player.containerMenu.broadcastFullState();
                    }
                    if (player.inventoryMenu != null && player.containerMenu != player.inventoryMenu) {
                        player.inventoryMenu.broadcastFullState();
                    }
                }
            }
        }
    }

    public static void setLimit(String path, int value, MinecraftServer server) {
        int next64 = limit64;
        int next16 = limit16;
        int next1 = limit1;

        if (path.equals("items_64_limit")) {
            next64 = value;
        } else if (path.equals("items_16_limit")) {
            next16 = value;
        } else if (path.equals("items_1_limit")) {
            next1 = value;
        }

        setLimits(next64, next16, next1, server);
    }

    public static void setClientLimits(int l64, int l16, int l1) {
        limit64 = l64;
        limit16 = l16;
        limit1 = l1;
    }
}
