// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ItemCountRenderer {

    public static String formatCount(int count) {
        if (count < 1000) {
            return String.valueOf(count);
        } else if (count < 1_000_000) {
            return (count / 1000) + "K";
        } else if (count < 1_000_000_000) {
            return (count / 1_000_000) + "M";
        } else {
            return (count / 1_000_000_000) + "B";
        }
    }

    public static void renderItemCount(GuiGraphics graphics, Font font, ItemStack itemStack, int x, int y, String countText) {
        if (itemStack.getCount() != 1 || countText != null) {
            String amount = countText == null ? formatCount(itemStack.getCount()) : countText;
            int textWidth = font.width(amount);

            float maxAllowedWidth = 16.0f;
            if (textWidth > maxAllowedWidth) {
                float scale = maxAllowedWidth / textWidth;

                PoseStack pose = graphics.pose();
                pose.pushPose();

                float targetRight = x + 17;
                float targetBottom = y + 9;

                pose.translate(targetRight, targetBottom, 200.0f);
                pose.scale(scale, scale, 1.0f);

                graphics.drawString(font, amount, -textWidth, 0, 16777215, true);

                pose.popPose();
            } else {
                graphics.drawString(font, amount, x + 17 - textWidth, y + 9, 16777215, true);
            }
        }
    }
}
