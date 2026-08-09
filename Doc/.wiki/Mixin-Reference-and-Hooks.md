# Mixin Reference & Hooks

## Overview

Stack Size Adjuster utilizes SpongePowered Mixins to adjust stack size limits, container drop limits, and font rendering directly within Minecraft's core engine classes.

---

## 📊 Complete Mixin Injection Breakdown Table

| Mixin Class | Target Minecraft Class | Injection Point (`@At`) / Type | Purpose & Hook Description |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Modifies default item stack size using `StackSizeManager`. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Modifies item instance stack limit dynamically. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` on `ExtraCodecs.intRange` | Expands max stack size range to `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` on `getMaxStackSize` | Overrides container slot stack limit to `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` on `dropItemStack` | Delegates container item drops to `InventoryDropHelper`. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` on `getQuickCraftPlaceCount` | Uses `double` precision math to prevent quick-crafting divide wrap. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Intercepts `/give` to prevent entity overflow via `GiveCommandHelper`. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` on `ExtraCodecs.intRange` | Modifies codec bounds for `DataComponents.MAX_STACK_SIZE`. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` on `ExtraCodecs.intRange` | Expands template codec range to `Integer.MAX_VALUE`. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | Listens to GameRule updates and synchronizes active limits. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | Overrides slot item count text rendering with scale-down matrix. |
