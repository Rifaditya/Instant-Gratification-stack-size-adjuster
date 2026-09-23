// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet-another-config-lib")
                || FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            try {
                Class<?> helperClass = Class.forName(
                    "net.instantgratification.stacksizeadjuster.config.YaclScreenHelper",
                    false,
                    Thread.currentThread().getContextClassLoader()
                );
                java.lang.reflect.Method method = helperClass.getMethod("createScreen");
                return (ConfigScreenFactory<?>) method.invoke(null);
            } catch (Exception ignored) {
                // Fallback gracefully without crash
            }
        }
        return parent -> null;
    }
}
