# Stack Size Adjuster

Allows customizing item stack sizes dynamically for 64-stackable, 16-stackable, and non-stackable item categories using GameRules or configuration screens.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

- **Category-Based Customization**: Configure limits for three default categories of items:
  - **64-Stack Limit**: Blocks, raw food, ores, etc. (Default: `128`)
  - **16-Stack Limit**: Snowballs, eggs, ender pearls, etc. (Default: `32`)
  - **1-Stack Limit**: Tools, weapons, armor, potions, stews, etc. (Default: `1`)
- **Raw Integer Values**: Configure stack limits to any custom integer value up to `2,147,483,647` (no limit clamping!).
  - *Note on Limits & Storage*: The configuration GUI features a dynamic warning tooltip at `39,768,215` to prevent total-container overflows if you fill a container completely with the *same* item.
    * *⚠️ Performance Note*: Moving or sorting extremely large item stacks (millions or billions) can cause transient game lag or frame stutter due to CPU calculation load.
    * *🎒 Modded Container / Backpack Safety Formula*: If you are using custom containers or modded backpacks, you can calculate the maximum safe stack size to fully fill the container without overflow using this formula:
      `Safe Stack Limit = 2,147,483,647 / [Total Slots in Container]`
      * **100-slot container**: `21,474,836` max per slot
      * **200-slot container**: `10,737,418` max per slot
    * *Safe Reference List (for identical items)*:
      * **Hopper (5 slots)**: `429,496,729` max per slot
      * **Dispenser / Dropper (9 slots)**: `238,609,294` max per slot
      * **Single Chest / Shulker Box / Barrel (27 slots)**: `79,536,431` max per slot
      * **Player Inventory (36 slots)**: `59,652,323` max per slot
      * **Double Chest / Large Chest (54 slots)**: `39,768,215` max per slot
    - *Note on Different Items*: If you are storing *different* item types in the same container, you can safely use the absolute maximum limit of `2,147,483,647` per slot since the game tracks and serializes slots independently!
    - *Flat vs. Nested Storage Safety*:
      * **Player Inventory (Fully Immune)**: The player inventory is saved flatly as direct separate tags inside the player `.dat` files. Because individual slot counts are never summed up during saving, you can safely set and fill all 36 slots of your player inventory up to the absolute maximum limit of `2,147,483,647` with identical items without any overflow or deletion issues!
      * **Chests, Barrels, and Shulker Boxes (Vulnerable to Overflow)**: World containers (like Chests, Double Chests, and Barrels) and nested container items (like Shulker Boxes and Bundles) are vulnerable. Tooltip renderers, inventory sorters, or game functions sum up their total contents. If the total count of identical items inside exceeds 2.14 billion, it triggers a signed 32-bit integer overflow and deletes your items. Make sure slots in these containers stay under the safe per-slot limits!
- **Slot Capacity Bypass**: Bypasses Minecraft's default slot-level count limits, allowing inventory slots to hold over 99 items cleanly.
- **Dynamic Count Text Scaling**: Inventory count numbers automatically scale down when they exceed 99 to fit inside slot boundaries without overlapping adjacent slots.
- **Modded Item Compatibility**: Automatically recognizes and scales stack sizes for modded items based on their default base limits.
- **Optional YACL / ModMenu Integration**: If YetAnotherConfigLib (YACL) and ModMenu are installed, provides an in-game config GUI. Otherwise, works out of the box using server-compatible GameRules.

---

## 📋 Configuration

Change stack size limits using `/gamerule` in-game or via the config GUI:
- `stack-size-adjuster:items_64_limit` (Default: `64`)
- `stack-size-adjuster:items_16_limit` (Default: `16`)
- `stack-size-adjuster:items_1_limit` (Default: `1`)

---

## 📦 Installation

1. Install **[Fabric API](https://modrinth.com/mod/fabric-api)**.
2. Download the mod jar and place it in your `mods` folder.
3. Launch the game.

---

## 📜 Credits & License

- **Architect**: Dasik (Rifaditya)
- **License**: GPLv3
- **Sources**: [GitHub Repo](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster)

---

> **Modpack Permissions:** You are free to include this mod in modpacks, **provided the modpack is hosted on the same platform** (e.g. Modrinth).
>
> **Cross-platform distribution is not permitted.** If you download this mod from Modrinth, your modpack must also be published on Modrinth.
