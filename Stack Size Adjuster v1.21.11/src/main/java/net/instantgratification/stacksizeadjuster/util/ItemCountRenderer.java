// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

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

                Matrix3x2fStack pose = graphics.pose();
                pose.pushMatrix();

                float targetRight = x + 17;
                float targetBottom = y + 9;

                pose.translate(targetRight, targetBottom);
                pose.scale(scale, scale);

                graphics.drawString(font, amount, -textWidth, 0, 16777215, true);

                pose.popMatrix();
            } else {
                graphics.drawString(font, amount, x + 17 - textWidth, y + 9, 16777215, true);
            }
        }
    }
}
