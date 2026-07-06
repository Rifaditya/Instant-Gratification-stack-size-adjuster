package net.instantgratification.stacksizeadjuster.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import net.instantgratification.stacksizeadjuster.util.ItemCountRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Verified against: GuiGraphicsExtractor.java (26.2 Release)
@Mixin(GuiGraphicsExtractor.class)
public class GuiGraphicsExtractorMixin {
    @Inject(
        method = "itemCount",
        at = @At("HEAD"),
        cancellable = true
    )
    private void stacksizeadjuster$onItemCount(Font font, ItemStack itemStack, int x, int y, String countText, CallbackInfo ci) {
        ItemCountRenderer.renderItemCount((GuiGraphicsExtractor) (Object) this, font, itemStack, x, y, countText);
        ci.cancel();
    }
}
