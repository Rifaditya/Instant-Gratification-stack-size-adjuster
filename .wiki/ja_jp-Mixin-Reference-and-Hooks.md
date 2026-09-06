# Mixin リファレンスとフック

## 概要

Stack Size Adjuster は SpongePowered Mixin を使用して、スタック上限やドロップ挙動、フォント描画のフックを Minecraft のコアエンジンに注入します。

---

## 📊 Mixin 注入ポイント一覧

| Mixin クラス | 対象 Minecraft クラス | 注入ポイント (`@At`) / 種別 | 説明と役割 |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | `StackSizeManager` を用いてデフォルトスタック数を変更。 |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | アイテムインスタンスのスタック上限を動的に変更。 |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` (`ExtraCodecs.intRange`) | 最大スタック数範囲を `Integer.MAX_VALUE` に拡張。 |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` (`getMaxStackSize`) | コンテナ枠の最大スタック数を `Integer.MAX_VALUE` に上書き。 |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` (`dropItemStack`) | コンテナ破壊時のドロップを `InventoryDropHelper` に委譲。 |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` (`getQuickCraftPlaceCount`) | オーバーフローを防ぐため `double` 精度で計算。 |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | `/give` をフックして `GiveCommandHelper` で安全に処理。 |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` (`ExtraCodecs.intRange`) | `DataComponents.MAX_STACK_SIZE` コーデックの境界を変更。 |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` (`ExtraCodecs.intRange`) | テンプレートコーデックを `Integer.MAX_VALUE` に拡張。 |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | GameRule 変更を監視して制限値を同期。 |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | スロットの個数表示を縮小マトリックスで描画。 |
