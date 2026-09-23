// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// Verified against: Container.java (1.21.11)
@Mixin(Container.class)
public interface ContainerMixin {
    /**
     * @author Antigravity
     * @reason Overriding max container stack size limit to support large stack sizes
     */
    @Overwrite
    default int getMaxStackSize() {
        return Integer.MAX_VALUE;
    }
}
