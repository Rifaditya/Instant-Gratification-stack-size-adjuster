package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.item.ItemStack;
import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.core.component.DataComponents;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;

// Verified against: ItemStack.java (26.2 Release)
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Redirect(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"
        ),
        require = 1
    )
    private static Codec<Integer> redirectCountRange(int min, int max) {
        return ExtraCodecs.intRange(1, Integer.MAX_VALUE);
    }

    public int getMaxStackSize() {
        ItemStack self = (ItemStack) (Object) this;
        int original = self.getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
        return StackSizeManager.getModifiedStackSize(self.getItem(), original);
    }
}
