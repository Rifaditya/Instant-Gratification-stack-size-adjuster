# 行為設定檔與條件

## 概述

Stack Size Adjuster 依靠一套狀態機架構，在伺服端與客戶端邊界之間無縫銜接世界設定、動態遊戲規則與網路封包。

---

## 🔄 狀態機生命週期圖

```text
               +----------------------------------+
               |        伺服端啟動 / 世界建立        |
               +----------------------------------+
                                |
                                v
               [ 載入基礎設定範本 ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ 註冊動態遊戲規則 ]
               (`items_64_limit`, `items_16_limit` 等)
                                |
                +---------------+---------------+
                |                               |
          全新建立的世界                     已存在的載入世界
                |                               |
                v                               v
        套用設定預設值至規則              從 `level.dat` 載入已存規則
                |                               |
                +---------------+---------------+
                                |
                                v
              [ 初始化執行期 StackSizeManager ]
                                |
                                v
              [ 玩家加入事件 / GameRule 編輯修改 ]
                                |
                                v
             [ 派發 S2C 封包: sync_limit ]
                                |
                                v
             [ 強制重新整理客戶端選單介面狀態 ]
```

---

## ⚙️ 核心觸發事件與處理器

1. **伺服端啟動完成 (`ServerLifecycleEvents.SERVER_STARTED`)**：
   - 重新載入基礎設定檔。
   - 若世界為新生成（`!overworldData.isInitialized()`），從基礎設定填入世界 GameRules 預設值。
   - 依據當前世界 GameRules 初始化 `StackSizeManager` 上限。
2. **玩家連線 (`ServerPlayConnectionEvents.JOIN`)**：
   - 向剛加入的玩家發送初始 `StackSizeLimitSyncPayload`。
3. **遊戲內規則修改 (`MinecraftServerMixin.onGameRuleChanged`)**：
   - 偵測 `stack-size-adjuster:*` 規則的改動。
   - 更新 `StackSizeManager` 的 `volatile` 變數。
   - 向所有線上玩家廣播更新封包並觸發 `broadcastFullState()`。
