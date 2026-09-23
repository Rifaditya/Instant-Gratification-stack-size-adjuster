# Mixin Reference & Hooks

## Overview

Stack Size Adjuster utilizes SpongePowered Mixins to adjust stack size limits, container drop limits, and font rendering directly within Minecraft's core engine classes. Due to Minecraft's architectural evolution across eras, injection targets differ between versions.

---

## 📊 Modern & Intermediate Architecture (MC 26.x & MC 1.21.x)

| Mixin Class | Target Minecraft Class | Injection Point (`@At`) / Type | Purpose & Hook Description |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Modifies default item stack size using `StackSizeManager`. |
| `ItemInstanceMixin` *(26.x)* | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Modifies item instance stack limit dynamically. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` on `ExtraCodecs.intRange` | Expands max stack size range to `Integer.MAX_VALUE`. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` on `ExtraCodecs.intRange` | Unclamps codec bounds for `DataComponents.MAX_STACK_SIZE`. |
| `ItemStackTemplateMixin` *(26.x)* | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` on `ExtraCodecs.intRange` | Expands template codec range to `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` on `getMaxStackSize` | Overrides container slot stack limit to `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` on `dropItemStack` | Delegates container item drops to `InventoryDropHelper`. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` on `getQuickCraftPlaceCount` | Uses `double` precision math to prevent quick-crafting divide wrap. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Intercepts `/give` to prevent entity overflow via `GiveCommandHelper`. |
| `GuiGraphicsMixin` *(1.21.x)* | `net.minecraft.client.gui.GuiGraphics` | `@Inject(method = "renderItemDecorations", at = @At("HEAD"))` | Renders compact / scaled font for high stack counts. |

---

## 🏛️ Legacy Architecture (MC 1.20.1)

In Minecraft 1.20.1, `DataComponents` do not exist. Stack limits are enforced directly on `Item` and `ItemStack`:

| Mixin Class | Target Minecraft Class | Injection Point (`@At`) / Type | Purpose & Hook Description |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Direct interception modifying base item stack size. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Direct interception on item stack instances. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` on `getMaxStackSize` | Overrides container slot stack limit to `Integer.MAX_VALUE`. |
| `ItemRendererMixin` | `net.minecraft.client.renderer.entity.ItemRenderer` | `@Inject(method = "renderGuiItemDecorations", at = @At("HEAD"))` | Scaled item count text for multi-digit quantities. |
