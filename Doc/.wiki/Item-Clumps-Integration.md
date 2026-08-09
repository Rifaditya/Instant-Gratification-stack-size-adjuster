# Item Clumps Integration

## Overview

Stack Size Adjuster works in direct synergy with **Item Clumps** (`item_clumps >=1.0.18+26.2`). While Stack Size Adjuster controls item stack limits in inventories and container drop caps, Item Clumps handles nearby ground entity merging.

---

## 🤝 Synergistic Mechanics Breakdown

```
[ Container Break Event ]
           |
           v
[ Stack Size Adjuster: InventoryDropHelper ]
Splits stack into capped entity count (e.g. max 8 entities per slot)
           |
           v
[ Entities Spawned on Ground ]
           |
           v
[ Item Clumps Mod: Ground Entity Merging ]
Scans 3.5 block radius and merges item entities into a single entity pile
           |
           v
[ Single High-Count Item Entity ] (Zero Lag!)
```

---

## 📊 Feature Synergy Matrix

| Responsibility Layer | Handled By | Benefit |
| :--- | :--- | :--- |
| **Inventory Stack Limits** | **Stack Size Adjuster** | Custom 64/16/1 limits dynamically enforced in chests & players |
| **Container Break Entity Spawning** | **Stack Size Adjuster** | Prevents spawning thousands of entities during container breaks |
| **Ground Entity Radius Merging** | **Item Clumps** | Merges dropped entities into single stacks up to integer limits |
| **Item Pick-Up Mechanics** | **Minecraft Core & Mixins** | Picks up merged entity stacks directly into expanded slots |

---

## ⚙️ Recommended GameRule Configuration

When running with **Item Clumps**, set `max_drop_entities` to **8** (or **1** for ultra-high performance):
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
This ensures container drops radiate visually with 8 initial entities, which Item Clumps immediately consolidates into a single entity pile.
