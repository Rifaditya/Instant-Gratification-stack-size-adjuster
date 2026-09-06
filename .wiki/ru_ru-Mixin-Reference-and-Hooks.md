# Справочник Mixin и точки внедрения

## Обзор

Stack Size Adjuster использует SpongePowered Mixin для изменения лимитов стаков, оптимизации выпадения предметов и масштабирования шрифтов интерфейса.

---

## 📊 Таблица внедрения Mixin

| Класс Mixin | Целевой класс Minecraft | Точка внедрения (`@At`) / Тип | Описание назначения хука |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Изменяет размер стака предмета через `StackSizeManager`. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Динамически изменяет лимит стака экземпляра предмета. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` в `ExtraCodecs.intRange` | Расширяет диапазон стака до `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` метода `getMaxStackSize` | Задает лимит слотов контейнера в `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` метода `dropItemStack` | Перенаправляет выпадение предметов в `InventoryDropHelper`. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` метода `getQuickCraftPlaceCount` | Использует `double` вычисления во избежание переполнения. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Перехватывает `/give` для предотвращения перегрузки сервера. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` в `ExtraCodecs.intRange` | Модифицирует кодек `DataComponents.MAX_STACK_SIZE`. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` в `ExtraCodecs.intRange` | Расширяет кодек шаблона до `Integer.MAX_VALUE`. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | Отслеживает изменения правил и синхронизирует лимиты. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | Заменяет рендеринг количества предметов матричным сжатием. |
