# Item Clumps 連動整合

## 概述

Stack Size Adjuster 與 **Item Clumps**（`item_clumps >=1.0.18+26.2`）形成深度協同。Stack Size Adjuster 負責控制物品欄內的堆疊上限與容器破壞掉落上限，而 Item Clumps 則負責地面相鄰掉落物實體的合併。

---

## 🤝 協同機制流程圖

```
[ 容器被破壞事件 ]
        |
        v
[ Stack Size Adjuster: InventoryDropHelper ]
將物品堆疊拆分為受控數量的實體（例如每個槽位最多 8 個實體）
        |
        v
[ 實體生成在地面上 ]
        |
        v
[ Item Clumps 模組: 地面掉落物實體合併 ]
掃描 3.5 格方塊半徑並將掉落物合併為單一實體堆
        |
        v
[ 單個高數量物品實體 ]（徹底消除卡頓！）
```

---

## 📊 功能分工協同矩陣

| 職責層次 | 負責模組 | 優勢價值 |
| :--- | :--- | :--- |
| **物品欄堆疊限制** | **Stack Size Adjuster** | 在玩家物品欄與箱子中動態執行 64/16/1 上限 |
| **容器破壞實體生成控制** | **Stack Size Adjuster** | 破壞箱子時防止生成數以千計的掉落物實體 |
| **地面掉落物範圍合併** | **Item Clumps** | 將散落實體合併為單個實體堆，支援到整數上限 |
| **拾取機制** | **Minecraft 核心與 Mixin** | 將合併的高數量實體直接拾取進擴容後的槽位 |

---

## ⚙️ 推薦遊戲規則設定

搭配 **Item Clumps** 執行時，建議將 `max_drop_entities` 設為 **8**（追求極限效能可設為 **1**）：
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
這樣容器掉落時既能保有 8 個實體四散拋灑的視覺回饋，Item Clumps 也能在瞬間將其合併為一個實體，兼顧視覺與效能。
