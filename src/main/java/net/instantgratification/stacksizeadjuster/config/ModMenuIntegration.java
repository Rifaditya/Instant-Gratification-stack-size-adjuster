package net.instantgratification.stacksizeadjuster.config;

// Verified against: ModMenuIntegration.java (26.2+)
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.dasik.social.api.config.GuiHelper;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
            "stack-size-adjuster",
            "net.instantgratification.stacksizeadjuster.config.YaclScreenHelper",
            "createScreen"
        );
    }
}
