// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster;

import net.instantgratification.stacksizeadjuster.util.CustomStackSizeOverride;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StackSizeManagerTest {

    @BeforeAll
    public static void setupMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @BeforeEach
    @AfterEach
    public void resetManagerState() throws Exception {
        StackSizeManager.setClientLimits(64, 16, 1);

        Field customOverridesField = StackSizeManager.class.getDeclaredField("CUSTOM_OVERRIDES");
        customOverridesField.setAccessible(true);
        ((List<?>) customOverridesField.get(null)).clear();

        Field overridesField = StackSizeManager.class.getDeclaredField("OVERRIDES");
        overridesField.setAccessible(true);
        ((List<?>) overridesField.get(null)).clear();
    }

    @Test
    @DisplayName("Verify default stack size limit mapping (64, 16, 1)")
    public void testDefaultLimitMapping() {
        assertEquals(64, StackSizeManager.getLimit64(), "Default limit for 64-stack items should be 64");
        assertEquals(16, StackSizeManager.getLimit16(), "Default limit for 16-stack items should be 16");
        assertEquals(1, StackSizeManager.getLimit1(), "Default limit for 1-stack items should be 1");

        assertEquals(64, StackSizeManager.getModifiedStackSize(null, 64));
        assertEquals(16, StackSizeManager.getModifiedStackSize(null, 16));
        assertEquals(1, StackSizeManager.getModifiedStackSize(null, 1));

        // Items with non-standard base sizes >= 64 or >= 16
        assertEquals(64, StackSizeManager.getModifiedStackSize(null, 100));
        assertEquals(16, StackSizeManager.getModifiedStackSize(null, 32));
    }

    @Test
    @DisplayName("Verify custom limits applied via setLimits and setLimit")
    public void testCustomLimitsViaSetLimits() {
        StackSizeManager.setLimits(128, 32, 8, null);

        assertEquals(128, StackSizeManager.getLimit64());
        assertEquals(32, StackSizeManager.getLimit16());
        assertEquals(8, StackSizeManager.getLimit1());

        assertEquals(128, StackSizeManager.getModifiedStackSize(null, 64));
        assertEquals(32, StackSizeManager.getModifiedStackSize(null, 16));
        assertEquals(8, StackSizeManager.getModifiedStackSize(null, 1));

        // Individual path update
        StackSizeManager.setLimit("items_64_limit", 256, null);
        assertEquals(256, StackSizeManager.getLimit64());
        assertEquals(256, StackSizeManager.getModifiedStackSize(null, 64));

        StackSizeManager.setLimit("items_16_limit", 64, null);
        assertEquals(64, StackSizeManager.getLimit16());
        assertEquals(64, StackSizeManager.getModifiedStackSize(null, 16));

        StackSizeManager.setLimit("items_1_limit", 16, null);
        assertEquals(16, StackSizeManager.getLimit1());
        assertEquals(16, StackSizeManager.getModifiedStackSize(null, 1));
    }

    @Test
    @DisplayName("Verify CustomStackSizeOverride precedence over general limits")
    public void testCustomStackSizeOverridePrecedence() {
        StackSizeManager.setLimits(128, 32, 1, null);

        // Register custom override: target 16-stack items and force stack size to 99
        CustomStackSizeOverride testOverride = (item, original) -> (original == 16) ? 99 : -1;
        StackSizeManager.registerOverride(testOverride);

        // Override applies to 16-stack items
        assertEquals(99, StackSizeManager.getModifiedStackSize(null, 16), "Addon override should take precedence");

        // 64-stack item unhandled by override should fall back to general category limit
        assertEquals(128, StackSizeManager.getModifiedStackSize(null, 64), "Unhandled items should defer to category limit");

        // Explicit 0 return from override is respected
        CustomStackSizeOverride zeroOverride = (item, original) -> (original == 1) ? 0 : -1;
        StackSizeManager.registerOverride(zeroOverride);
        assertEquals(0, StackSizeManager.getModifiedStackSize(null, 1), "Explicit custom override size should be returned");
    }

    @Test
    @DisplayName("Verify fail-safe guard preventing <= 0 returns for category fallback")
    public void testFailSafeGuardPreventingZeroOrNegative() {
        // If limits are configured to invalid non-positive numbers (e.g. 0 or negative)
        StackSizeManager.setLimits(0, -5, -100, null);

        // Category fallback must guard and return original value to prevent infinite loot loops
        assertEquals(64, StackSizeManager.getModifiedStackSize(null, 64), "Fail-safe must return original stack size 64");
        assertEquals(16, StackSizeManager.getModifiedStackSize(null, 16), "Fail-safe must return original stack size 16");
        assertEquals(1, StackSizeManager.getModifiedStackSize(null, 1), "Fail-safe must return original stack size 1");

        // Original <= 0 should pass through immediately without modification
        assertEquals(0, StackSizeManager.getModifiedStackSize(null, 0));
        assertEquals(-1, StackSizeManager.getModifiedStackSize(null, -1));
    }

    @Test
    @DisplayName("Verify saturated and boundary limit conditions")
    public void testSaturatedAndBoundaryConditions() {
        // Saturated Integer.MAX_VALUE limits
        StackSizeManager.setLimits(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, null);

        assertEquals(Integer.MAX_VALUE, StackSizeManager.getModifiedStackSize(null, 64));
        assertEquals(Integer.MAX_VALUE, StackSizeManager.getModifiedStackSize(null, 16));
        assertEquals(Integer.MAX_VALUE, StackSizeManager.getModifiedStackSize(null, 1));

        // Minimum positive boundary of 1
        StackSizeManager.setLimits(1, 1, 1, null);

        assertEquals(1, StackSizeManager.getModifiedStackSize(null, 64));
        assertEquals(1, StackSizeManager.getModifiedStackSize(null, 16));
        assertEquals(1, StackSizeManager.getModifiedStackSize(null, 1));
    }
}
