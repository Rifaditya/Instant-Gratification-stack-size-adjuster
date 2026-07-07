# Changelog

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
