package net.instantgratification.stacksizeadjuster;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.resources.Identifier;
import net.instantgratification.stacksizeadjuster.config.StackSizeConfig;
import net.instantgratification.stacksizeadjuster.network.StackSizeLimitSyncPayload;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Verified against: ModInitializer.java (Fabric API)
// Verified against: DynamicGameRuleManager.java (DasikLibrary 1.8.2)
public class StackSizeAdjusterFabric implements ModInitializer {
    public static final String MOD_ID = "stack-size-adjuster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GameRuleCategory CUSTOM_CATEGORY = DynamicGameRuleManager.registerCategory(
        Identifier.fromNamespaceAndPath(MOD_ID, MOD_ID)
    );

    public static GameRule<Integer> ITEMS_64_LIMIT;
    public static GameRule<Integer> ITEMS_16_LIMIT;
    public static GameRule<Integer> ITEMS_1_LIMIT;

    @Override
    public void onInitialize() {
        LOGGER.info("Instant Gratification: Stack Size Adjuster Initialized");

        // Load config baseline template
        StackSizeConfig.load(FabricLoader.getInstance().getConfigDir());

        // Register GameRules with dynamic defaults loaded from baseline config
        ITEMS_64_LIMIT = DynamicGameRuleManager.integerRule(MOD_ID + ":items_64_limit", CUSTOM_CATEGORY, StackSizeConfig.get().items64Limit)
            .name("64-Stack Limit")
            .description("Maximum stack size for items that naturally stack to 64. Default: 128")
            .range(1, Integer.MAX_VALUE)
            .register();

        ITEMS_16_LIMIT = DynamicGameRuleManager.integerRule(MOD_ID + ":items_16_limit", CUSTOM_CATEGORY, StackSizeConfig.get().items16Limit)
            .name("16-Stack Limit")
            .description("Maximum stack size for items that naturally stack to 16. Default: 32")
            .range(1, Integer.MAX_VALUE)
            .register();

        ITEMS_1_LIMIT = DynamicGameRuleManager.integerRule(MOD_ID + ":items_1_limit", CUSTOM_CATEGORY, StackSizeConfig.get().items1Limit)
            .name("1-Stack Limit")
            .description("Maximum stack size for items that naturally stack to 1. Default: 1")
            .range(1, Integer.MAX_VALUE)
            .register();

        // Register Payload S2C
        PayloadTypeRegistry.clientboundPlay().register(StackSizeLimitSyncPayload.TYPE, StackSizeLimitSyncPayload.CODEC);

        // Sync limits to client when player joins the world
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(new StackSizeLimitSyncPayload(
                StackSizeManager.getLimit64(),
                StackSizeManager.getLimit16(),
                StackSizeManager.getLimit1()
            ));
        });

        // Initialize/update active limits on server starting
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            int l64 = server.getGameRules().get(ITEMS_64_LIMIT);
            int l16 = server.getGameRules().get(ITEMS_16_LIMIT);
            int l1 = server.getGameRules().get(ITEMS_1_LIMIT);
            StackSizeManager.setLimits(l64, l16, l1, server);
        });
    }
}
