package net.instantgratification.stacksizeadjuster.config;

// Verified against: YaclScreenHelper.java (YACL 3.9.5)
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRules;
import net.instantgratification.stacksizeadjuster.StackSizeAdjusterFabric;

public class YaclScreenHelper {
    public static ConfigScreenFactory<?> createScreen() {
        return YaclScreenHelper::buildScreen;
    }

    private static Screen buildScreen(Screen parent) {
        StackSizeConfig config = StackSizeConfig.get();

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("config.stacksizeadjuster.title"))
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.stacksizeadjuster.category.general"))
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.stacksizeadjuster.group.categories"))

                    // Items 64 category
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.stacksizeadjuster.option.limit_64"))
                        .description(OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.limit_64.description")))
                        .binding(
                            64,
                            () -> config.items64Limit,
                            val -> config.items64Limit = val
                        )
                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .min(1)
                            .max(Integer.MAX_VALUE)
                        )
                        .build()
                    )

                    // Items 16 category
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.stacksizeadjuster.option.limit_16"))
                        .description(OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.limit_16.description")))
                        .binding(
                            16,
                            () -> config.items16Limit,
                            val -> config.items16Limit = val
                        )
                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .min(1)
                            .max(Integer.MAX_VALUE)
                        )
                        .build()
                    )

                    // Items 1 category
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.stacksizeadjuster.option.limit_1"))
                        .description(OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.limit_1.description")))
                        .binding(
                            1,
                            () -> config.items1Limit,
                            val -> config.items1Limit = val
                        )
                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .min(1)
                            .max(Integer.MAX_VALUE)
                        )
                        .build()
                    )

                    .build()
                )
                .build()
            )
            .save(() -> {
                StackSizeConfig.save();
                
                // If in-game, update the integrated server's gamerules
                Minecraft client = Minecraft.getInstance();
                if (client != null && client.getSingleplayerServer() != null) {
                    MinecraftServer server = client.getSingleplayerServer();
                    GameRules rules = server.getGameRules();
                    rules.set(StackSizeAdjusterFabric.ITEMS_64_LIMIT, config.items64Limit, server);
                    rules.set(StackSizeAdjusterFabric.ITEMS_16_LIMIT, config.items16Limit, server);
                    rules.set(StackSizeAdjusterFabric.ITEMS_1_LIMIT, config.items1Limit, server);
                }
            })
            .build()
            .generateScreen(parent);
    }
}
