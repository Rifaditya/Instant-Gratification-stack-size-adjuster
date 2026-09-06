# 動態遊戲規則參考

## 概述

Stack Size Adjuster 透過 **DasikLibrary** 的 `DynamicGameRuleManager` 註冊帶命名空間的遊戲規則。所有規則歸屬於自訂類別 `stack-size-adjuster:stack-size-adjuster`。

---

## 📋 遊戲規則目錄

| 遊戲規則鍵名 | 資料類型 | 預設值 | 取值範圍 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ 至 } 2,147,483,647$ | 原本自然堆疊至 64 的物品堆疊上限。 |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ 至 } 2,147,483,647$ | 原本自然堆疊至 16 的物品堆疊上限。 |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ 至 } 2,147,483,647$ | 原本不可堆疊物品（單物品）的堆疊上限。 |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ 至 } 64$ | 破壞容器時每個槽位生成的最大掉落物實體數。 |

---

## 💻 遊戲內指令

### 查看遊戲規則目前值
```text
/gamerule stack-size-adjuster:items_64_limit
```

### 修改遊戲規則值
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 雙向設定同步機制

當世界生成或載入時：
1. `StackSizeConfig`（全域設定檔 `config/stack-size-adjuster.json`）僅為**新建立的世界**提供預設範本初始值。
2. 對於**已存在的存檔**，透過 `/gamerule` 指令或遊戲規則介面調整會即時更新 `StackSizeManager`。
3. 修改遊戲規則會觸發 `MinecraftServerMixin.onGameRuleChanged`：
   ```java
   @Inject(method = "onGameRuleChanged", at = @At("TAIL"))
   private <T> void onGameRuleChanged(GameRule<T> rule, T value, CallbackInfo ci) {
       Identifier ruleId = rule.getIdentifier();
       if (ruleId != null && ruleId.getNamespace().equals("stack-size-adjuster")) {
           if (value instanceof Integer intVal) {
               StackSizeManager.setLimit(ruleId.getPath(), intVal, (MinecraftServer) (Object) this);
           }
       }
   }
   ```
4. 伺服端自動向所有線上玩家廣播 `StackSizeLimitSyncPayload` 並呼叫 `player.containerMenu.broadcastFullState()` 強制重新整理介面。
