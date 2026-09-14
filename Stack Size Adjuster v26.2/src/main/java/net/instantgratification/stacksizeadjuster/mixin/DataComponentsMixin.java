package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.core.component.DataComponents;
import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Verified against: DataComponents.java (26.2 Release)
@Mixin(DataComponents.class)
public class DataComponentsMixin {
    @Redirect(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"
        ),
        require = 1
    )
    private static Codec<Integer> redirectMaxStackSizeRange(int min, int max) {
        return ExtraCodecs.intRange(1, Integer.MAX_VALUE);
    }
}
