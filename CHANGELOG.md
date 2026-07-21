# Changelog

## [1.4.14+26.2] - 2026-07-14

### Fixed
- **Give Command Integer Overflow**: Fixed integer overflow bug when giving items with very high stack size limits. The `/give` command's maximumallowed count calculation (`maxStackSize * 100`) is now processed using `long` values and capped to `Integer.MAX_VALUE` to prevent negative limit results (like `-80`) that blocked players from using `/give` on command blocks and other stack-adjusted items.

## [1.4.12+26.2] - 2026-07-11

### Fixed
- Fixed launch crash caused by `InvalidInjectionException` on `ItemStackMixin`. Reverted the `@Inject` on `getMaxStackSize()` back to a direct public method override, resolving the target method lookup failure on Minecraft 26.2.

## [1.4.11+26.2] - 2026-07-11

### Removed
- Removed live GameRule sync on config save. Changing config values now only defines default settings for new worlds, allowing each world to maintain independent GameRule settings.

## [1.4.10+26.2] - 2026-07-11

### Added
- Added programmatic Addon Override API in `StackSizeManager`. Addon mods (like Potion Stacker) can now register dynamic override functions to adjust stack sizes programmatically.
- Refactored mixins from `@Overwrite` to non-conflicting `@Inject(at = @At("RETURN"), cancellable = true)` to support addon mod coexistence and prevent startup collisions.

## [1.4.9+26.2] - 2026-07-11

### Fixed
- Fixed global configuration not updating active GameRules on newly created worlds. The mod now reloads the global config baseline during the `SERVER_STARTED` event and copies template values directly to GameRules if the world is not initialized yet (`!isInitialized()`).
- Added prominent notice to config tooltips clarifying that global JSON configurations only define the default values for newly generated worlds, and existing worlds must be modified in-game.

## [1.4.8+26.2] - 2026-07-11

### Removed
- Removed the dedicated "Potion Limit" setting and GameRule. Reverted potion-stacking configuration from this mod entirely.
- All potion-stacking capabilities have been deferred to the future standalone addon mod (Potion Stacker).

## [1.4.7+26.2] - 2026-07-08 [DEPRECATED / SUPERSEDED]

### Added
- Added dedicated **"Potion Limit"** configuration setting and GameRule (default: 16). Superseded by `1.4.8+26.2` due to migration to dedicated addon mod.

## [1.4.6+26.2] - 2026-07-08 [DEPRECATED / SUPERSEDED]

### Fixed
- **Design Flaw**: Grouped potions and other 1-stack items (like beds/swords) under the same setting, causing beds and swords to stack. Superseded by `1.4.7+26.2`.

- Fixed dynamic stack size adjustments for 16-stack items (like eggs, ender pearls, snowballs) and 1-stack items (like potions). Replaced strict equality limit checks in `StackSizeManager.getModifiedStackSize()` with clean category mapping to allow YACL's default `items16Limit = 32` and any custom limit values to take effect correctly.

## [1.4.5+26.2] - 2026-07-08

### Fixed

- Fixed item duplication and leftovers glitch when drag-splitting and consolidating massive stack sizes (up to 2.14 billion). Overrode `AbstractContainerMenu.getQuickCraftPlaceCount()` to compute slot distributions using double precision math to prevent float rounding errors.

## [1.4.4+26.2] - 2026-07-08

### Fixed

- Fixed double-click stack consolidation duplication glitch. Explicitly defined `getMaxStackSize()` directly on `ItemStack` to ensure dynamic stack limits are consistently queried across all container actions and to prevent method resolution bypasses caused by other mods.

## [1.4.3+26.2] - 2026-07-07

### Added

- Added configurable **Max Drop Entities** GameRule and YACL config option. Controls how many item entities spawn per inventory slot when a container breaks (range 1–64, default 8). Vanilla has no cap and can spawn thousands of entities for large stacks.

## [1.4.2+26.2] - 2026-07-07

### Fixed

- Fixed a dependency registration typo in `fabric.mod.json` where the required mod ID was specified as `item-clumps` (hyphenated) instead of the actual ID `item_clumps` (underscored), which caused startup load failures.

## [1.4.1+26.2] - 2026-07-07 (skip)

### Changed

- Bumped required **Item Clumps** version to `>=1.0.18+26.2` to support dynamic ground merge limit alignment and the removal of the redundant config/GameRule settings.

## [1.4.0+26.2] - 2026-07-07

### Added

- Added **Item Clumps** mod as a required dependency to handle dynamic ground item entity compression, preventing severe server and client lag when players die or containers are broken with massive stack sizes.

## [1.3.6+26.2] - 2026-07-07

### Fixed

- Fixed a major game/server crash when containers (chests, barrels, shulker boxes) containing extremely large stack sizes (millions/billions) are broken or destroyed, by dynamically scaling entity split sizes to cap maximum spawned item entities per slot.

## [1.3.5+26.2] - 2026-07-06

### Changed

- Specified player inventory immunity to container overflow.
- Added 'Change it at your own risk!' warning disclaimer to all tooltips, configs, and descriptions.

## [1.3.4+26.2] - 2026-07-06

### Changed

- Added moving/sorting lag warning.
- Added safe per-slot capacity safety calculation math formula for modded backpacks/containers.

## [1.3.3+26.2] - 2026-07-06

### Changed

- Adjusted warning threshold to `39,768,215` (Double Chest safe limit).

## [1.3.2+26.2] - 2026-07-06

### Changed

- Doubled baseline stack defaults: items naturally stacking to 64 now default to **`128`**, and items naturally stacking to 16 now default to **`32`**. Unstackable items remain at a default of `1`.

## [1.3.1+26.2] - 2026-07-06

### Changed

- Changed configuration overflow warning threshold from 1,000,000,000 to **`79,536,431`**. This matches the exact maximum limit where fully filling a Shulker Box (27 slots) with this item will not exceed the 32-bit signed integer capacity and prevent item deletion.

## [1.3.0+26.2] - 2026-07-06

### Fixed

- Fixed player inventory and shulker box saving issues where items above count 99 were rejected or could disappear during world autosave. Added redirects to bypass the strict `[1;99]` validation ranges inside `DataComponents.max_stack_size` and `ItemStackTemplate` (used by bundles).

## [1.2.0+26.2] - 2026-07-06

### Added

- Added forced client-side inventory and container screen refresh when stack size limits change on the server, eliminating the need to relog.

## [1.1.0+26.2] - 2026-07-06

### Added

- Added dynamic tooltip warning in the YACL GUI that triggers when any stack size limit is set above 1,000,000,000 (1 billion). The tooltip dynamically questions: *"Do you really want to set the value to [number]?"* and warns of integer overflow risks, while still allowing the player to enter it if they choose.

## [1.0.6+26.2] - 2026-07-06

### Fixed

- Fixed stack count numbers overlapping in the inventory by dynamically scaling the text scale down for count values that exceed 2 digits (> 99).

## [1.0.5+26.2] - 2026-07-06

### Fixed

- Resolved the inventory slot cap of 99 by overriding default `getMaxStackSize()` inside `Container` interface and redirecting `ItemStack`'s count codec range validation from `1-99` to `1-Integer.MAX_VALUE`.

## [1.0.4+26.2] - 2026-07-06

### Changed

- Renamed setting labels in the GameRules and YACL screens to be short and precise: "64-Stack Limit", "16-Stack Limit", and "1-Stack Limit".

## [1.0.3+26.2] - 2026-07-06

### Changed

- Expanded maximum stack size limits configuration range from vanilla limits (64) to support raw integers up to `Integer.MAX_VALUE`. 
- Migrated YACL config screen fields from fixed Enum selectors to raw integer inputs.

## [1.0.2+26.2] - 2026-07-06

### Fixed

- Fixed collapsible headers display in the GameRules screen. Aligned the category translation key format with the exact `gamerule.category.<namespace>.<path>` syntax to restore bold formatting.

## [1.0.1+26.2] - 2026-07-06

### Fixed

- Resolved a startup crash caused by an invalid `@Overwrite` reference on `ItemStack.getMaxStackSize()`. Implemented interface-level mixin targeting `ItemInstance` to override stack limits safely.

## [1.0.0+26.2] - 2026-07-06 [CRASHED]

### Added

- Initial release of Stack Size Adjuster
- Three categories for stack size customization: 64-stack, 16-stack, and 1-stack items
- Independent namespaced GameRules: `stack-size-adjuster:items_64_limit`, `stack-size-adjuster:items_16_limit`, `stack-size-adjuster:items_1_limit`
- Optional YetAnotherConfigLib (YACL) and ModMenu screen integration
- Dynamic server-to-client stack limit packet sync
- Modded items support
