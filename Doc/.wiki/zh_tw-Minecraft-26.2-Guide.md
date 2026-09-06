# Minecraft 26.2 目標指南

| 屬性 | 規格說明 |
| :--- | :--- |
| **Minecraft 目標版本** | 26.2（穩定正式版） |
| **模組版本** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*`（任何 26.2 相容版本） |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **識別碼格式** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## 概述

適用於 Minecraft 26.2 的 **Stack Size Adjuster** 旨在解除原版嚴苛的物品堆疊限制。它允許玩家與伺服器管理員針對原本可堆疊至 64、16 或 1 的物品分別設定自訂最大堆疊上限。

### 26.2 核心特性：
1. **動態類別縮放**：透過 GameRules 獨立調控 64 堆疊、16 堆疊以及單物品工具/藥水的上限。
2. **容器掉落卡頓防護**：破壞箱子或容器時，限制每個物品欄槽位生成實體數量上限（`max_drop_entities`）。
3. **整數溢位安全防護**：針對 54 槽位大箱子，建議將上限限制在 $39,768,215$ 以內，防止超過 32 位元有號整數上限（$21.4\text{ 億}$）。
4. **即時伺服端-客戶端同步**：自訂 S2C 封包 `stack-size-adjuster:sync_limit` 瞬間同步物品欄選單，無需玩家重新連線。

---

## 安裝與設定

1. 確保已安裝 **Fabric Loader 0.19.1+** 與 **Java 25**。
2. 將 `stack-size-adjuster-1.4.16+26.2.jar` 放入 `.minecraft/mods` 目錄中。
3. 確保已安裝必要依賴：
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. 啟動遊戲客戶端或伺服端。

---

## 執行時期版本守衛 (Runtime Version Guard)

在模組初始化期間，`ModVersionGuard` 會透過以下邏輯驗證類別路徑完整性：
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
如果偵測到不相容的載入器上下文或不符合的 Minecraft 執行時期環境，初始化將安全中止並輸出詳細記錄。
