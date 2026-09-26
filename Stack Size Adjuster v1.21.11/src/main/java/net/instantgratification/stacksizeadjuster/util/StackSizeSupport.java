// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.util;

import net.dasik.social.api.SocialLinks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StackSizeSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger("StackSizeAdjuster|Support");

    private StackSizeSupport() {}

    public static String getDiscordUrl() {
        return SocialLinks.DISCORD_INVITE_URL;
    }

    public static String getKofiUrl() {
        return SocialLinks.KOFI_URL;
    }

    public static String getGithubUrl() {
        return SocialLinks.GITHUB_URL;
    }

    public static String getModPortalUrl() {
        return SocialLinks.MOD_PORTAL_URL;
    }

    public static void logSupportNotice() {
        LOGGER.info("Stack Size Adjuster | Support & Community Links: Discord: {} | Ko-fi: {} | Portal: {}",
                SocialLinks.DISCORD_INVITE_URL,
                SocialLinks.KOFI_URL,
                SocialLinks.MOD_PORTAL_URL);
    }
}
