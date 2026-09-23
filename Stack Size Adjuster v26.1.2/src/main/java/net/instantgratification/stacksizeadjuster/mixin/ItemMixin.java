package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.core.component.DataComponents;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Verified against: Item.java (26.2 Release)
@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"), cancellable = true)
    private void onGetDefaultMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        Item self = (Item) (Object) this;
        int original = self.components().getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
        int modified = StackSizeManager.getModifiedStackSize(self, original);
        if (modified != original) {
            cir.setReturnValue(modified);
        }
    }
}
