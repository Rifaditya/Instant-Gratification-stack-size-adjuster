# Справочник динамических игровых правил

## Обзор

Stack Size Adjuster регистрирует правила с пространствами имен через `DynamicGameRuleManager` из **DasikLibrary**. Все правила принадлежат категории `stack-size-adjuster:stack-size-adjuster`.

---

## 📋 Список игровых правил

| Ключ игрового правила | Тип | По умолчанию | Диапазон значений | Описание |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | от $1$ до $2,147,483,647$ | Лимит стака для предметов, естественно стакуемых до 64. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | от $1$ до $2,147,483,647$ | Лимит стака для предметов, естественно стакуемых до 16. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | от $1$ до $2,147,483,647$ | Лимит стака для естественно нестакуемых предметов. |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | от $1$ до $64$ | Максимум сущностей на слот при разрушении контейнера. |

---

## 💻 Команды в игре

### Просмотр значения правила
```text
/gamerule stack-size-adjuster:items_64_limit
```

### Изменение значения правила
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 Двусторонняя синхронизация конфигурации

При генерации или загрузке мира:
1. `StackSizeConfig` (глобальный `config/stack-size-adjuster.json`) задает базовые значения только для **вновь создаваемых миров**.
2. В **существующих мирах** изменение правил через команду `/gamerule` или графический интерфейс мгновенно обновляет `StackSizeManager`.
3. Изменение правила вызывает хук `MinecraftServerMixin.onGameRuleChanged`:
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
4. Сервер автоматически рассылает пакет `StackSizeLimitSyncPayload` всем игрокам и вызывает `player.containerMenu.broadcastFullState()`.
