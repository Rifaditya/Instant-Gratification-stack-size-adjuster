# 📋 Stack Size Adjuster Release Queue & Backlog

This file tracks which built versions (from the central archive folder "E:\Minecraft Project\Instant Gratification Collection\Stack Size Adjuster\Archive Jar of all versions") have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [ ] **`1.4.14+26.2`** (2026-07-14) - - Fix Give Command Integer Overflow. - - Fixed integer overflow in /give command's max allowed count by using long calculations and safe clamping to Integer.MAX_VALUE.
- [ ] **`1.4.12+26.2`** (2026-07-11) - - Fix Launch Crash. - - Fixed launch crash due to InvalidInjectionException on ItemStackMixin by reverting getMaxStackSize inject to direct method override.
- [ ] **`1.4.11+26.2`** (2026-07-11) - - Remove Live Config Sync. - - Removed live GameRule syncing from YACL config save block so config changes only define default values for newly created worlds.
- [ ] **`1.4.10+26.2`** (2026-07-11) - - Addon Override API & Mixin Safety. - - Added dynamic BiFunction override API to StackSizeManager and refactored mixins to @Inject to support conflict-free addon installation.
- [ ] **`1.4.9+26.2`** (2026-07-11) - - Sync Config to New World GameRules. - - Synchronize global YACL config settings to world GameRules on startup for newly created worlds. Added config tooltips warning about per-world isolation.
- [ ] **`1.4.8+26.2`** (2026-07-11) - - Revert Potion Configuration. - - Completely reverted potion stacking settings, GameRules, mixins, and syncing logic, deferring them to the future addon mod.
- [ ] **`1.4.7+26.2`** (2026-07-08) - - [DEPRECATED / SUPERSEDED] Dedicated Potion Limit. - - Added dedicated Potion Limit. Superseded by 1.4.8+26.2.
- [ ] **`1.4.6+26.2`** (2026-07-08) - - [DEPRECATED / SUPERSEDED] Fix Dynamic Limits. - - Fixed category limit mapping in StackSizeManager. Superseded by 1.4.7+26.2.
- [ ] **`1.4.5+26.2`** (2026-07-08) - - Fix Drag-Splitting Float Precision Loss. - - Override getQuickCraftPlaceCount() using double precision division in AbstractContainerMenu to fix item duplication/leftovers when drag-splitting and consolidating massive stack sizes near 2.14B.
- [ ] **`1.4.4+26.2`** (2026-07-08) - - Fix Double-Click Consolidation Duplication. - - Override getMaxStackSize() directly in ItemStack to resolve slot desync duplication when gathering/sorting spread stacks.
- [ ] **`1.4.3+26.2`** (2026-07-07) - - Configurable Max Drop Entities. - - Added Max Drop Entities GameRule and YACL config slider (1–64, default 8) to control how many item entities spawn per slot when a container breaks.
- [ ] **`1.4.2+26.2`** (2026-07-07) - - Fixed dependency ID typo. - - Fixed dependency ID typo in fabric.mod.json from item-clumps to item_clumps.
- [ ] **`1.4.1+26.2`** (2026-07-07) - - Dynamic Ground Merge Sync. - - Bumped required Item Clumps dependency version to 1.0.18+26.2 to support dynamic ground merge limit alignment and the removal of the redundant config/GameRule settings.
- [ ] **`1.4.0+26.2`** (2026-07-07) - - Required dependency on Item Clumps. - - Made Item Clumps a required dependency to handle dynamic ground item entity compression, resolving lag and crashes on player death or chest destruction.
- [ ] **`1.3.6+26.2`** (2026-07-07) - - Chest break crash fix. - - Fixed chest destruction crash by dynamically scaling item entity split sizes to cap maximum spawned item entities per slot.
- [ ] **`1.3.5+26.2`** (2026-07-06) - - Player inventory immunity and warning disclaimer. - - Specified player inventory immunity, and added 'Change it at your own risk!' warning disclaimer to all tooltips and docs.
- [ ] **`1.3.4+26.2`** (2026-07-06) - - Moving lag warning & backpack safety formula. - - Added moving lag warnings and safety slot formulas for modded backpacks.
- [ ] **`1.3.3+26.2`** (2026-07-06) - - Double Chest safe threshold. - - Adjusted warning threshold to 39,768,215 (Double Chest limit).
- [ ] **`1.3.2+26.2`** (2026-07-06) - - Doubled default stacks. - - Doubled standard stack defaults (64 limit to 128, 16 limit to 32) while keeping 1-stack items at 1.
- [ ] **`1.3.1+26.2`** (2026-07-06) - - Shulker Box Warning Threshold. - - Set warning limit to 79,536,431 to prevent container-wide overflow when filling a Shulker Box.
- [ ] **`1.3.0+26.2`** (2026-07-06) - - Serialization range fix. - - Lifted serialization range checks from DataComponents (for shulker box contents) and ItemStackTemplate (for bundles) to allow saving items above 99.
- [ ] **`1.2.0+26.2`** (2026-07-06) - - Live client refresh. - - Force client container and inventory update when server game rules change, preventing relog requirements.
- [ ] **`1.1.0+26.2`** (2026-07-06) - - Added overflow warnings. - - Appended dynamic tooltip warnings recommending a max limit of 1 billion to prevent integer overflows.
- [ ] **`1.0.6+26.2`** (2026-07-06) - - Overlapping text fix. - - Dynamically scale stack count text scale down for items stacking above 99.
- [ ] **`1.0.5+26.2`** (2026-07-06) - - Over-99 stack sizes. - - Fixed inventory slot cap at 99. Overrode Container.getMaxStackSize() and updated ItemStack count codec limits.
- [ ] **`1.0.4+26.2`** (2026-07-06) - - Renamed settings labels. - - Updated labels to "64-Stack Limit", "16-Stack Limit", and "1-Stack Limit".
- [ ] **`1.0.3+26.2`** (2026-07-06) - - Range expansion. - - Changed stack limits to allow range 1 to Integer.MAX_VALUE and changed YACL screen to use integer text entry.
- [ ] **`1.0.2+26.2`** (2026-07-06) - - UI fix. - - Fixed category name not being bolded in GameRules list.
- [ ] **`1.0.1+26.2`** (2026-07-06) - - Crash fix. - - Resolved startup crash due to InvalidMixinException on ItemStack.getMaxStackSize. Override is now applied at interface-level.
- [ ] **`1.0.0+26.2`** (2026-07-06) - - Initial release. - - Three categories for stack size customization: 64-stack, 16-stack, and 1-stack items. - - Independent namespaced GameRules: `stack-size-adjuster:items_64_limit`, `stack-size-adjuster:items_16_limit`, `stack-size-adjuster:items_1_limit`. - - Optional YACL and ModMenu integration.
