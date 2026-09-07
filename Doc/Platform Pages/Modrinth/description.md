<p align="center">
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://modrinth.com/mod/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-8A2BE2?style=for-the-badge" alt="Requires Dasik Library"></a>
  <a href="https://modrinth.com/mod/item_clumps"><img src="https://img.shields.io/badge/Requires-Item_Clumps-orange?style=for-the-badge" alt="Requires Item Clumps"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 📦 Stack Size Adjuster

> **"Take Control of Your Inventory. Custom Limits, No Boundaries."**

> [!NOTE]
> **1 Jar 1 Version Policy:** I build **1 dedicated JAR for each Minecraft version** (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.

Tired of inventory clutter? Or do you want to create a harder, more restricted survival experience? **Stack Size Adjuster** grants you full authority over item stack limits. Scale weapons, armor, blocks, and consumables dynamically at runtime with zero mixin conflicts and full container safety.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/4bcbd52a43c30f910c522782ceb45175e023d9c0.webp" alt="Chest and Nested Shulker Box Storage Stacks" width="85%"><br>
  <em>Hold massive item stacks (from 64 up to 2.14 Billion) across chests, inventories, and nested Shulker Boxes with dynamic font scaling</em>
</p>

### 🎚️ Category-Based Customization
Configure limits dynamically across the three standard Minecraft item categories:
- **64-Stack Limit**: Standard stackable items (blocks, raw food, ores, building materials). Default: `128`.
- **16-Stack Limit**: Semi-stackable items (ender pearls, snowballs, eggs, signs). Default: `32`.
- **1-Stack Limit**: Unstackable items (weapons, tools, armor, potions, stews, beds). Default: `1`.

### 🔢 Raw Integer Limits & Dynamic Range
No more artificial caps! Adjust limits dynamically to any integer value from `1` up to `2,147,483,647` (`Integer.MAX_VALUE`).
- **Dynamic Warning Tooltip**: The configuration GUI features a dynamic warning tooltip that alerts you if a limit is set above `39,768,215`. This threshold prevents total-container overflows when completely filling containers with identical items.
- **Flat vs. Nested Storage Safety**:
  - **Player Inventory (Fully Immune)**: Player inventories are saved flatly as direct separate tags inside `.dat` files without slot summing. You can safely fill all 36 player slots up to `2,147,483,647` with identical items!
  - **Chests, Barrels & Shulker Boxes (Vulnerable to Overflow)**: World containers sum up total contents during tooltips and saving. Keep individual slot counts within the safe capacity thresholds to prevent 32-bit signed integer overflow deletion.

### 🔓 Slot Capacity Bypass
Say goodbye to the standard slot stack limit of 99 items. Stack Size Adjuster overrides default container and stack serializer limits, allowing you to hold massive stacks (e.g., `640`, `1,000`, or `100,000`) in any chest, hopper, or inventory slot.

### 📐 Dynamic Font Scale-Down
Worried about large numbers overflowing slot boxes? When stack counts exceed 2 digits (> 99), the client-side renderer dynamically scales down the count numbers to fit perfectly within slot boundaries, ensuring text never overlaps.

### 🛡️ Container Destruction Protection & Item Clumps
Includes the **Max Drop Entities** GameRule (`stack-size-adjuster:max_drop_entities`, default: `8`) and integrates natively with **Item Clumps** to compress dropped item entities when large containers break or players die, preventing severe server lag and client FPS drops.

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/f21e494bf04044a72d24e7d8f073bf4f79c14427.webp" alt="Zero-Lag Container Destruction with Item Clumps" width="85%"><br>
  <em>Breaking containers with millions of items merges drops into single clean 3D text entities, locking framerates at a smooth 60 FPS</em>
</p>

### 🎯 Drag-Splitting & Consolidation Precision
Overrides container drag-splitting with double-precision math during QUICK_CRAFT operations and `ItemStack` consolidation, preventing item duplication or leftovers when distributing massive stacks.

### 🛠️ Give Command Integer Overflow Safeguard
Re-routes `/give` command calculations with `long` math and safe clamping to `Integer.MAX_VALUE`, ensuring players can safely `/give` items even with extreme stack limits active.

### 🔌 Programmatic Addon Override API
Exposes `StackSizeManager.registerOverride(...)` and `@FunctionalInterface CustomStackSizeOverride`, allowing companion addon mods (such as Potion Stacker Addon or Stew Stacker Addon) to programmatically register custom item stack limits without colliding with general category rules.

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/a70a6c3f1cdd7492b8bc2228d51eb939d852ca9f.webp" alt="Addons and Semi-Stackable Items Showcase" width="85%"><br>
  <em>Stack 16-limit items to billions, and stack soups, stews, and potions (x16) via companion Addons</em>
</p>

### 🔒 Forward Compatibility & Version Guard
Pre-configured with `"minecraft": ">=26.2-"` open-ended bounds and zero-dependency `ModVersionGuard` Knot ClassLoader startup protection to display human-readable guidance if an incompatible Minecraft API version is encountered.

### 🧩 Compatibility & HUD Integration
- **Item Clumps**: Full native integration for ground item compression.
- **YetAnotherConfigLib (YACL) & ModMenu**: Optional clean in-game GUI configuration screen.
- **Server-Side Vanilla Client Support**: Synchronizes limits to vanilla clients via standard networking packets.

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/5821fb6d63cdb776536a3fe955ab0539a34dbe91.webp" alt="Jade WTHIT Tooltip and Hotbar Capacity" width="85%"><br>
  <em>Full Jade / WTHIT tooltip HUD support displaying real-time massive container stack counts</em>
</p>

---

## 📊 Quick Reference & Mechanics Matrix

| Item Category / Storage Type | Vanilla Limit | SSA Default | Maximum Safe Limit | Key Behavior & Safety Notes |
| :--- | :---: | :---: | :---: | :--- |
| **64-Stack Items** | 64 | **128** | `2,147,483,647` | Blocks, ores, crops, materials. Scale up for bulk storage. |
| **16-Stack Items** | 16 | **32** | `2,147,483,647` | Ender pearls, snowballs, eggs, buckets. |
| **1-Stack Items** | 1 | **1** | `2,147,483,647` | Tools, weapons, armor, beds. Keeps vanilla balance by default. |
| **Player Inventory (36 slots)** | 64 | — | **`2,147,483,647`** | **Fully Immune**: Flat NBT serialization prevents slot count summing. |
| **Hopper (5 slots)** | 64 | — | **`429,496,729`** | Safe per-slot limit to prevent hopper sum overflow. |
| **Dispenser / Dropper (9 slots)** | 64 | — | **`238,609,294`** | Safe per-slot limit when filled with identical items. |
| **Single Chest / Shulker Box (27 slots)** | 64 | — | **`79,536,431`** | Safe per-slot limit to avoid Shulker Box NBT overflow. |
| **Double Chest (54 slots)** | 64 | — | **`39,768,215`** | Safe per-slot limit when storing 54 identical item slots. |
| **Custom / Modded Backpack (N slots)** | — | — | `2,147,483,647 / N` | Apply formula: `Safe Limit = 2,147,483,647 / [Total Slots]`. |

---

## 🚀 In-Game Commands & Quick Start

Stack sizes can be adjusted live in-game without restarting using standard Minecraft `/gamerule` commands:

```text
/gamerule stack-size-adjuster:items_64_limit <value>
/gamerule stack-size-adjuster:items_16_limit <value>
/gamerule stack-size-adjuster:items_1_limit <value>
/gamerule stack-size-adjuster:max_drop_entities <value>
```

Changes made via `/gamerule` immediately sync to all connected clients and refresh active container screens.

---

## ⚙️ Configuration (Native GameRules)

> [!IMPORTANT]
> **💡 Config vs. In-Game GameRules:** The global configuration file (`config/stack-size-adjuster.json`) only defines default values for newly created worlds. In existing worlds, change settings in-game via the **Edit Game Rules** UI screen or the `/gamerule` command.

| GameRule Name | Type | Default | Valid Range | Description |
| :--- | :---: | :---: | :---: | :--- |
| `stack-size-adjuster:items_64_limit` | `Integer` | `128` | `1` to `2,147,483,647` | Maximum stack size for standard 64-stack items. |
| `stack-size-adjuster:items_16_limit` | `Integer` | `32` | `1` to `2,147,483,647` | Maximum stack size for semi-stackable 16-stack items. |
| `stack-size-adjuster:items_1_limit` | `Integer` | `1` | `1` to `2,147,483,647` | Maximum stack size for unstackable 1-stack items. |
| `stack-size-adjuster:max_drop_entities` | `Integer` | `8` | `1` to `64` | Maximum item entities spawned per slot on container break. |

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/9906f4d23bb875a352c08c664b7458e2884805d9.webp" alt="Native Minecraft Edit Game Rules Menu" width="85%"><br>
  <em>Native in-game Edit Game Rules menu with bold Stack Size Adjuster (4 rules) category</em><br><br>
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/9cbe3d5cddd620840b24eb669c0a0d5cb15f3c5f.webp" alt="YetAnotherConfigLib YACL GUI Screen" width="85%"><br>
  <em>Optional YetAnotherConfigLib (YACL) GUI configuration screen with safe limit presets & detailed tooltips</em>
</p>

---

## 📖 In-Depth How-To & Operational Playbook

### 1. Drop-In Setup & Baseline Initialization
1. Drop `stack-size-adjuster-<version>.jar` along with **Fabric API**, **Dasik Library**, and **Item Clumps** into your `mods` folder.
2. On first launch, the mod auto-generates `config/stack-size-adjuster.json` with recommended baseline defaults (`128` for 64-stack items, `32` for 16-stack items, `1` for 1-stack items, and `8` for max drop entities).

### 2. Live In-Game Tuning vs. Global Template
- **For New Worlds**: Modify values in `config/stack-size-adjuster.json` or through the ModMenu / YACL GUI before creating a world. The game copies these baseline defaults into the world's GameRules on initialization.
- **For Existing Worlds**: Use `/gamerule stack-size-adjuster:<rule> <value>` or the **Edit Game Rules** menu. The mod will instantly push updated stack boundaries to all clients and force-refresh open inventories.

### 3. Container Safety Math & Calculations
- When configuring stack limits in the millions or billions, use the **Safe Container Capacity Formula**:
  $$\text{Safe Per-Slot Limit} = \left\lfloor \frac{2,147,483,647}{\text{Total Container Slots}} \right\rfloor$$
- **Different Items**: Storing different item types in the same container is 100% safe up to `2,147,483,647` per slot because Minecraft tracks distinct items independently in NBT.
- **Identical Items**: Keep identical items below the container threshold (e.g. `39,768,215` for Double Chests, `79,536,431` for Shulker Boxes) to prevent total-count integer wrapping.

### 4. Companion Addon Integration
- Stack Size Adjuster includes an Addon Override API. Dedicated addon mods (such as *Potion Stacker Addon*) seamlessly register specific overrides for targeted items (e.g., allowing Potions to stack to `16` while keeping Weapons/Armor at `1`).

### 5. Troubleshooting & Crash Prevention
- **Loot Table Protection**: Includes automated safeguards against negative stack sizes (`<= 0`) during mob drops and egg laying.
- **Give Command Safety**: Safe `long` calculations ensure large `/give` commands never overflow into negative quantities.

---

## 🧩 Recommended Sister Mods

If you enjoy **Stack Size Adjuster**, these companion mods from the **Instant Gratification Collection** plug in seamlessly:

* 📦 [**Item Clumps**](https://modrinth.com/mod/ig-item-clumps): Automatically merges loose ground items into single high-density stacks with floating count labels, preventing entity lag when bursting massive inventories.
* 🧲 [**Magnet (Let Me Get That!)**](https://modrinth.com/mod/instant-gratification-magnet,-let-me-get-that!): Automatically vacuums loose item stacks and XP orbs directly toward your player within a configurable radius, effortlessly filling high-capacity storage.
* 🧪 [**Potion Stacker Addon**](https://modrinth.com/mod/ig-potion-stacker-addon): Seamlessly overrides potion limits to stack regular, splash, and lingering potions up to 16 or 64 per slot with safe empty bottle returns.

> 🌟 *Explore the full [**Instant Gratification Collection**](https://modrinth.com/collection/instant-gratification) for more high-convenience enhancements.*

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider supporting development!

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

> [!NOTE]
> **🇮🇩 Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!


> [!TIP]
> **Dedicated Server Hosting Partner:**
> Looking for a reliable server to play with friends? Check out **BisectHosting** for 1-click modpack installations, automated backups, and 24/7 dedicated customer support.
---

## 📜 Credits & Modpack Permissions

| Property | Information |
| :--- | :--- |
| **Creator / Author** | **Dasik** (Rifaditya) |
| **Collection** | Instant Gratification Collection |
| **License** | [GNU General Public License v3.0 (GPLv3)](https://www.gnu.org/licenses/gpl-3.0.html) |
| **Source Code** | [GitHub - Rifaditya/Instant-Gratification-stack-size-adjuster](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster/issues) |
| **Documentation / Wiki** | [GitHub Wiki](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster/wiki) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports—provided your project remains open-source under GPLv3 with proper attribution.<br>
> **However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.**

---

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
