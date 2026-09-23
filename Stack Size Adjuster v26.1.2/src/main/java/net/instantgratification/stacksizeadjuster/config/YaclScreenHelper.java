package net.instantgratification.stacksizeadjuster.config;

// Verified against: YaclScreenHelper.java (YACL 3.9.5)
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

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
                        .description(val -> {
                            if (val > 39768215) {
                                return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.warning", val));
                            }
                            return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.limit_64.description"));
                        })
                        .binding(
                            128,
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
                        .description(val -> {
                            if (val > 39768215) {
                                return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.warning", val));
                            }
                            return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.limit_16.description"));
                        })
                        .binding(
                            32,
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
                        .description(val -> {
                            if (val > 39768215) {
                                return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.warning", val));
                            }
                            return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.limit_1.description"));
                        })
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
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.stacksizeadjuster.group.container_drops"))
 
                    // Max Drop Entities
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.stacksizeadjuster.option.max_drop_entities"))
                        .description(OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.max_drop_entities.description")))
                        .binding(
                            8,
                            () -> config.maxDropEntities,
                            val -> config.maxDropEntities = val
                        )
                        .customController(opt -> new IntegerSliderController(opt, 1, 64, 1))
                        .build()
                    )
 
                    .build()
                )
                .build()
            )
            .save(StackSizeConfig::save)
            .build()
            .generateScreen(parent);
    }
}
