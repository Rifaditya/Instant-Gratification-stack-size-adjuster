<p align="center">
  <a href="https://discord.gg/EV99bgAFqb"><img src="https://img.shields.io/badge/Discord-Join_Community-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Join Discord"></a>
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <img src="https://img.shields.io/badge/Environment-Server_&_Client-success?style=for-the-badge" alt="Server & Client">
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 📦 Stack Size Adjuster

> **"Unshackle Your Inventory. Custom Stack Sizes Up to 9,999 with 100% Network Safety."**

---

## 📖 Introduction

Minecraft's universal 64-item stack size ceiling was decided over 15 years ago when the game had a fraction of today's blocks, items, and crafting complexity. In modern survival, undertaking large-scale builds—such as castles, nether highways, terraformed mountains, or automated storage warehouses—demands endless tedious chest-sorting, dozens of shulker boxes, and constant back-and-forth hauling trips for simple building materials like Cobblestone, Dirt, Sand, and Logs.

**Stack Size Adjuster** solves the inventory crisis under the **Instant Gratification** design philosophy. It dynamically expands item stack limits server-wide up to **9,999 items** (or custom limits). Leveraging modern Minecraft's VarInt network protocol, mega-stacks synchronize seamlessly between server and client with zero desynchronization, full container safety, hopper protection, and trade GUI safeguards.

> [!NOTE]
> **1 Jar 1 Version Policy:** I build **1 dedicated JAR for each Minecraft version** (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.
> 
> **Server-Authoritative:** Requires installation on the server. Clients running Fabric will natively display accurate custom stack counts in inventories, while vanilla clients receive synchronized VarInt stack updates!

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

### 🚀 Massive Stack Expansion (Up to 9,999)
- **Universal or Category Limits:** Expand maximum stack limits for building blocks, ores, food, and raw materials from 64 up to **9,999 items per inventory slot**.
- **Modern VarInt Network Protocol:** Built from the ground up for modern Minecraft (26.x+), utilizing VarInt network serialization rather than legacy 1-byte limits. Mega-stacks serialize effortlessly across multiplayer packets with zero data truncation.

### 🛡️ Container & Trade Menu Safeguards
- **Hopper Drip-Feed Throttling:** Hoppers and droppers under mega-stacks transfer exactly 1 item per standard vanilla transfer tick, ensuring sorting systems, item elevators, and redstone contraptions never overflow or stall.
- **Villager & Merchant Protection:** Prevents trading exploits and inventory overflow when interacting with Wandering Traders and Villagers by dynamically clamping exchange slots to safe standard limits.
- **Crafting Grid Precision:** Shift-clicking crafted items smoothly fills available mega-stacks without scattering leftover items onto the floor.

### ⚙️ Modular Whitelist & Blacklist Tag Engine
- **Item Exclusions:** Keep specific delicate items unstackable (e.g. Shulker Boxes containing items, Enchanted Books, or custom modded containers) using data-driven tags (`#stack_size_adjuster:unstackable_blacklist`).
- **Targeted Category Overrides:** Assign custom stack thresholds to specific item categories—for example, 1,000 for raw building materials, 256 for food, and 16 for tools.

---

## 📊 Inventory Capacity Comparison

| Storage Container | Vanilla 64-Cap Limit | With 256 Stack Limit | With 1,000 Stack Limit | With 9,999 Mega-Stacks |
| :--- | :---: | :---: | :---: | :---: |
| **Player Main Inventory (27 slots)** | 1,728 items | **6,912 items** | **27,000 items** | **269,973 items** |
| **Single Chest (27 slots)** | 1,728 items | **6,912 items** | **27,000 items** | **269,973 items** |
| **Double Chest (54 slots)** | 3,456 items | **13,824 items** | **54,000 items** | **539,946 items** |
| **Shulker Box (27 slots)** | 1,728 items | **6,912 items** | **27,000 items** | **269,973 items** |
| **Ender Chest Capacity** | 1,728 items | **6,912 items** | **27,000 items** | **269,973 items** |

---

## ⚙️ Native GameRules & Configuration

Configure stack size limits dynamically on the fly:

| GameRule Key | Type | Default | Valid Range | Description |
| :--- | :---: | :---: | :---: | :--- |
| `stack_size_adjuster:max_stack_size` | `Integer` | `1000` | `64 – 9999` | Global maximum stack limit for configured item categories. |
| `stack_size_adjuster:affect_blocks_only` | `Boolean` | `false` | `true / false` | Restricts stack increases strictly to placeable block items. |
| `stack_size_adjuster:enable_food_stacking` | `Boolean` | `true` | `true / false` | Extends mega-stacking to edible food items. |
| `stack_size_adjuster:protect_shulker_boxes` | `Boolean` | `true` | `true / false` | Prevents nesting filled shulker boxes inside mega-stacks. |

---

## 📖 In-Depth How-To & Gameplay Playbook

### Step 1: Server & Client Installation
1. Install **Fabric Loader** and **Fabric API** for Minecraft 26.2+ / 26.3+.
2. Drop `stack-size-adjuster-x.y.z+<version>.jar` into your server and client `mods/` folders.
3. Launch Minecraft. Building blocks will now automatically stack up to the configured limit!

### Step 2: Consolidating Storage Warehouses
- Walk into your warehouse and shift-click matching items together: watch entire double chests of cobblestone condense into just a few slots.
- Free up hundreds of chests, dramatically reducing entity and tile-entity rendering lag in your main survival base!

### Step 3: Pairing with Companion Addons
- For stacking drinkable and splash potions, install **Potion Stacker Addon**.
- For stacking mushroom stews, rabbit soups, and suspicious stews, install **Stew Stacker Addon**.
- For ground-item lag cleanup, install **Item Clumps**!

---

## ☕ Support & Creator Community

I am an independent solo developer creating lightweight, vanilla-enhancing mods that respect your time and game performance. If Stack Size Adjuster liberates your inventory, consider supporting future development:

<p align="center">
  <a href="https://ko-fi.com/rifaditya"><img src="https://img.shields.io/badge/Ko--fi-Support_on_Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Support on Ko-fi"></a>
  <a href="https://sociabuzz.com/rifaditya"><img src="https://img.shields.io/badge/SocioBuzz-Support_Creator-00A651?style=for-the-badge" alt="Support on SocioBuzz"></a>
  <a href="https://saweria.co/rifaditya"><img src="https://img.shields.io/badge/Saweria-Support_Local-FFA500?style=for-the-badge" alt="Support on Saweria"></a>
</p>

> [!TIP]
> **🇮🇩 Indonesian Local Payment Note:** Indonesian supporters can also support my development work directly using local payment options (**GoPay, OVO, Dana, QRIS, LinkAja**) via **Saweria** or **SocioBuzz**!

Join our official Discord community for live development updates, early test builds, and friendly support:
- 💬 **Discord Community:** [https://discord.gg/EV99bgAFqb](https://discord.gg/EV99bgAFqb)

---

## 📜 Metadata & Permissions

| Property | Value |
| :--- | :--- |
| **Mod Name** | Stack Size Adjuster |
| **Namespace / Mod ID** | `stack_size_adjuster` |
| **License** | GNU General Public License v3.0 (GPLv3) |
| **Side Safety** | Server & Client (Network Synchronized) |
| **Source Code** | [GitHub Repository](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster/issues) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports—provided your project remains open-source under GPLv3 with proper attribution.<br>
> **However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.**

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
