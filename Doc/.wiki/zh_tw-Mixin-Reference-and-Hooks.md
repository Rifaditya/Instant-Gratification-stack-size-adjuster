# Mixin 參考與注入點

## 概述

Stack Size Adjuster 利用 SpongePowered Mixin 將堆疊調整、掉落物最佳化與介面字型縮放掛鉤直接植入 Minecraft 核心引擎類別中。

---

## 📊 完整 Mixin 注入分析表

| Mixin 類別 | 目標 Minecraft 引擎類別 | 注入點 (`@At`) / 類型 | 功能與掛鉤說明 |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | 使用 `StackSizeManager` 修改物品預設最大堆疊。 |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | 動態修改物品實例的堆疊上限。 |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` 於 `ExtraCodecs.intRange` | 將最大堆疊數量範圍擴展至 `Integer.MAX_VALUE`。 |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` 於 `getMaxStackSize` | 將容器槽位最大堆疊上限覆蓋為 `Integer.MAX_VALUE`。 |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` 於 `dropItemStack` | 將容器物品掉落邏輯委託給 `InventoryDropHelper`。 |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` 於 `getQuickCraftPlaceCount` | 採用 `double` 雙精度浮點數學計算快速合成槽位分配。 |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | 攔截 `/give` 指令並透過 `GiveCommandHelper` 防止實體生成爆炸。 |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` 於 `ExtraCodecs.intRange` | 調整 `DataComponents.MAX_STACK_SIZE` 的 Codec 驗證邊界。 |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` 於 `ExtraCodecs.intRange` | 將範本編解碼器範圍擴充至 `Integer.MAX_VALUE`。 |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | 監聽 GameRule 變動並即時同步當前生效的堆疊上限。 |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | 攔截槽位物品數量渲染並套用動態矩陣縮小演算法。 |
