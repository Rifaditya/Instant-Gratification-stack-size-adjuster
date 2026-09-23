// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.mixin.client;

import net.instantgratification.stacksizeadjuster.util.ItemCountRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// Verified against: GuiGraphics.java (1.21.1)
@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
    @ModifyVariable(
        method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
        at = @At("HEAD"),
        argsOnly = true
    )
    private String stacksizeadjuster$formatItemCount(String text, Font font, ItemStack stack, int x, int y) {
        if (text == null && !stack.isEmpty() && stack.getCount() >= 1000) {
            return ItemCountRenderer.formatCount(stack.getCount());
        }
        return text;
    }
}
