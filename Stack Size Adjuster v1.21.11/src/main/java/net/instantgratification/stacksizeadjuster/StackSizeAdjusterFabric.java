// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import net.instantgratification.stacksizeadjuster.config.StackSizeConfig;
import net.instantgratification.stacksizeadjuster.network.StackSizeLimitSyncPayload;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackSizeAdjusterFabric implements ModInitializer {
    public static final String MOD_ID = "stack-size-adjuster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final CustomGameRuleCategory CUSTOM_CATEGORY = new CustomGameRuleCategory(
        Identifier.fromNamespaceAndPath(MOD_ID, MOD_ID),
        Component.translatable("gamerule.category." + MOD_ID + "." + MOD_ID)
    );

    public static GameRule<Integer> ITEMS_64_LIMIT;
    public static GameRule<Integer> ITEMS_16_LIMIT;
    public static GameRule<Integer> ITEMS_1_LIMIT;
    public static GameRule<Integer> MAX_DROP_ENTITIES;

    public static int maxDropEntities = 8;

    @Override
    public void onInitialize() {
        LOGGER.info("Instant Gratification: Stack Size Adjuster Initialized (1.21.11)");

        // Load config baseline template
        StackSizeConfig.load(FabricLoader.getInstance().getConfigDir());
        maxDropEntities = StackSizeConfig.get().maxDropEntities;

        // Register GameRules with dynamic defaults loaded from baseline config
        ITEMS_64_LIMIT = GameRuleBuilder.forInteger(StackSizeConfig.get().items64Limit)
            .category(CUSTOM_CATEGORY)
            .range(1, Integer.MAX_VALUE)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "items_64_limit"));

        ITEMS_16_LIMIT = GameRuleBuilder.forInteger(StackSizeConfig.get().items16Limit)
            .category(CUSTOM_CATEGORY)
            .range(1, Integer.MAX_VALUE)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "items_16_limit"));

        ITEMS_1_LIMIT = GameRuleBuilder.forInteger(StackSizeConfig.get().items1Limit)
            .category(CUSTOM_CATEGORY)
            .range(1, Integer.MAX_VALUE)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "items_1_limit"));

        MAX_DROP_ENTITIES = GameRuleBuilder.forInteger(StackSizeConfig.get().maxDropEntities)
            .category(CUSTOM_CATEGORY)
            .range(1, 64)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "max_drop_entities"));

        // Change callbacks invoking StackSizeManager.setLimits(...)
        GameRuleEvents.changeCallback(ITEMS_64_LIMIT).register((val, server) -> {
            StackSizeManager.setLimit("items_64_limit", val, server);
            broadcastSync(server);
        });

        GameRuleEvents.changeCallback(ITEMS_16_LIMIT).register((val, server) -> {
            StackSizeManager.setLimit("items_16_limit", val, server);
            broadcastSync(server);
        });

        GameRuleEvents.changeCallback(ITEMS_1_LIMIT).register((val, server) -> {
            StackSizeManager.setLimit("items_1_limit", val, server);
            broadcastSync(server);
        });

        GameRuleEvents.changeCallback(MAX_DROP_ENTITIES).register((val, server) -> {
            maxDropEntities = val;
        });

        // Register payload via PayloadTypeRegistry.playS2C().register(...)
        PayloadTypeRegistry.playS2C().register(StackSizeLimitSyncPayload.TYPE, StackSizeLimitSyncPayload.CODEC);

        // Send sync payload on ServerPlayConnectionEvents.JOIN
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(new StackSizeLimitSyncPayload(
                StackSizeManager.getLimit64(),
                StackSizeManager.getLimit16(),
                StackSizeManager.getLimit1()
            ));
        });

        // Synchronize on ServerLifecycleEvents.SERVER_STARTED
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            StackSizeConfig.load(FabricLoader.getInstance().getConfigDir());

            GameRules rules = server.overworld().getGameRules();

            if (!server.getWorldData().overworldData().isInitialized()) {
                rules.set(ITEMS_64_LIMIT, StackSizeConfig.get().items64Limit, server);
                rules.set(ITEMS_16_LIMIT, StackSizeConfig.get().items16Limit, server);
                rules.set(ITEMS_1_LIMIT, StackSizeConfig.get().items1Limit, server);
                rules.set(MAX_DROP_ENTITIES, StackSizeConfig.get().maxDropEntities, server);
            }

            int l64 = rules.get(ITEMS_64_LIMIT);
            int l16 = rules.get(ITEMS_16_LIMIT);
            int l1 = rules.get(ITEMS_1_LIMIT);
            maxDropEntities = rules.get(MAX_DROP_ENTITIES);
            StackSizeManager.setLimits(l64, l16, l1, server);
        });
    }

    private static void broadcastSync(MinecraftServer server) {
        if (server == null) return;
        StackSizeLimitSyncPayload payload = new StackSizeLimitSyncPayload(
            StackSizeManager.getLimit64(),
            StackSizeManager.getLimit16(),
            StackSizeManager.getLimit1()
        );
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            ServerPlayNetworking.send(player, payload);
        }
    }
}
