# Dynamic GameRules Reference

## Overview

Stack Size Adjuster registers namespaced GameRules via `DynamicGameRuleManager` from **DasikLibrary**. All rules belong to the custom category `stack-size-adjuster:stack-size-adjuster`.

---

## 📋 GameRule Directory

| GameRule Key | Type | Default | Min / Max Bound | Description |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ to } 2,147,483,647$ | Stack size cap for items naturally stacking to 64. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ to } 2,147,483,647$ | Stack size cap for items naturally stacking to 16. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ to } 2,147,483,647$ | Stack size cap for naturally unstackable items. |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ to } 64$ | Maximum item entities spawned per slot on container break. |

---

## 💻 In-Game Commands

### View GameRule Value
```text
/gamerule stack-size-adjuster:items_64_limit
```

### Modify GameRule Value
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 Two-Way Config Synchronization

When a world is generated or loaded:
1. `StackSizeConfig` (global `config/stack-size-adjuster.json`) sets the default template values for **newly created worlds**.
2. For **existing worlds**, changing GameRules in-game via `/gamerule` or the GameRule GUI updates `StackSizeManager` in real-time.
3. Modifying a GameRule triggers `MinecraftServerMixin.onGameRuleChanged`:
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
4. The server automatically broadcasts `StackSizeLimitSyncPayload` to all online players and calls `player.containerMenu.broadcastFullState()`.
