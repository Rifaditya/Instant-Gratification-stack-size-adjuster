# Mixin 参考与注入点

## 概述

Stack Size Adjuster 利用 SpongePowered Mixin 将堆叠调整、掉落物优化与界面字体缩放挂钩直接植入 Minecraft 核心引擎类中。

---

## 📊 完整 Mixin 注入分析表

| Mixin 类 | 目标 Minecraft 引擎类 | 注入点 (`@At`) / 类型 | 功能与挂钩说明 |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | 使用 `StackSizeManager` 修改物品默认最大堆叠。 |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | 动态修改物品实例的堆叠上限。 |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` 于 `ExtraCodecs.intRange` | 将最大堆叠数量范围扩展至 `Integer.MAX_VALUE`。 |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` 于 `getMaxStackSize` | 将容器槽位最大堆叠上限覆盖为 `Integer.MAX_VALUE`。 |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` 于 `dropItemStack` | 将容器物品掉落逻辑委托给 `InventoryDropHelper`。 |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` 于 `getQuickCraftPlaceCount` | 采用 `double` 双精度浮点数学计算快速合成槽位分配。 |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | 拦截 `/give` 命令并通过 `GiveCommandHelper` 防止实体生成爆炸。 |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` 于 `ExtraCodecs.intRange` | 调整 `DataComponents.MAX_STACK_SIZE` 的 Codec 验证边界。 |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` 于 `ExtraCodecs.intRange` | 将模板编解码器范围扩展至 `Integer.MAX_VALUE`。 |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | 监听 GameRule 变动并实时同步当前生效的堆叠上限。 |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | 拦截槽位物品数量渲染并应用动态矩阵缩小算法。 |
