# Référence des Mixins et points d'injection

## Vue d'ensemble

Stack Size Adjuster exploite SpongePowered Mixin pour ajuster les plafonds, optimiser les chutes d'objets et redimensionner les polices au cœur du moteur Minecraft.

---

## 📊 Tableau récapitulatif des Mixins

| Classe Mixin | Classe ciblée dans Minecraft | Point d'injection (`@At`) / Type | Rôle et description du hook |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Modifie la taille par défaut via `StackSizeManager`. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Ajuste dynamiquement la taille de pile de l'instance. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` sur `ExtraCodecs.intRange` | Étend l'intervalle à `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` sur `getMaxStackSize` | Porte la limite des slots à `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` sur `dropItemStack` | Redirige les drops vers `InventoryDropHelper`. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` sur `getQuickCraftPlaceCount` | Emploie des flottants `double` pour éviter les dépassements. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Intercepte `/give` avec `GiveCommandHelper`. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` sur `ExtraCodecs.intRange` | Ajuste le codec de `DataComponents.MAX_STACK_SIZE`. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` sur `ExtraCodecs.intRange` | Étend le codec de modèle à `Integer.MAX_VALUE`. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | Synchronise les limites lors des changements de règles. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | Applique l'échelle matricielle sur les chiffres des slots. |
