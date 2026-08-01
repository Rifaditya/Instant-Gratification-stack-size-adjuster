<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 📦 Stack Size Adjuster

### 🎮 Version Compatibility

This mod is actively maintained and updated:
* <strong>Minecraft 26.2+</strong>: Fully supported with open-ended forward compatibility.

<blockquote><strong>Take Control of Your Inventory. Custom Limits, No Boundaries.</strong></blockquote>

Tired of inventory clutter? Or do you want to create a harder, more restricted survival experience? <strong>Stack Size Adjuster</strong> grants you full authority over item stack limits. Scale weapons, armor, blocks, and consumables dynamically at runtime.

Part of the <strong>Instant Gratification Collection</strong> — mods that respect the player's time.

---

## ✨ Features

### 🎚️ Category-Based Customization
Configure limits for the three standard Minecraft item categories:
- <strong>64-Stack Limit</strong>: Standard stackable items (blocks, raw food, ores, building materials). Default: <code>128</code>.
- <strong>16-Stack Limit</strong>: Semi-stackable items (ender pearls, snowballs, eggs, signs). Default: <code>32</code>.
- <strong>1-Stack Limit</strong>: Unstackable items (weapons, tools, armor, potions, stews, beds). Default: <code>1</code>.

### 🔢 Raw Integer Values
No more artificial caps! Adjust limits dynamically to any integer value from <code>1</code> up to <code>2,147,483,647</code>.
- <strong>Overflow Protection Warning</strong>: The configuration GUI features a dynamic warning tooltip that alerts you if a limit is set above <code>39,768,215</code>. This threshold is specifically to prevent total-container overflows when filling containers completely with the <em>same</em> item type. <strong>Change it at your own risk!</strong>
  * <em>⚠️ Performance Note</em>: Moving or sorting extremely large item stacks (millions or billions) can cause transient game lag or frame stutter due to CPU calculation load.
  * <em>🎒 Modded Container / Backpack Safety Formula</em>: If you are using custom containers or modded backpacks, you can calculate the maximum safe stack size to fully fill the container without overflow using this formula:
    <br><br>
    <code>Safe Stack Limit = 2,147,483,647 / [Total Slots in Container]</code>
    <br><br>
    * <strong>100-slot backpack</strong>: <code>21,474,836</code> max per slot
    * <strong>200-slot backpack</strong>: <code>10,737,418</code> max per slot
  * <em>Safe Reference List (for identical items)</em>:
    * <strong>Hopper (5 slots)</strong>: <code>429,496,729</code> max per slot
    * <strong>Dispenser / Dropper (9 slots)</strong>: <code>238,609,294</code> max per slot
    * <strong>Single Chest / Shulker Box / Barrel (27 slots)</strong>: <code>79,536,431</code> max per slot
    * <strong>Double Chest / Large Chest (54 slots)</strong>: <code>39,768,215</code> max per slot
  - <em>Note on Different Items</em>: If you are storing <em>different</em> item types in the same container, you can safely go up to the absolute limit of <code>2,147,483,647</code> per slot since the game saves and tracks different item types independently!
  - <em>Flat vs. Nested Storage Safety</em>:
    * <strong>Player Inventory (Fully Immune)</strong>: The player inventory is saved flatly as direct separate tags inside the player <code>.dat</code> files. Because individual slot counts are never summed up during saving, you can safely set and fill all 36 slots of your player inventory up to the absolute maximum limit of <code>2,147,483,647</code> with identical items without any overflow or deletion issues!
    * <strong>Chests, Barrels, and Shulker Boxes (Vulnerable to Overflow)</strong>: World containers (like Chests, Double Chests, and Barrels) and nested container items (like Shulker Boxes and Bundles) are vulnerable. Tooltip renderers, inventory sorters, or game functions sum up their total contents. If the total count of identical items inside exceeds 2.14 billion, it triggers a signed 32-bit integer overflow and deletes your items. Make sure slots in these containers stay under the safe per-slot limits!
    * <strong>Storage Networks & Mod Compatibility</strong>: Works with storage network mods (like Tom's Simple Storage, etc.) at normal and moderately high values. Extreme limits (100M+) inside very large networks may cause items to be lost or trigger extraction loops due to third-party integer limitations (truncations in external mod logic). Change it at your own risk!

### 🔓 Slot Capacity Bypass
Say goodbye to the standard slot stack limit of 99 items. Stack Size Adjuster overrides the default container and stack serializer limits, allowing you to hold massive stacks (e.g., <code>640</code> or <code>1000</code>) in any chest, hopper, or inventory slot.

### 📐 Dynamic Font Scale-Down
Worried about large numbers overlapping slots? When stack counts exceed 99, the client-side renderer dynamically scales down the count numbers to fit perfectly within slot boundaries, ensuring text never overlaps.

### 🛡️ Container Destruction Protection & Item Clumps
Includes the <strong>Max Drop Entities</strong> GameRule (<code>stack-size-adjuster:max_drop_entities</code>, default: <code>8</code>) and integrates with <strong>Item Clumps</strong> to cap spawned item entities when containers break or players die, preventing severe server and client lag spikes.

### 🎯 Drag-Splitting & Consolidation Precision
Overrides container drag-splitting with double-precision math during QUICK_CRAFT and <code>ItemStack</code> consolidation to prevent item duplication or desync glitches when organizing massive stacks.

### 🛠️ Give Command Integer Overflow Safeguard
Re-routes <code>/give</code> command calculations with <code>long</code> math and safe clamping to <code>Integer.MAX_VALUE</code> so players can safely <code>/give</code> items even with extreme stack limits active.

### 🔌 Programmatic Addon Override API
Exposes <code>StackSizeManager.registerOverride(...)</code> allowing third-party addon mods (like Potion Stacker Addon) to dynamically inject custom stack rules without mixin conflicts.

### 🔒 Forward Compatibility & Version Guard
Pre-configured with <code>"minecraft": ">=26.2-"</code> open-ended bounds and zero-dependency <code>ModVersionGuard</code> startup protection to prevent world save corruption on unsupported game drops.

---

## ⚙️ Configuration (Native Game Rules)

<blockquote>
<strong>⚠️ Important: Config vs. In-Game GameRules</strong><br>
The global configuration file only defines <strong>default values for new worlds</strong> at creation time.
If you have <strong>already created/opened a world</strong>, changing the config file will have no effect. You must change the settings in-game using the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.
</blockquote>

Configure options in-game using <code>/gamerule</code> or via the integrated YACL config GUI:
- <code>stack-size-adjuster:items_64_limit</code> (Default: <code>128</code>)
- <code>stack-size-adjuster:items_16_limit</code> (Default: <code>32</code>)
- <code>stack-size-adjuster:items_1_limit</code> (Default: <code>1</code>)
- <code>stack-size-adjuster:max_drop_entities</code> (Default: <code>8</code>)

---

## 📦 Installation

1. Install <strong><a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api">Fabric API</a></strong>.
2. Install <strong><a href="https://www.curseforge.com/minecraft/mc-mods/item-clumps">Item Clumps</a></strong> (Required dependency for item entity compression).
3. Download the mod jar and place it in your <code>mods</code> folder.
4. Launch the game.

---

## ☕ Support

If you enjoy the <strong>Instant Gratification</strong> collection, consider supporting development!

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
