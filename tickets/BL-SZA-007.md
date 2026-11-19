# 🎫 [BL-SZA-007] Built-In Data-Driven Stew Stacking (Fold Stew Stacker Addon into Core)

- **Ticket ID**: `[BL-SZA-007]`
- **Category**: `[FEATURE]`
- **Priority**: `[HIGH]`
- **Scope / Target Version**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Date Added**: `2026-11-19`
- **Target Repository / Root**: `Instant Gratification Collection/Stack Size Adjuster`

---

## 🎯 1. Goal & Definition of Done (The 4-Pillar Objective Architecture)

1. **North Star Objective**: Integrate configurable, data-driven stew and soup stacking (`stew_limit`, default `16`) directly into **Stack Size Adjuster** across all 6 active Minecraft anchors (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`) so players get safe stew stacking out of the box without needing the retired standalone `Stew Stacker Addon`.
2. **In-Game Experience (Before vs. After)**:
   - *Before*: Stews and soups stay locked at `1` under default `items_1_limit = 1`. Players either raise `items_1_limit` globally (unintentionally making swords, pickaxes, armor, buckets, and beds stackable) or search for the retired standalone `stew-stacker-addon` mod.
   - *After*: Mushroom Stew, Rabbit Stew, Beetroot Soup, identical-effect Suspicious Stew, and modded soups tagged via `#c:foods/soup` automatically stack up to `16` (configurable via GameRule `stack-size-adjuster:stew_limit`, JSON config, and YACL settings) out of the box, returning a single empty bowl safely on consumption without losing the remaining stack.
3. **🚫 Anti-Goals (Off-Limits / Do Not Touch)**:
   - ❌ **Stews/Soups Only (No Other Unstackables)**: Do NOT fold potions, buckets, cakes, beds, tools, or armor into this ticket (`Potion Stacker Addon` remains completely untouched).
   - ❌ **Never Merge Different Suspicious Stew Effects**: Do NOT alter item equality, NBT, or DataComponent merging rules; Suspicious Stews with different potion effects MUST NOT stack together (only identical effects/components stack naturally).
   - ❌ **No Consumption/Recipe Gameplay Rebalancing**: Do NOT change hunger/saturation values, eating speed, cooldowns, or crafting/cauldron recipes — strictly stack size limit plus safe single-bowl return on eat.
4. **✅ Definition of Done (Binary Verification Checklist)**:
   - [ ] Data-driven item tag `#stack-size-adjuster:stews` (`data/stack-size-adjuster/tags/item/stews.json` on `1.21.1+`, `data/stack-size-adjuster/tags/items/stews.json` on `1.20.1`) ships with all 4 vanilla stews/soups and optional `#c:foods/soup` inclusion.
   - [ ] `stew_limit` (default `16`) takes priority over `items_1_limit` inside `StackSizeManager.getModifiedStackSize`, while preserving `#c:stack_size_exempt` and external `CUSTOM_OVERRIDES`/`OVERRIDES` precedence.
   - [ ] GameRule `stack-size-adjuster:stew_limit`, `StackSizeConfig.stewLimit`, YACL GUI controls (`v26.1.2`–`v26.3`), language keys (`en_us.json`), and S2C `StackSizeLimitSyncPayload` (guarded by `ServerPlayNetworking.canSend`) synchronize reliably across server and client.
   - [ ] Safe single-bowl return works on `1.20.1` (`BowlFoodItemMixin` + `SuspiciousStewItemMixin`) and is verified via Stage 1 decompiled source audit on `1.21.1` through `26.3` for both free-slot and full-inventory survival consumption.
   - [ ] Soft startup deprecation warning logs via `LOGGER.warn(...)` when `stew-stacker-addon` is co-installed (zero crash; no `"breaks"` entry added to `fabric.mod.json`).
   - [ ] 5-Pillar tests and `./gradlew test build --no-daemon --console=plain` pass across all 6 active anchors (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`).

---

## ❓ 2. Problem Statement & Player Impact

The standalone `Stew Stacker Addon` (`kwGC64eU`) was retired (`skip_publish: true`, `archived: true`, `discontinued: true`) after Modrinth withheld the project under Section 6.2b. As a result, players installing **Stack Size Adjuster** currently have no dedicated stew/soup stack setting: leaving `items_1_limit = 1` keeps stews unstackable, while raising `items_1_limit` also stacks tools, weapons, armor, and utility items.

Folding stew stacking directly into **Stack Size Adjuster** eliminates separate-addon friction for players and upgrades the legacy hardcoded 4-item check (`StewStackerManager.isStewOrSoup`) into a proper datapack-extensible item tag (`#stack-size-adjuster:stews`).

---

## 🔬 3. Architectural Blueprint & Target Classes

1. **Data-Driven Tag Definition (`STEWS_TAG`)**:
   - Resource file:
     - MC `1.21.1`–`26.3`: `src/main/resources/data/stack-size-adjuster/tags/item/stews.json`
     - MC `1.20.1`: `src/main/resources/data/stack-size-adjuster/tags/items/stews.json`
   - Tag contents:
     ```json
     {
       "replace": false,
       "values": [
         "minecraft:mushroom_stew",
         "minecraft:rabbit_stew",
         "minecraft:beetroot_soup",
         "minecraft:suspicious_stew",
         {
           "id": "#c:foods/soup",
           "required": false
         }
       ]
     }
     ```
   - Java tag key in `StackSizeManager`: `public static final TagKey<Item> STEWS_TAG = TagKey.create(Registries.ITEM, ...)` (`Identifier.fromNamespaceAndPath("stack-size-adjuster", "stews")` on `1.21.11`–`26.3`; `ResourceLocation.fromNamespaceAndPath(...)` on `1.21.1`; `new ResourceLocation("stack-size-adjuster", "stews")` on `1.20.1`).
2. **`StackSizeManager` (`net.instantgratification.stacksizeadjuster.util.StackSizeManager`)**:
   - State: `private static volatile int stewLimit = 16;` plus `public static int getStewLimit()`.
   - Overloads & Backward Compatibility:
     - Add 5-arg `setLimits(int l64, int l16, int l1, int stew, MinecraftServer server)` and 4-arg `setClientLimits(int l64, int l16, int l1, int stew)`.
     - Retain existing 4-arg `setLimits(int l64, int l16, int l1, MinecraftServer server)` (delegating with current `stewLimit`) and 3-arg `setClientLimits(int l64, int l16, int l1)` overloads so external addons (`Potion Stacker Addon`, older callers) preserve binary compatibility.
     - Update `setLimit(String path, int value, MinecraftServer server)` to handle `"stew_limit"`.
   - Precedence order inside `getModifiedStackSize(Item item, int original)`:
     0. `#c:stack_size_exempt` (`C_STACK_SIZE_EXEMPT`) where present (Stage 1: verify `v26.3` exemption parity with `v1.20.1`–`v26.2`).
     1. `CUSTOM_OVERRIDES` (returns if `>= 0`).
     2. `OVERRIDES` (returns if `>= 0 && != original`).
     3. Safe `STEWS_TAG` check (`isStewItem(Item item)` guarding `item != null` and unbound tag/registry states so headless tests and pre-tag bootstrap never throw) -> sets `result = stewLimit` before the `original == 1` (`limit1`) branch, ensuring `stew_limit` overrides `items_1_limit`.
     4. Standard `limit64` (`original >= 64`), `limit16` (`original >= 16`), `limit1` (`original == 1`) buckets.
     5. Fail-safe `<= 0` guard (`return original > 0 ? original : 1;`) to prevent `LootTable.createStackSplitter` infinite loops.
3. **`StackSizeConfig` (`net.instantgratification.stacksizeadjuster.config.StackSizeConfig`)**:
   - Add `public int stewLimit = 16;` alongside existing `items64Limit = 128`, `items16Limit = 32`, `items1Limit = 1`, `maxDropEntities = 8` (keep `VERSION = 1` / `ConfigHelper` sparse-safe defaults).
4. **`StackSizeAdjusterFabric` (`net.instantgratification.stacksizeadjuster.StackSizeAdjusterFabric`)**:
   - Register GameRule `STEW_LIMIT = DynamicGameRuleManager.integerRule(MOD_ID + ":stew_limit", CUSTOM_CATEGORY, StackSizeConfig.get().stewLimit).name("Stew Limit").description(...).range(1, Integer.MAX_VALUE).register();`.
   - Synchronize `stewLimit` on `SERVER_STARTED` (applying `StackSizeConfig.get().stewLimit` when `!server.getWorldData().overworldData().isInitialized()`), on live GameRule mutations, and on `ServerPlayConnectionEvents.JOIN`.
   - Soft Co-Install Deprecation Guard: during `onInitialize()`, if `FabricLoader.getInstance().isModLoaded("stew-stacker-addon")`, log a clear `LOGGER.warn(...)` informing server owners/players that stew stacking is now built into Stack Size Adjuster and `stew-stacker-addon` can be safely removed. Do NOT add `"breaks"` to `fabric.mod.json` so existing modpacks never crash on update.
5. **Networking (`StackSizeLimitSyncPayload` & `StackSizeAdjusterFabricClient`)**:
   - Extend `StackSizeLimitSyncPayload(int limit64, int limit16, int limit1, int stewLimit)` (`4` `ByteBufCodecs.VAR_INT` fields on `1.21.1`–`26.3`; `4` `writeVarInt`/`readVarInt` fields on `1.20.1` `FriendlyByteBuf`).
   - Update `StackSizeAdjusterFabricClient` packet handler to call `StackSizeManager.setClientLimits(..., payload.stewLimit())`.
   - Server-Side Vanilla-Client Guard: guard every `ServerPlayNetworking.send(player, ...)` and `JOIN` packet send with `ServerPlayNetworking.canSend(...)` so vanilla clients joining modded servers never trigger protocol disconnects.
6. **`YaclScreenHelper` & Localization (`en_us.json`)**:
   - On `v26.1.2`, `v26.2`, `v26.3` (`YaclScreenHelper.java`), add a `stew_limit` `IntegerFieldControllerBuilder` option (`min(1)`, `max(Integer.MAX_VALUE)`, default `16` bound to `config.stewLimit`) inside `group.categories`, reusing the `val > 39768215` overflow warning (`config.stacksizeadjuster.option.warning`). (`v1.20.1`–`v1.21.11` currently do not bundle `YaclScreenHelper.java`; keep their `ModMenuIntegration` reflection guard intact).
   - Add translation keys to `assets/stacksizeadjuster/lang/en_us.json` (plus `assets/stack-size-adjuster/lang/en_us.json` and `:stew_limit` colon fallback keys in `v1.20.1`–`v1.21.11`):
     - `"config.stacksizeadjuster.option.limit_stew": "Stew & Soup Limit"`
     - `"config.stacksizeadjuster.option.limit_stew.description"` (matching existing `option.limit_1.description` format and `§6Notice:§r` block)
     - `"gamerule.stack-size-adjuster.stew_limit": "Stew Limit"`
     - `"gamerule.stack-size-adjuster.stew_limit.description"`
7. **Safe Single-Bowl Return Mixins (`1.20.1` + Stage 1 `1.21.1` Verification)**:
   - Port `BowlFoodItemMixin` and `SuspiciousStewItemMixin` (originating from `Stew Stacker Addon v1.20.1`) into `Stack Size Adjuster v1.20.1` (`stack-size-adjuster.mixins.json`).
   - Fix legacy gaps from the standalone addon: inspect decompiled `BowlFoodItem.finishUsingItem` and `SuspiciousStewItem.finishUsingItem` in `1.20.1`, guard `!level.isClientSide`, ensure stack count decrements properly without returning a whole stack of bowls or duplicating bowls, and add/drop a single `Items.BOWL` safely when inventory is full.
   - **Mandatory Stage 1 Decompilation Check on `1.21.1`**: Before editing `Stack Size Adjuster v1.21.1`, inspect decompiled `BowlFoodItem`, `SuspiciousStewItem`, `Item.finishUsingItem`, and `FoodProperties.usingConvertsTo` (`1.21.1` predates `1.21.2`'s `UseRemainder` component) to verify whether `1.21.1` safely handles stacked stew consumption or requires the same Mixins.
8. **Companion Ticket Boundaries**:
   - Keep this ticket strictly scoped to existing GameRule, `StackSizeConfig`, and `YaclScreenHelper` surfaces without coupling to unimplemented refactors in [`BL-SZA-002`](./BL-SZA-002.md) (Brigadier Command Suite) or [`BL-SZA-003`](./BL-SZA-003.md) (UniversalConfigScreen `@ConfigOption`).

---

## ⚓ 4. Multi-Era Anchor Parity Specifications

| Anchor Subproject | MC Pin | Java / Loom | Tag Path | Resource ID & Payload | GameRule Sync Mechanism | Bowl Return Mechanism |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| `Stack Size Adjuster v26.3` | `>=26.3-` | Java 25 / Loom `1.15-SNAPSHOT` | `tags/item/stews.json` | `Identifier` + `StreamCodec` (4 `VAR_INT`) | `MinecraftServerMixin` (`onGameRuleChanged`) + `StackSizeManager.setLimits` | Verify `UseRemainder` component in Stage 1 |
| `Stack Size Adjuster v26.2` | `>=26.2-` | Java 25 / Loom `1.15-SNAPSHOT` | `tags/item/stews.json` | `Identifier` + `StreamCodec` (4 `VAR_INT`) | `MinecraftServerMixin` (`onGameRuleChanged`) + `StackSizeManager.setLimits` | Verify `UseRemainder` component in Stage 1 |
| `Stack Size Adjuster v26.1.2` | `>=26.1.2- <26.2-` | Java 25 / Loom `1.15-SNAPSHOT` | `tags/item/stews.json` | `Identifier` + `StreamCodec` (4 `VAR_INT`) | `MinecraftServerMixin` (`onGameRuleChanged`) + `StackSizeManager.setLimits` | Verify `UseRemainder` component in Stage 1 |
| `Stack Size Adjuster v1.21.11` | `~1.21.11` | Java 21 / `fabric-loom-remap` `1.15-SNAPSHOT` | `tags/item/stews.json` | `Identifier` + `StreamCodec` (4 `VAR_INT`) | `GameRuleEvents.changeCallback` + `broadcastSync` | Verify `UseRemainder` component in Stage 1 |
| `Stack Size Adjuster v1.21.1` | `~1.21.1` | Java 21 / `fabric-loom` `1.10.2` | `tags/item/stews.json` | `ResourceLocation` + `StreamCodec` (4 `VAR_INT`) | `ServerTickEvents.END_SERVER_TICK` polling + `broadcastSync` | Stage 1 decompile check (`usingConvertsTo` vs Mixin) |
| `Stack Size Adjuster v1.20.1` | `~1.20.1` | Java 17 / `fabric-loom` `1.10.2` | `tags/items/stews.json` | `ResourceLocation` + `FriendlyByteBuf` (4 `VarInt`) | `ServerTickEvents.END_SERVER_TICK` polling + `syncLimits` | `BowlFoodItemMixin` + `SuspiciousStewItemMixin` |

- **Stage 1 Root Directory Check**: Inspect whether the root `src/` and root `gradle.properties` (`minecraft_version=26.2`, `mod_version=1.4.18+26.2`) are an unused legacy mirror of `Stack Size Adjuster v26.2/` (`mod_version=1.4.19+26.2`) before modifying root files.
- **Anchor 7 Alignment**: If [`BL-SZA-005`](./BL-SZA-005.md) (`Stack Size Adjuster v26.x/`) is scaffolded before or alongside this ticket, mirror every `v26.3` change to `v26.x` in lockstep.

---

## 🛡️ 5. Edge Cases, Collisions & Failure Modes

1. **Survival Consume (Free Slot Available)**: Eating 1 stew from a 16-stack leaves `15` stews in hand and adds `1` empty `minecraft:bowl` to a free inventory slot.
2. **Survival Consume (Full Inventory)**: Eating 1 stew from a 16-stack when all inventory slots are full leaves `15` stews in hand and drops `1` empty `minecraft:bowl` cleanly at the player's feet (`!player.getInventory().add(bowl) -> player.drop(bowl, false)`), strictly server-side (`!level.isClientSide`).
3. **Eating the Last Stew (`count == 1`)**: Consuming a single stew returns `1` empty bowl directly in hand without duplicating or deleting the bowl.
4. **Creative Mode (`instabuild`)**: Eating a stew in Creative neither consumes from the stack nor spawns duplicate empty bowls.
5. **Different Suspicious Stew Effects / Components**: Two Suspicious Stews carrying distinct effect lists or DataComponents/NBT never stack together; only identical stews stack up to `stewLimit`.
6. **Early Bootstrap / Unbound Tag Lookup in Headless Tests**: Checking `item.builtInRegistryHolder().is(STEWS_TAG)` before datapack tags bind (or inside unit tests without a loaded server) must safely short-circuit without throwing `IllegalStateException` or `NullPointerException`.
7. **Extreme & Invalid Values (`stew_limit <= 0` or `Integer.MAX_VALUE`)**: Values `<= 0` fall back via the fail-safe guard (`original > 0 ? original : 1`), while high values up to `Integer.MAX_VALUE` remain uncapped per the Freedom Over Anti-Crash policy with non-blocking warnings above `39,768,215`.
8. **Legacy `stew-stacker-addon` Co-Installed**: Co-installing the retired addon logs a clear startup warning without crashing; `CUSTOM_OVERRIDES` API stays intact so external addons (including `Potion Stacker Addon`) continue working normally.
9. **Vanilla Client Joining Modded Server**: Server-side `ServerPlayNetworking.canSend(...)` prevents `StackSizeLimitSyncPayload` / `SYNC_LIMIT_ID` sends from disconnecting vanilla or unmodded clients during login or live GameRule updates.

---

## 🚀 6. Implementation Steps (Code-Level Plan)

1. **Stage 1 Code & Decompilation Audit**:
   - Inspect live `StackSizeManager`, `StackSizeConfig`, `StackSizeAdjusterFabric`, `StackSizeLimitSyncPayload`, `YaclScreenHelper`, and `en_us.json` across all 6 subprojects (plus check whether root `src/` is deprecated/unused and whether `v26.3` should match `C_STACK_SIZE_EXEMPT`).
   - Decompile/inspect `BowlFoodItem`, `SuspiciousStewItem`, `Item.finishUsingItem`, and `FoodProperties.usingConvertsTo` in `1.21.1` (and verify `UseRemainder` handling in `1.21.11`–`26.3` + `1.20.1` `finishUsingItem`).
2. **Data-Driven Tag Resources**:
   - Create `data/stack-size-adjuster/tags/item/stews.json` in `v1.21.1`–`v26.3` and `data/stack-size-adjuster/tags/items/stews.json` in `v1.20.1`.
3. **Config & Core Manager**:
   - Add `stewLimit = 16` to `StackSizeConfig`.
   - Add `STEWS_TAG`, `stewLimit`, `getStewLimit()`, backward-compatible `setLimits` / `setClientLimits` overloads, `setLimit("stew_limit", ...)`, and safe tag lookup in `StackSizeManager`.
4. **Networking & Vanilla-Client Safety**:
   - Extend `StackSizeLimitSyncPayload` (4 `VAR_INT`s) on `1.21.1`–`26.3` and `SYNC_LIMIT_ID` `FriendlyByteBuf` on `1.20.1`.
   - Update `StackSizeAdjusterFabricClient` receiver and guard server sends with `ServerPlayNetworking.canSend(...)`.
5. **Initializer, GameRule & Deprecation Warning**:
   - Register `stack-size-adjuster:stew_limit` in `StackSizeAdjusterFabric`, wire initial world defaults + live GameRule change sync, and emit the soft `LOGGER.warn(...)` when `stew-stacker-addon` is loaded.
6. **GUI & Language Assets**:
   - Update `YaclScreenHelper.java` in `v26.1.2`, `v26.2`, `v26.3` with `stew_limit` + `> 39768215` overflow warning.
   - Update all `en_us.json` files (`assets/stacksizeadjuster/lang/en_us.json` across all 6 anchors + `assets/stack-size-adjuster/lang/en_us.json` in `v1.20.1`–`v1.21.11`).
7. **Legacy Bowl-Return Mixins**:
   - Port `BowlFoodItemMixin` and `SuspiciousStewItemMixin` into `v1.20.1` (and `v1.21.1` if Stage 1 decompile audit shows stack-loss or missing remainder behavior), fixing `!level.isClientSide` and full-inventory drop edge cases.
8. **5-Pillar Verification & Cross-Anchor Rollout**:
   - Author/extend tests (`StackSizeManagerTest` + 5-Pillar tests; note [`BL-SZA-001`](./BL-SZA-001.md) for `26.x` test harness baseline), execute `./gradlew test build --no-daemon --console=plain` cleanly across `v26.3 -> v26.2 -> v26.1.2 -> v1.21.11 -> v1.21.1 -> v1.20.1`, then perform a 1-version bump and 4-point release registration per anchor.

---

## 🏁 7. 5-Pillar Verification Checklist

- [ ] **Pillar 1 (Chaos Property Fuzzing)**: Fuzz `stewLimit` across `1..Integer.MAX_VALUE` plus negative/zero boundary inputs (`<= 0` fail-safe fallback) with 10,000+ iterations without overflow or crash.
- [ ] **Pillar 2 (Concurrency Load)**: Stress 50 concurrent virtual/worker threads racing `StackSizeManager.setLimits(...)` and `getModifiedStackSize(...)` on stew and non-stew items with zero race conditions or uncaught exceptions.
- [ ] **Pillar 3 (GameRule / Config / Translation Sync)**: Verify `stew_limit` GameRule defaults, `StackSizeConfig.stewLimit` round-trip, `YaclScreenHelper` option bindings (`v26.1.2`–`v26.3`), and matching `en_us.json` translation tokens across all anchors.
- [ ] **Pillar 4 (Behavioral GameTest & Negative Parity)**:
  - Verify all 4 vanilla stews (`mushroom_stew`, `rabbit_stew`, `beetroot_soup`, `suspicious_stew`) and `#c:foods/soup` stack to `16` (and obey custom `stew_limit`).
  - Verify survival consume from a 16-stack returns 1 bowl to free slot or drops 1 bowl on full inventory, `count == 1` returns 1 bowl cleanly, and creative `instabuild` does not consume or duplicate bowls.
  - **Negative Parity**: Verify Suspicious Stews with different potion effects NEVER merge, and non-stew unstackables (potions, swords, buckets, armor) remain governed by `items_1_limit = 1`.
- [ ] **Pillar 5 (Sidedness Contract, GPLv3 & Vanilla Join Safety)**: Assert zero `net.minecraft.client.*` imports in common code, valid `// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3` headers on modified `.java` files, and `ServerPlayNetworking.canSend` guards preventing vanilla-client disconnects.
- [ ] **Multi-Anchor Compilation (`./gradlew test build --no-daemon --console=plain`)**:
  - [ ] `Stack Size Adjuster v26.3` green
  - [ ] `Stack Size Adjuster v26.2` green
  - [ ] `Stack Size Adjuster v26.1.2` green
  - [ ] `Stack Size Adjuster v1.21.11` green
  - [ ] `Stack Size Adjuster v1.21.1` green
  - [ ] `Stack Size Adjuster v1.20.1` green

---

## 🛡️ 8. Autonomous Goal Dependability & Self-Healing Contract

1. **Toolchain Authority**: Always treat each subproject's on-disk `build.gradle`, `gradle.properties`, and `fabric.mod.json` (`Java 25` / Loom `1.15-SNAPSHOT` on `26.x`, `Java 21` on `1.21.1`/`1.21.11`, `Java 17` on `1.20.1`) as ground truth rather than cached memory.
2. **Gradle Execution Discipline**: Run Gradle builds/tests with `--no-daemon --console=plain` and warn the user in one line before any command expected to exceed 10 seconds.
3. **1-Attempt Self-Healing Boundary**: If any anchor fails compilation or tests due to a mapping/API difference, perform at most one targeted decompiled-source verification + self-heal edit against that anchor's JARs; if blocked by an upstream API conflict or missing dependency, stop and escalate cleanly rather than weakening assertions.
4. **Continuation & Clean State**: Complete one anchor cleanly before advancing to the next (`26.3 -> 26.2 -> 26.1.2 -> 1.21.11 -> 1.21.1 -> 1.20.1`), keeping pre-existing `RELEASE_QUEUE.md` state isolated and untouched unless explicitly scheduling the release entry.
