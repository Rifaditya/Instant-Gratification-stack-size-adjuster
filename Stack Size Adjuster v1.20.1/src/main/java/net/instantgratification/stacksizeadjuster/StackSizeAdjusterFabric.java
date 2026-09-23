// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.instantgratification.stacksizeadjuster.config.StackSizeConfig;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Verified against: MinecraftServer.java, GameRules.java (1.20.1)
public class StackSizeAdjusterFabric implements ModInitializer {
    public static final String MOD_ID = "stack-size-adjuster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ResourceLocation SYNC_LIMIT_ID = new ResourceLocation(MOD_ID, "sync_limit");

    public static GameRules.Key<GameRules.IntegerValue> ITEMS_64_LIMIT;
    public static GameRules.Key<GameRules.IntegerValue> ITEMS_16_LIMIT;
    public static GameRules.Key<GameRules.IntegerValue> ITEMS_1_LIMIT;
    public static GameRules.Key<GameRules.IntegerValue> MAX_DROP_ENTITIES;

    public static int maxDropEntities = 8;

    @Override
    public void onInitialize() {
        LOGGER.info("Instant Gratification: Stack Size Adjuster Initialized (1.20.1)");

        // Load config baseline template
        StackSizeConfig.load(FabricLoader.getInstance().getConfigDir());
        maxDropEntities = StackSizeConfig.get().maxDropEntities;

        // Register GameRules via GameRuleRegistry.register
        ITEMS_64_LIMIT = GameRuleRegistry.register(
            "stack-size-adjuster:items_64_limit",
            GameRules.Category.MISC,
            GameRuleFactory.createIntRule(128, 1, Integer.MAX_VALUE, (server, rule) -> {
                StackSizeManager.setLimit("items_64_limit", rule.get(), server);
                syncLimits(server);
            })
        );

        ITEMS_16_LIMIT = GameRuleRegistry.register(
            "stack-size-adjuster:items_16_limit",
            GameRules.Category.MISC,
            GameRuleFactory.createIntRule(32, 1, Integer.MAX_VALUE, (server, rule) -> {
                StackSizeManager.setLimit("items_16_limit", rule.get(), server);
                syncLimits(server);
            })
        );

        ITEMS_1_LIMIT = GameRuleRegistry.register(
            "stack-size-adjuster:items_1_limit",
            GameRules.Category.MISC,
            GameRuleFactory.createIntRule(1, 1, Integer.MAX_VALUE, (server, rule) -> {
                StackSizeManager.setLimit("items_1_limit", rule.get(), server);
                syncLimits(server);
            })
        );

        MAX_DROP_ENTITIES = GameRuleRegistry.register(
            "stack-size-adjuster:max_drop_entities",
            GameRules.Category.MISC,
            GameRuleFactory.createIntRule(8, 1, 64, (server, rule) -> {
                maxDropEntities = rule.get();
            })
        );

        // Send current limits on player join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeVarInt(StackSizeManager.getLimit64());
            buf.writeVarInt(StackSizeManager.getLimit16());
            buf.writeVarInt(StackSizeManager.getLimit1());
            sender.sendPacket(SYNC_LIMIT_ID, buf);
        });

        // Synchronize on ServerLifecycleEvents.SERVER_STARTED
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            StackSizeConfig.load(FabricLoader.getInstance().getConfigDir());

            GameRules rules = server.getGameRules();

            if (!server.getWorldData().overworldData().isInitialized()) {
                rules.getRule(ITEMS_64_LIMIT).set(StackSizeConfig.get().items64Limit, server);
                rules.getRule(ITEMS_16_LIMIT).set(StackSizeConfig.get().items16Limit, server);
                rules.getRule(ITEMS_1_LIMIT).set(StackSizeConfig.get().items1Limit, server);
                rules.getRule(MAX_DROP_ENTITIES).set(StackSizeConfig.get().maxDropEntities, server);
            }

            int l64 = rules.getInt(ITEMS_64_LIMIT);
            int l16 = rules.getInt(ITEMS_16_LIMIT);
            int l1 = rules.getInt(ITEMS_1_LIMIT);
            maxDropEntities = rules.getInt(MAX_DROP_ENTITIES);
            StackSizeManager.setLimits(l64, l16, l1, server);
        });
    }

    public static void syncLimits(MinecraftServer server) {
        if (server == null) return;
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(StackSizeManager.getLimit64());
        buf.writeVarInt(StackSizeManager.getLimit16());
        buf.writeVarInt(StackSizeManager.getLimit1());
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            ServerPlayNetworking.send(player, SYNC_LIMIT_ID, PacketByteBufs.copy(buf));
            if (player.containerMenu != null) player.containerMenu.broadcastFullState();
            if (player.inventoryMenu != null && player.containerMenu != player.inventoryMenu) player.inventoryMenu.broadcastFullState();
        }
    }
}
