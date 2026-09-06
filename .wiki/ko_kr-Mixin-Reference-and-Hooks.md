# Mixin 참조 및 훅

## 개요

Stack Size Adjuster는 SpongePowered Mixin을 사용하여 Minecraft 코어 엔진 클래스에 스택 크기 조정, 드롭 제어 및 폰트 렌더링 훅을 주입합니다.

---

## 📊 전체 Mixin 주입 상세 표

| Mixin 클래스 | 대상 Minecraft 클래스 | 주입 지점 (`@At`) / 타입 | 목적 및 훅 설명 |
| :--- | :--- | :--- | :--- |
| `ItemMixin` | `net.minecraft.world.item.Item` | `@Inject(method = "getDefaultMaxStackSize", at = @At("RETURN"))` | `StackSizeManager`를 통해 기본 스택 크기 변경. |
| `ItemInstanceMixin` | `net.minecraft.world.item.ItemInstance` | `@Inject(method = "getMaxStackSize", at = @At("RETURN"))` | 아이템 인스턴스의 스택 한도를 동적으로 변경. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@Redirect` (`ExtraCodecs.intRange`) | 최대 스택 범위를 `Integer.MAX_VALUE`로 확장. |
| `ContainerMixin` | `net.minecraft.world.Container` | `@Overwrite` (`getMaxStackSize`) | 컨테이너 슬롯 제한을 `Integer.MAX_VALUE`로 변경. |
| `ContainersMixin` | `net.minecraft.world.Containers` | `@Overwrite` (`dropItemStack`) | 컨테이너 드롭 처리를 `InventoryDropHelper`로 위임. |
| `AbstractContainerMenuMixin` | `net.minecraft.world.inventory.AbstractContainerMenu` | `@Overwrite` (`getQuickCraftPlaceCount`) | 오버플로 방지를 위해 `double` 연산 사용. |
| `GiveCommandMixin` | `net.minecraft.server.commands.GiveCommand` | `@Inject(method = "giveItem", at = @At("HEAD"))` | `/give`를 가로채어 `GiveCommandHelper`로 안전하게 처리. |
| `DataComponentsMixin` | `net.minecraft.core.component.DataComponents` | `@Redirect` (`ExtraCodecs.intRange`) | `DataComponents.MAX_STACK_SIZE` 코덱 범위 조정. |
| `ItemStackTemplateMixin` | `net.minecraft.world.item.ItemStackTemplate` | `@Redirect` (`ExtraCodecs.intRange`) | 템플릿 코덱 범위를 `Integer.MAX_VALUE`로 확장. |
| `MinecraftServerMixin` | `net.minecraft.server.MinecraftServer` | `@Inject(method = "onGameRuleChanged", at = @At("TAIL"))` | GameRule 변경을 감지하여 한도 동기화. |
| `GuiGraphicsExtractorMixin` | `net.minecraft.client.gui.GuiGraphicsExtractor` | `@Inject(method = "itemCount", at = @At("HEAD"))` | 슬롯 수량 텍스트 렌더링에 축소 행렬 적용. |
