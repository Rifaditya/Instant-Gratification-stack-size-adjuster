<p align="center">
  <a href="https://discord.gg/EV99bgAFqb"><img src="https://img.shields.io/badge/Discord-Join_Community-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Join Discord"></a>
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://modrinth.com/mod/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-purple?style=for-the-badge" alt="Requires Dasik Library"></a>
  <img src="https://img.shields.io/badge/Environment-Server_&_Client-success?style=for-the-badge" alt="Server & Client">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
</p>

# 📦 Stack Size Adjuster

> **"Unshackle Your Inventory. Configurable Stack Limits with High-Performance Drop Protection & Dynamic Font Scaling."**

---

## 📖 Introduction

Minecraft's standard 64-item stack ceiling was established over 15 years ago when the game had only a handful of blocks and crafting recipes. In modern survival, building large structures, massive terraforming projects, or sorting large storage rooms turns inventory management into a chore of dozens of chests and endless hauling trips.

**Stack Size Adjuster** solves the inventory bottleneck under the **Instant Gratification** design philosophy. It dynamically expands item stack limits server-wide across natural item categories (64-stack items, 16-stack items, and 1-stack items) with real-time network synchronization, client-side auto-scaling numbers, and container drop lag prevention.

> [!NOTE]
> **1 Jar 1 Version Policy:** I build **1 dedicated JAR for each Minecraft version** (e.g. 1.20.1, 1.21.1, 1.21.11, 26.1.2, 26.2, 26.3+). Please download the exact build that matches your Minecraft installation.
> 
> **Server & Client Architecture:** Install on the server (or singleplayer world) to enable custom stack logic. Clients with the mod installed will benefit from dynamic font auto-scaling in inventory slots and instant GameRule sync!

Part of the **Instant Gratification Collection** — mods designed to streamline your gameplay and respect your time.

---

## ✨ Features

### 🚀 Category-Based Stack Expansion
- **64-Stack Items (Default: 128):** Expands building blocks, ores, ingots, wood, and standard resources up to any integer limit.
- **16-Stack Items (Default: 32):** Expands utility items that naturally cap at 16 (ender pearls, snowballs, buckets, signs, eggs) without breaking their identity.
- **1-Stack Items (Default: 1):** Kept at 1 by default to ensure tools, weapons, and armor retain standard behavior. Server owners can uncap this category at any time if desired.
- **Uncapped Freedom:** Supports stack sizes up to 2,147,483,647 (Integer.MAX_VALUE), adhering to the Freedom Over Anti-Crash design principle.

### 🛡️ Container Drop Lag Protection
- Breaking a chest containing thousands of items in vanilla would normally spawn hundreds or thousands of individual item entities, creating severe TPS drops or client stutter.
- **Cluster Drop System:** Intelligently groups dropped items into a controlled number of item entities per inventory slot (governed by `max_drop_entities`, default 8), preserving full item quantities while keeping server performance smooth.

### 🔤 Dynamic Inventory Font Scaling
- Numbers with 3, 4, or more digits (like 128, 999, or 9999) normally overflow outside the 16x16 inventory slot boundary.
- The client-side renderer calculates text width and dynamically scales down high-count numbers so they fit cleanly and legibly inside the item slot.

### ⚡ Real-Time Network Sync & Container Refresh
- Stack limits synchronize automatically via custom network payloads whenever a player joins or an admin changes GameRules.
- Open container and inventory screens immediately refresh with the updated limits without requiring a server reboot or player relog.

### 🧩 Addon Ecosystem Compatibility
- Built-in API hooks allow specialized companion mods to register dedicated per-item overrides:
  - **Potion Stacker Addon:** Stacks drinkable, splash, and lingering potions, water bottles, and stews.
  - **Stew Stacker Addon:** Stacks mushroom stews, rabbit soups, beetroot soups, and suspicious stews.

---

## ⚙️ Native GameRules & Configuration

Stack limits can be adjusted in real time using vanilla GameRules:

| GameRule Key | Type | Default | Valid Range | Description |
| :--- | :---: | :---: | :---: | :--- |
| `stack-size-adjuster:items_64_limit` | `Integer` | `128` | `1 – 2,147,483,647` | Maximum stack size for items that naturally stack to 64. |
| `stack-size-adjuster:items_16_limit` | `Integer` | `32` | `1 – 2,147,483,647` | Maximum stack size for items that naturally stack to 16. |
| `stack-size-adjuster:items_1_limit` | `Integer` | `1` | `1 – 2,147,483,647` | Maximum stack size for items that naturally stack to 1. |
| `stack-size-adjuster:max_drop_entities` | `Integer` | `8` | `1 – 64` | Maximum item entities spawned per slot when a container breaks. |

### 🖥️ In-Game Configuration Screen (ModMenu & YACL)
If **ModMenu** and **YetAnotherConfigLib (YACL)** are installed, you can configure these options directly from the client title screen or mods menu:
- Config file: `config/stack-size-adjuster.json` (acts as baseline defaults for newly generated worlds).

---

## ☕ Support & Creator Community

I am an independent solo developer creating lightweight, vanilla-enhancing mods. If Stack Size Adjuster makes your survival experience more enjoyable, consider supporting future development:

<p align="center">
  <a href="https://ko-fi.com/rifaditya"><img src="https://img.shields.io/badge/Ko--fi-Support_on_Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Support on Ko-fi"></a>
  <a href="https://sociabuzz.com/rifaditya"><img src="https://img.shields.io/badge/SocioBuzz-Support_Creator-00A651?style=for-the-badge" alt="Support on SocioBuzz"></a>
  <a href="https://saweria.co/rifaditya"><img src="https://img.shields.io/badge/Saweria-Support_Local-FFA500?style=for-the-badge" alt="Support on Saweria"></a>
</p>

> [!TIP]
> **🇮🇩 Indonesian Local Payment Note:** Indonesian supporters can support my work directly via **Saweria** or **SocioBuzz** using **GoPay, OVO, Dana, QRIS, LinkAja**!

Join my official Discord community for development updates, test builds, and feedback:
- 💬 **Discord Community:** [https://discord.gg/EV99bgAFqb](https://discord.gg/EV99bgAFqb)

---

## 📜 Metadata & Permissions

| Property | Value |
| :--- | :--- |
| **Mod Name** | Stack Size Adjuster |
| **Namespace / Mod ID** | `stack-size-adjuster` |
| **License** | GNU General Public License v3.0 (GPLv3) |
| **Side Safety** | Server & Client (Network Synchronized) |
| **Required Dependencies** | Fabric API, Dasik Library |
| **Optional Dependencies** | ModMenu, YetAnotherConfigLib (YACL) |
| **Source Code** | [GitHub Repository](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster/issues) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack! The mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading or mirroring the JAR to third-party scraper sites or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are free to inspect the code, learn from it, and fork it for genuine modifications or substantial feature additions under GPLv3 with proper attribution.<br>
> **Straight 1:1 re-uploads, clone forks without meaningful functional additions, or re-publishing identical builds under different project names are strictly forbidden.**

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>