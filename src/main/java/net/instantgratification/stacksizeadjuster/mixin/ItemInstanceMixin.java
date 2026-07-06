package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.item.ItemInstance;
import net.minecraft.core.component.DataComponents;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// Verified against: ItemInstance.java (26.2 Release)
@Mixin(ItemInstance.class)
public interface ItemInstanceMixin extends ItemInstance {
    /**
     * @author Antigravity
     * @reason Overriding max stack size dynamically based on the category limit
     */
    @Overwrite
    default int getMaxStackSize() {
        int original = this.getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
        return StackSizeManager.getModifiedStackSize(original);
    }
}
