// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.instantgratification.stacksizeadjuster.config.StackSizeConfig;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.instantgratification.stacksizeadjuster.util.StackSizeSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Verified against: MinecraftServer.java, GameRules.java (1.20.1)
public class StackSizeAdjusterFabric implements ModInitializer {
    public static final String MOD_ID = "stack-size-adjuster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ResourceLocation SYNC_LIMIT_ID = new ResourceLocation(MOD_ID, "sync_limit");

    public static CustomGameRuleCategory CUSTOM_CATEGORY;
    public static GameRules.Key<GameRules.IntegerValue> ITEMS_64_LIMIT;
    public static GameRules.Key<GameRules.IntegerValue> ITEMS_16_LIMIT;
    public static GameRules.Key<GameRules.IntegerValue> ITEMS_1_LIMIT;
    public static GameRules.Key<GameRules.IntegerValue> MAX_DROP_ENTITIES;

    public static int maxDropEntities = 8;

    @Override
    public void onInitialize() {
        LOGGER.info("Instant Gratification: Stack Size Adjuster Initialized (1.20.1)");
        StackSizeSupport.logSupportNotice();

        // Load config baseline template
        StackSizeConfig.load(FabricLoader.getInstance().getConfigDir());
        maxDropEntities = StackSizeConfig.get().maxDropEntities;

        // Register Dynamic GameRules with custom category and translations via Dasik Library
        CUSTOM_CATEGORY = DynamicGameRuleManager.registerCategory(
            new ResourceLocation(MOD_ID, MOD_ID),
            Component.translatable("gamerule.category.stacksizeadjuster").withStyle(ChatFormatting.BOLD, ChatFormatting.YELLOW)
        );

        ITEMS_64_LIMIT = DynamicGameRuleManager.integerRule(MOD_ID + ":items_64_limit", CUSTOM_CATEGORY, StackSizeConfig.get().items64Limit)
            .name("64-Stack Limit")
            .description("Maximum stack size for items that naturally stack to 64.")
            .range(1, Integer.MAX_VALUE)
            .register();

        ITEMS_16_LIMIT = DynamicGameRuleManager.integerRule(MOD_ID + ":items_16_limit", CUSTOM_CATEGORY, StackSizeConfig.get().items16Limit)
            .name("16-Stack Limit")
            .description("Maximum stack size for items that naturally stack to 16.")
            .range(1, Integer.MAX_VALUE)
            .register();

        ITEMS_1_LIMIT = DynamicGameRuleManager.integerRule(MOD_ID + ":items_1_limit", CUSTOM_CATEGORY, StackSizeConfig.get().items1Limit)
            .name("1-Stack Limit")
            .description("Maximum stack size for items that naturally stack to 1.")
            .range(1, Integer.MAX_VALUE)
            .register();

        MAX_DROP_ENTITIES = DynamicGameRuleManager.integerRule(MOD_ID + ":max_drop_entities", CUSTOM_CATEGORY, StackSizeConfig.get().maxDropEntities)
            .name("Max Drop Entities")
            .description("Maximum item entities spawned per inventory slot when broken.")
            .range(1, 64)
            .register();

        // Send current limits on player join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeVarInt(StackSizeManager.getLimit64());
            buf.writeVarInt(StackSizeManager.getLimit16());
            buf.writeVarInt(StackSizeManager.getLimit1());
            sender.sendPacket(SYNC_LIMIT_ID, buf);
        });

        // Periodic sync in case gamerules are changed via in-game commands
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTickCount() % 20 == 0) {
                GameRules rules = server.getGameRules();
                int l64 = rules.getInt(ITEMS_64_LIMIT);
                int l16 = rules.getInt(ITEMS_16_LIMIT);
                int l1 = rules.getInt(ITEMS_1_LIMIT);
                int mde = rules.getInt(MAX_DROP_ENTITIES);
                if (l64 != StackSizeManager.getLimit64() || l16 != StackSizeManager.getLimit16() || l1 != StackSizeManager.getLimit1() || mde != maxDropEntities) {
                    maxDropEntities = mde;
                    StackSizeManager.setLimits(l64, l16, l1, server);
                    syncLimits(server);
                }
            }
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
