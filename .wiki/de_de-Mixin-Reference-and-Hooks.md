# Mixin-Referenz & Injection-Points

## Übersicht

Stack Size Adjuster verwendet SpongePowered Mixins, um Stapelgrenzen, Drop-Verhalten und Schriftendarstellung direkt im Minecraft-Kern anzupassen.

---

## 📊 Vollständige Mixin-Übersichtstabelle

| Mixin-Klasse | Zielklasse in Minecraft | Injection-Point (`@At`) / Typ | Zweck & Beschreibung des Hooks |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Modifiziert Standard-Stapelgröße via `StackSizeManager`. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Modifiziert Stapellimit dynamisch für ItemInstances. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` auf `ExtraCodecs.intRange` | Erweitert Stapelgrößen-Codec auf `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` auf `getMaxStackSize` | Setzt Behälter-Slotlimit auf `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` auf `dropItemStack` | Leitet Behälter-Drops an `InventoryDropHelper` weiter. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` auf `getQuickCraftPlaceCount` | Verhindert Überläufe beim Verteilen mittels `double`. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Fängt `/give` via `GiveCommandHelper` ab. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` auf `ExtraCodecs.intRange` | Passt Codec-Grenzen für `MAX_STACK_SIZE` an. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` auf `ExtraCodecs.intRange` | Erweitert Vorlagen-Codec auf `Integer.MAX_VALUE`. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | Synchronisiert Limits bei GameRule-Änderungen. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | Skaliert Zahlenanzeige bei großen Mengen herunter. |
