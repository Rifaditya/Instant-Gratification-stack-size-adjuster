// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.instantgratification.stacksizeadjuster.StackSizeAdjusterFabric;

// Verified against: EntityType.java (1.20.1)
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        double size = EntityType.ITEM.getWidth();
        double centerRange = 1.0 - size;
        double halfSize = size / 2.0;
        RandomSource random = level.getRandom();
        double xo = Math.floor(x) + random.nextDouble() * centerRange + halfSize;
        double yo = Math.floor(y) + random.nextDouble() * centerRange;
        double zo = Math.floor(z) + random.nextDouble() * centerRange + halfSize;

        int maxEntities = StackSizeAdjusterFabric.maxDropEntities;
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Safety limit: if count is extremely large, split into at most maxEntities entities per stack
            if (currentCount > splitSize * maxEntities) {
                splitSize = (currentCount + maxEntities - 1) / maxEntities;
            }
            
            ItemEntity entity = new ItemEntity(level, xo, yo, zo, itemStack.split(splitSize));
            entity.setDeltaMovement(
                random.triangle(0.0, 0.11485000171139836),
                random.triangle(0.2, 0.11485000171139836),
                random.triangle(0.0, 0.11485000171139836)
            );
            level.addFreshEntity(entity);
        }
    }
}
