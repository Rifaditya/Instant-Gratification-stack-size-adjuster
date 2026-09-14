package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.world.item.ItemStackTemplate;
import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Verified against: ItemStackTemplate.java (26.2 Release)
@Mixin(ItemStackTemplate.class)
public class ItemStackTemplateMixin {
    @Redirect(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"
        ),
        require = 1
    )
    private static Codec<Integer> redirectTemplateCountRange(int min, int max) {
        return ExtraCodecs.intRange(1, Integer.MAX_VALUE);
    }
}
