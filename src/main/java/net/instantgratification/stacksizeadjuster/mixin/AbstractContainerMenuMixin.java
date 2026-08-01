package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// Verified against: AbstractContainerMenu.java (26.2 Release)
@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    /**
     * @author Antigravity
     * @reason Prevent precision loss when dividing extremely large stack sizes by using double precision math instead of float.
     */
    @Overwrite
    public static int getQuickCraftPlaceCount(int quickCraftSlotsSize, int quickCraftingType, ItemStack itemStack) {
        return switch (quickCraftingType) {
            case 0 -> (int) ((double) itemStack.getCount() / (double) quickCraftSlotsSize);
            case 1 -> 1;
            case 2 -> itemStack.getMaxStackSize();
            default -> itemStack.getCount();
        };
    }
}
