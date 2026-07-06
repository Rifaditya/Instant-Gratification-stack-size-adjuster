# 📋 Stack Size Adjuster Release Queue & Backlog

This file tracks which built versions (from the central archive folder "E:\Minecraft Project\Instant Gratification Collection\Stack Size Adjuster\Archive Jar of all versions") have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [ ] **`1.2.0+26.2`** (2026-07-06) - - Live client refresh. - - Force client container and inventory update when server game rules change, preventing relog requirements.
- [ ] **`1.1.0+26.2`** (2026-07-06) - - Added overflow warnings. - - Appended dynamic tooltip warnings recommending a max limit of 1 billion to prevent integer overflows.
- [ ] **`1.0.6+26.2`** (2026-07-06) - - Overlapping text fix. - - Dynamically scale stack count text scale down for items stacking above 99.
- [ ] **`1.0.5+26.2`** (2026-07-06) - - Over-99 stack sizes. - - Fixed inventory slot cap at 99. Overrode Container.getMaxStackSize() and updated ItemStack count codec limits.
- [ ] **`1.0.4+26.2`** (2026-07-06) - - Renamed settings labels. - - Updated labels to "64-Stack Limit", "16-Stack Limit", and "1-Stack Limit".
- [ ] **`1.0.3+26.2`** (2026-07-06) - - Range expansion. - - Changed stack limits to allow range 1 to Integer.MAX_VALUE and changed YACL screen to use integer text entry.
- [ ] **`1.0.2+26.2`** (2026-07-06) - - UI fix. - - Fixed category name not being bolded in GameRules list.
- [ ] **`1.0.1+26.2`** (2026-07-06) - - Crash fix. - - Resolved startup crash due to InvalidMixinException on ItemStack.getMaxStackSize. Override is now applied at interface-level.
- [ ] **`1.0.0+26.2`** (2026-07-06) - - Initial release. - - Three categories for stack size customization: 64-stack, 16-stack, and 1-stack items. - - Independent namespaced GameRules: `stack-size-adjuster:items_64_limit`, `stack-size-adjuster:items_16_limit`, `stack-size-adjuster:items_1_limit`. - - Optional YACL and ModMenu integration.
