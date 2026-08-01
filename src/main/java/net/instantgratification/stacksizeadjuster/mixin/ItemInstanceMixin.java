package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.item.ItemInstance;
import net.minecraft.core.component.DataComponents;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Verified against: ItemInstance.java (26.2 Release)
@Mixin(ItemInstance.class)
public interface ItemInstanceMixin extends ItemInstance {
    @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
    default void onGetMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        int original = this.getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
        int modified = StackSizeManager.getModifiedStackSize(this.typeHolder().value(), original);
        if (modified != original) {
            cir.setReturnValue(modified);
        }
    }
}
