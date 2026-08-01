package net.instantgratification.stacksizeadjuster.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.level.ServerPlayer;
import net.instantgratification.stacksizeadjuster.util.GiveCommandHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

// Verified against: GiveCommand.java (26.2 Release)
@Mixin(GiveCommand.class)
public class GiveCommandMixin {
    @Inject(method = "giveItem", at = @At("HEAD"), cancellable = true)
    private static void stacksizeadjuster$onGiveItem(CommandSourceStack source, ItemInput input, Collection<ServerPlayer> players, int count, CallbackInfoReturnable<Integer> cir) throws CommandSyntaxException {
        cir.setReturnValue(GiveCommandHelper.giveItem(source, input, players, count));
    }
}
