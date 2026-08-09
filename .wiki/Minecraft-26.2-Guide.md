# Minecraft 26.2 Target Guide

| Attribute | Specification |
| :--- | :--- |
| **Minecraft Target** | 26.2 (Stable Release) |
| **Mod Version** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*` (Any 26.2 compatible) |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **Identifier Format** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## Overview

**Stack Size Adjuster** for Minecraft 26.2 is engineered to remove arbitrary item stack restrictions. It allows players and administrators to set custom maximum stack limits for items that naturally stack to 64, 16, or 1.

### Key Capabilities in 26.2:
1. **Dynamic Category Scaling**: GameRules control category caps for 64-stackable, 16-stackable, and single-item tools/potions.
2. **Container Drop Lag Prevention**: Caps maximum item entity spawns per slot (`max_drop_entities`) when breaking chests or containers.
3. **Integer Overflow Protection**: Guards against $2.14\text{ billion}$ signed 32-bit integer overflows in 54-slot Large Chests by recommending a safety bound of $39,768,215$.
4. **Live Server-Client Sync**: Custom S2C packet `stack-size-adjuster:sync_limit` updates inventory menus instantly without requiring client reconnects.

---

## Installation & Setup

1. Ensure **Fabric Loader 0.19.1+** and **Java 25** are installed.
2. Place `stack-size-adjuster-1.4.16+26.2.jar` into your `.minecraft/mods` directory.
3. Ensure required dependencies are present:
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. Launch the game or server.

---

## Runtime Version Guard

During mod initialization, `ModVersionGuard` validates classpath resolution using:
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
If an incompatible loader context or mismatched Minecraft runtime is detected, initialization safely halts with an informative log trace.
