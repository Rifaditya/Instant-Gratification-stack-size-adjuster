# Dynamische GameRules Referenz

## Übersicht

Stack Size Adjuster registriert GameRules über den `DynamicGameRuleManager` der **DasikLibrary**. Alle Regeln gehören zur Kategorie `stack-size-adjuster:stack-size-adjuster`.

---

## 📋 GameRule-Verzeichnis

| GameRule-Schlüssel | Typ | Standard | Wertebereich | Beschreibung |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ bis } 2.147.483.647$ | Stapelgrenze für Gegenstände mit natürlicher 64er-Stapelung. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ bis } 2.147.483.647$ | Stapelgrenze für Gegenstände mit natürlicher 16er-Stapelung. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ bis } 2.147.483.647$ | Stapelgrenze für unstapelbare Gegenstände (Einzel-Items). |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ bis } 64$ | Maximale Drop-Entities pro Slot beim Zerstören von Behältern. |

---

## 💻 Befehle im Spiel

### GameRule-Wert anzeigen
```text
/gamerule stack-size-adjuster:items_64_limit
```

### GameRule-Wert ändern
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 Zwei-Wege-Konfigurationssynchronisation

Beim Generieren oder Laden einer Welt:
1. `StackSizeConfig` (`config/stack-size-adjuster.json`) setzt Standardwerte ausschließlich für **neu erstellte Welten**.
2. In **bestehenden Welten** werden Änderungen über `/gamerule` oder das Menü sofort in `StackSizeManager` übernommen.
3. Änderungen lösen `MinecraftServerMixin.onGameRuleChanged` aus:
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
4. Der Server sendet `StackSizeLimitSyncPayload` an alle Spieler und ruft `player.containerMenu.broadcastFullState()` auf.
