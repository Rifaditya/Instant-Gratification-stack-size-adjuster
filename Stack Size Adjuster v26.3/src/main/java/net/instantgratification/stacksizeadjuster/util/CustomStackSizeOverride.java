// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.world.item.Item;

@FunctionalInterface
public interface CustomStackSizeOverride {
    /**
     * Calculates the custom maximum stack size for the given item.
     * 
     * @param item The target item.
     * @param original The original max stack size of the item.
     * @return Non-negative integer (>= 0) if explicitly overridden by this addon,
     *         or -1 if this override does not apply (deferring to general category rules).
     */
    int getCustomStackSize(Item item, int original);
}
