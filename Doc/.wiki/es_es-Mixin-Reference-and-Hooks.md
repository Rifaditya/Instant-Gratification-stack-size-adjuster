# Referencia de Mixins y puntos de inyección

## Resumen

Stack Size Adjuster emplea SpongePowered Mixins para ajustar los límites de apilamiento, optimizar caídas y renderizar fuentes directamente en el motor de Minecraft.

---

## 📊 Tabla completa de inyección de Mixins

| Clase Mixin | Clase objetivo de Minecraft | Punto de inyección (`@At`) / Tipo | Propósito y descripción del hook |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Modifica el tamaño predeterminado con `StackSizeManager`. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Modifica el límite de pila de la instancia de forma dinámica. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` en `ExtraCodecs.intRange` | Expande el rango de pila a `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` en `getMaxStackSize` | Anula el límite de la ranura a `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` en `dropItemStack` | Delega las caídas en `InventoryDropHelper`. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` en `getQuickCraftPlaceCount` | Usa matemáticas `double` para evitar desbordamientos. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Intercepta `/give` para prevenir saturación de entidades. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` en `ExtraCodecs.intRange` | Ajusta los límites del códec de `DataComponents.MAX_STACK_SIZE`. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` en `ExtraCodecs.intRange` | Amplía el códec de plantilla a `Integer.MAX_VALUE`. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | Escucha cambios de GameRules y sincroniza límites activos. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | Anula la fuente de cantidad de ranura con escalado dinámico. |
