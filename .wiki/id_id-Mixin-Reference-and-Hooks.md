# Referensi Mixin & Hook

## Gambaran Umum

Stack Size Adjuster menggunakan SpongePowered Mixin untuk mengintegrasikan penyesuaian batas tumpukan, optimasi drop, dan rendering font langsung ke mesin Minecraft.

---

## 📊 Tabel Injeksi Mixin Lengkap

| Kelas Mixin | Kelas Target Minecraft | Titik Injeksi (`@At`) / Tipe | Deskripsi Tujuan Hook |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | Menyesuaikan batas tumpukan lewat `StackSizeManager`. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | Mengubah batas stack item secara dinamis. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` pada `ExtraCodecs.intRange` | Memperlebar batas hingga `Integer.MAX_VALUE`. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` pada `getMaxStackSize` | Menimpa batas slot kontainer ke `Integer.MAX_VALUE`. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` pada `dropItemStack` | Mengalihkan penanganan drop ke `InventoryDropHelper`. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` pada `getQuickCraftPlaceCount` | Menghitung pembagian crafting dengan presisi `double`. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | Mengamankan `/give` melalui `GiveCommandHelper`. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` pada `ExtraCodecs.intRange` | Menyesuaikan batas codec `MAX_STACK_SIZE`. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` pada `ExtraCodecs.intRange` | Memperlebar batas codec template ke `Integer.MAX_VALUE`. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | Menangkap perubahan GameRules dan sinkronisasi batas. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | Menerapkan skala matriks pada rendering jumlah item. |
