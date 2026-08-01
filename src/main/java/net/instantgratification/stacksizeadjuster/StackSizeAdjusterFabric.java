package net.instantgratification.stacksizeadjuster;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;
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
    public static GameRule<Integer> MAX_DROP_ENTITIES;

    @Override
    public void onInitialize() {
        net.instantgratification.stacksizeadjuster.util.ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
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

        MAX_DROP_ENTITIES = DynamicGameRuleManager.integerRule(MOD_ID + ":max_drop_entities", CUSTOM_CATEGORY, StackSizeConfig.get().maxDropEntities)
            .name("Max Drop Entities")
            .description("Maximum item entities spawned per inventory slot when a container breaks. Vanilla has no cap and spawns thousands of entities for large stacks. Default: 8 (recommended). Set to 1 for maximum performance.")
            .range(1, 64)
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
            // Reload config baseline template to fetch main-menu updates
            StackSizeConfig.load(FabricLoader.getInstance().getConfigDir());

            GameRules rules = server.getGameRules();
            
            // If the world is newly created (not initialized yet), apply the baseline config template directly to the active GameRules
            if (!server.getWorldData().overworldData().isInitialized()) {
                rules.set(ITEMS_64_LIMIT, StackSizeConfig.get().items64Limit, server);
                rules.set(ITEMS_16_LIMIT, StackSizeConfig.get().items16Limit, server);
                rules.set(ITEMS_1_LIMIT, StackSizeConfig.get().items1Limit, server);
                rules.set(MAX_DROP_ENTITIES, StackSizeConfig.get().maxDropEntities, server);
            }

            int l64 = rules.get(ITEMS_64_LIMIT);
            int l16 = rules.get(ITEMS_16_LIMIT);
            int l1 = rules.get(ITEMS_1_LIMIT);
            StackSizeManager.setLimits(l64, l16, l1, server);
        });
    }
}
