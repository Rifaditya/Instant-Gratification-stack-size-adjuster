# 动态游戏规则参考

## 概述

Stack Size Adjuster 通过 **DasikLibrary** 的 `DynamicGameRuleManager` 注册带命名空间的游戏规则。所有规则归属于自定义类别 `stack-size-adjuster:stack-size-adjuster`。

---

## 📋 游戏规则目录

| 游戏规则键名 | 数据类型 | 默认值 | 取值范围 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ 到 } 2,147,483,647$ | 原本自然堆叠至 64 的物品堆叠上限。 |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ 到 } 2,147,483,647$ | 原本自然堆叠至 16 的物品堆叠上限。 |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ 到 } 2,147,483,647$ | 原本不可堆叠物品（单物品）的堆叠上限。 |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ 到 } 64$ | 破坏容器时每个槽位生成的最大掉落物实体数。 |

---

## 💻 游戏内命令

### 查看游戏规则当前值
```text
/gamerule stack-size-adjuster:items_64_limit
```

### 修改游戏规则值
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 双向配置同步机制

当世界生成或加载时：
1. `StackSizeConfig`（全局配置文件 `config/stack-size-adjuster.json`）仅为**新创建的世界**提供默认模板初始值。
2. 对于**已存在的存档**，通过 `/gamerule` 命令或游戏规则界面调整会实时更新 `StackSizeManager`。
3. 修改游戏规则会触发 `MinecraftServerMixin.onGameRuleChanged`：
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
4. 服务端自动向所有在线玩家广播 `StackSizeLimitSyncPayload` 并调用 `player.containerMenu.broadcastFullState()` 强制刷新界面。
