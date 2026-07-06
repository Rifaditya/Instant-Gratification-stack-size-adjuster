package net.instantgratification.stacksizeadjuster.config;

import java.nio.file.Path;

public class StackSizeConfig {
    private static StackSizeConfig INSTANCE = new StackSizeConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public int items64Limit = 128;
    public int items16Limit = 32;
    public int items1Limit = 1;

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("stack-size-adjuster.json");
        INSTANCE = net.dasik.social.api.config.ConfigHelper.load(
            CONFIG_PATH, INSTANCE, StackSizeConfig.class, VERSION,
            config -> config.configVersion, (config, ver) -> config.configVersion = ver,
            null, org.slf4j.LoggerFactory.getLogger("StackSizeAdjuster")
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        net.dasik.social.api.config.ConfigHelper.save(CONFIG_PATH, INSTANCE, org.slf4j.LoggerFactory.getLogger("StackSizeAdjuster"));
    }

    public static StackSizeConfig get() {
        return INSTANCE;
    }
}
