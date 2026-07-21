<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 📦 Stack Size Adjuster

### 🎮 Version Compatibility & Parity

This mod is active and fully supported:
* **Minecraft 26.2+**: Current public release — **`v1.0.1`**

<blockquote><strong>Take Control of Your Inventory. Custom Limits, No Boundaries.</strong></blockquote>

Tired of inventory clutter? Or do you want to create a harder, more restricted survival experience? **Stack Size Adjuster** grants you full authority over item stack limits. Scale weapons, armor, blocks, and consumables dynamically at runtime.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

### 🎚️ Category-Based Customization
Configure limits for the three standard Minecraft item categories:
- **64-Stack Limit**: Standard stackable items (blocks, raw food, ores, building materials). Default: `128`.
- **16-Stack Limit**: Semi-stackable items (ender pearls, snowballs, eggs, signs). Default: `32`.
- **1-Stack Limit**: Unstackable items (weapons, tools, armor, potions, stews, beds). Default: `1`.

### 🔢 Raw Integer Values
No more artificial caps! Adjust limits dynamically to any integer value from `1` up to `2,147,483,647`.
- **Overflow Protection Warning**: The configuration GUI features a dynamic warning tooltip that alerts you if a limit is set above `39,768,215`. This threshold is specifically to prevent total-container overflows when filling containers completely with the *same* item type. **Change it at your own risk!**
  * *⚠️ Performance Note*: Moving or sorting extremely large item stacks (millions or billions) can cause transient game lag or frame stutter due to CPU calculation load.
  * *🎒 Modded Container / Backpack Safety Formula*: If you are using custom containers or modded backpacks, you can calculate the maximum safe stack size to fully fill the container without overflow using this formula:
    
    `Safe Stack Limit = 2,147,483,647 / [Total Slots in Container]`
    
    * **100-slot backpack**: `21,474,836` max per slot
    * **200-slot backpack**: `10,737,418` max per slot
  * *Safe Reference List (for identical items)*:
    * **Hopper (5 slots)**: `429,496,729` max per slot
    * **Dispenser / Dropper (9 slots)**: `238,609,294` max per slot
    * **Single Chest / Shulker Box / Barrel (27 slots)**: `79,536,431` max per slot
    * **Double Chest / Large Chest (54 slots)**: `39,768,215` max per slot
  - *Note on Different Items*: If you are storing *different* item types in the same container, you can safely go up to the absolute limit of `2,147,483,647` per slot since the game saves and tracks different item types independently!
  - *Flat vs. Nested Storage Safety*:
    * **Player Inventory (Fully Immune)**: The player inventory is saved flatly as direct separate tags inside the player `.dat` files. Because individual slot counts are never summed up during saving, you can safely set and fill all 36 slots of your player inventory up to the absolute maximum limit of `2,147,483,647` with identical items without any overflow or deletion issues!
    * **Chests, Barrels, and Shulker Boxes (Vulnerable to Overflow)**: World containers (like Chests, Double Chests, and Barrels) and nested container items (like Shulker Boxes and Bundles) are vulnerable. Tooltip renderers, inventory sorters, or game functions sum up their total contents. If the total count of identical items inside exceeds 2.14 billion, it triggers a signed 32-bit integer overflow and deletes your items. Make sure slots in these containers stay under the safe per-slot limits!
    * **Storage Networks & Mod Compatibility**: Works with storage network mods (like Tom's Simple Storage, etc.) at normal and moderately high values. Extreme limits (100M+) inside very large networks may cause items to be lost or trigger extraction loops due to third-party integer limitations (truncations in external mod logic). Change it at your own risk!

### 🔓 Slot Capacity Bypass
Say goodbye to the standard slot stack limit of 99 items. Stack Size Adjuster overrides the default container and stack serializer limits, allowing you to hold massive stacks (e.g., `640` or `1000`) in any chest, hopper, or inventory slot.

### 📐 Dynamic Font Scale-Down
Worried about large numbers overlapping slots? When stack counts exceed 99, the client-side renderer dynamically scales down the count numbers to fit perfectly within slot boundaries, ensuring text never overlaps.

### 📡 Dynamic GameRules (No Restarts Required)
Configure limits instantly on-the-fly using namespaced GameRules. Client configurations automatically sync with the server during logins or runtime changes.

---

## ⚙️ Configuration (Native Game Rules)


<blockquote class="warning">
<strong>âš ï¸ Important: Config vs. In-Game GameRules</strong><br>
The global configuration file only defines <strong>default values for new worlds</strong> at creation time.
If you have <strong>already created/opened a world</strong>, changing the config file will have no effect. You must change the settings in-game using the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.
</blockquote>
Configure options in-game using `/gamerule` or via the integrated YACL config GUI:
- `stack-size-adjuster:items_64_limit`
- `stack-size-adjuster:items_16_limit`
- `stack-size-adjuster:items_1_limit`

---

## 📦 Installation

1. Install **Fabric API**.
2. Download the mod jar and place it in your `mods` folder.
3. Launch the game.

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider supporting development!

<p align="center">

<a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
<a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
<a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>

</p>

<blockquote><strong>🇮🇩 Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!</blockquote>

---

<blockquote>
    <strong>📦 Modpack Permissions & Distribution:</strong><br>
    You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on <strong>Modrinth</strong> or <strong>CurseForge</strong>. Re-uploading or redistributing the mod jar file to third-party sites is strictly prohibited unless explicitly permitted by the creator.
    <br><br>
    <strong>License & Forks:</strong><br>
    Since the source code is licensed under <strong>GNU GPLv3</strong>, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3. The prohibition on third-party redistribution applies exclusively to the official compiled releases/jars published by the original creator (Dasik/Rifaditya). Forks must be published as distinct projects, not direct re-uploads of official builds.
</blockquote>


---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Creator** | **Dasik** (Rifaditya) |
| **Collection** | Instant Gratification |
| **License** | GPLv3 |

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
