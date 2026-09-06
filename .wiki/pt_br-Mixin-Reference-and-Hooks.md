# Referência de Mixins e pontos de injeção

## Visão geral

O Stack Size Adjuster utiliza o SpongePowered Mixin para ajustar limites de empilhamento, otimizar quedas de contêineres e escalar fontes na interface.

---

## 📊 Tabela de injeção de Mixins

| Classe Mixin | Classe alvo do Minecraft | Ponto de injeção (`@At`) / Tipo | Descrição do gancho |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Modifica tamanho padrão através de `StackSizeManager`. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Modifica dinamicamente o limite da instância de item. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` em `ExtraCodecs.intRange` | Expande limite para `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` em `getMaxStackSize` | Define limite dos slots para `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` em `dropItemStack` | Redireciona quedas para `InventoryDropHelper`. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` em `getQuickCraftPlaceCount` | Utiliza ponto flutuante `double` para evitar estouros. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Intercepta `/give` com `GiveCommandHelper`. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` em `ExtraCodecs.intRange` | Modifica os limites do codec `DataComponents.MAX_STACK_SIZE`. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` em `ExtraCodecs.intRange` | Expande o codec de modelo para `Integer.MAX_VALUE`. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | Ouve mudanças nas regras e sincroniza os limites. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | Aplica redução de escala matricial no número do slot. |
