package net.instantgratification.stacksizeadjuster.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.resources.Identifier;
import net.instantgratification.stacksizeadjuster.util.StackSizeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Verified against: MinecraftServer.java (26.2 Release)
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "onGameRuleChanged", at = @At("TAIL"))
    private <T> void onGameRuleChanged(GameRule<T> rule, T value, CallbackInfo ci) {
        Identifier ruleId = rule.getIdentifier();
        if (ruleId != null && ruleId.getNamespace().equals("stack-size-adjuster")) {
            if (value instanceof Integer intVal) {
                StackSizeManager.setLimit(ruleId.getPath(), intVal, (MinecraftServer) (Object) this);
            }
        }
    }
}
