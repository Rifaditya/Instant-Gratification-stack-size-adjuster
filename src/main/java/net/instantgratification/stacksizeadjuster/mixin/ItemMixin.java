package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.core.component.DataComponents;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// Verified against: Item.java (26.2 Release)
@Mixin(Item.class)
public class ItemMixin {
    /**
     * @author Antigravity
     * @reason Overriding default max stack size dynamically based on the category limit
     */
    @Overwrite
    public int getDefaultMaxStackSize() {
        Item self = (Item) (Object) this;
        int original = self.components().getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
        return StackSizeManager.getModifiedStackSize(original);
    }
}
