package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.instantgratification.stacksizeadjuster.util.InventoryDropHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// Verified against: Containers.java (26.2 Release)
@Mixin(Containers.class)
public class ContainersMixin {
    /**
     * @author Antigravity
     * @reason Overwriting dropItemStack to dynamically scale split size and prevent entity-spawn crashes/lag
     */
    @Overwrite
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        InventoryDropHelper.dropItemStack(level, x, y, z, itemStack);
    }
}
