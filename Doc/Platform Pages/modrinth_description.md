# Stack Size Adjuster

Allows customizing item stack sizes dynamically for 64-stackable, 16-stackable, and non-stackable item categories using GameRules or configuration screens.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

- **Category-Based Customization**: Configure limits for three default categories of items:
  - **64-Stack Limit**: Blocks, raw food, ores, etc. (Default: `64`)
  - **16-Stack Limit**: Snowballs, eggs, ender pearls, etc. (Default: `16`)
  - **1-Stack Limit**: Tools, weapons, armor, potions, stews, etc. (Default: `1`)
- **Raw Integer Values**: Configure stack limits to any custom integer value up to `2,147,483,647` (no limit clamping!).
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

1. Install **Fabric API**.
2. Download the mod jar and place it in your `mods` folder.
3. Launch the game.

---

## 📜 Credits & License

- **Architect**: Dasik (Rifaditya)
- **License**: GPLv3
- **Sources**: [GitHub Repo](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster)

---

> [!WARNING]
> **Modpack Permissions:** You are free to include this mod in modpacks, **provided the modpack is hosted on the same platform** (e.g. Modrinth). Cross-platform redistribution is not permitted. If you download this mod from Modrinth, your modpack must also be published on Modrinth.
