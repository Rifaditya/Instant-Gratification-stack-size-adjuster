# Item Clumps 联动整合

## 概述

Stack Size Adjuster 与 **Item Clumps**（`item_clumps >=1.0.18+26.2`）形成深度协同。Stack Size Adjuster 负责控制物品栏内的堆叠上限与容器破坏掉落上限，而 Item Clumps 则负责地面相邻掉落物实体的合并。

---

## 🤝 协同机制流程图

```
[ 容器被破坏事件 ]
        |
        v
[ Stack Size Adjuster: InventoryDropHelper ]
将物品堆叠拆分为受控数量的实体（例如每个槽位最多 8 个实体）
        |
        v
[ 实体生成在地面上 ]
        |
        v
[ Item Clumps 模组: 地面掉落物实体合并 ]
扫描 3.5 格方块半径并将掉落物合并为单一实体堆
        |
        v
[ 单个高数量物品实体 ]（彻底消除卡顿！）
```

---

## 📊 功能分工协同矩阵

| 职责层次 | 负责模块 | 优势价值 |
| :--- | :--- | :--- |
| **物品栏堆叠限制** | **Stack Size Adjuster** | 在玩家物品栏与箱子中动态执行 64/16/1 上限 |
| **容器破坏实体生成控制** | **Stack Size Adjuster** | 破坏箱子时防止生成数以千计的掉落物实体 |
| **地面掉落物范围合并** | **Item Clumps** | 将散落实体合并为单个实体堆，支持到整数上限 |
| **拾取机制** | **Minecraft 核心与 Mixin** | 将合并的高数量实体直接拾取进扩容后的槽位 |

---

## ⚙️ 推荐游戏规则配置

搭配 **Item Clumps** 运行时，建议将 `max_drop_entities` 设为 **8**（追求极限性能可设为 **1**）：
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
这样容器掉落时既能保有 8 个实体四散抛洒的视觉反馈，Item Clumps 也能在瞬间将其合并为一个实体，兼顾视觉与性能。
