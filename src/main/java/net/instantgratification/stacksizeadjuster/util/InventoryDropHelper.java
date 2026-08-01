package net.instantgratification.stacksizeadjuster.util;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.instantgratification.stacksizeadjuster.StackSizeAdjusterFabric;

public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        double size = EntityTypes.ITEM.getWidth();
        double centerRange = 1.0 - size;
        double halfSize = size / 2.0;
        RandomSource random = level.getRandom();
        double xo = Math.floor(x) + random.nextDouble() * centerRange + halfSize;
        double yo = Math.floor(y) + random.nextDouble() * centerRange;
        double zo = Math.floor(z) + random.nextDouble() * centerRange + halfSize;

        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
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
