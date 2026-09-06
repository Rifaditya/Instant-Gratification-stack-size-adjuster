# Référence des GameRules dynamiques

## Vue d'ensemble

Stack Size Adjuster enregistre ses règles via le `DynamicGameRuleManager` de **DasikLibrary** sous la catégorie `stack-size-adjuster:stack-size-adjuster`.

---

## 📋 Répertoire des règles de jeu

| Règle de jeu | Type | Valeur par défaut | Intervalle | Description |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ à } 2\,147\,483\,647$ | Limite d'empilement pour les objets empilables par 64. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ à } 2\,147\,483\,647$ | Limite d'empilement pour les objets empilables par 16. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ à } 2\,147\,483\,647$ | Limite d'empilement pour les objets non empilables. |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ à } 64$ | Nombre maximum d'entités par slot lors du bris d'un conteneur. |

---

## 💻 Commandes en jeu

### Consulter une règle
```text
/gamerule stack-size-adjuster:items_64_limit
```

### Modifier une règle
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 Synchronisation bidirectionnelle

Lors de la création ou du chargement d'un monde :
1. `StackSizeConfig` (`config/stack-size-adjuster.json`) ne définit les valeurs que pour les **mondes nouvellement créés**.
2. Dans les **mondes existants**, modifier une règle met à jour `StackSizeManager` en direct.
3. Les modifications déclenchent `MinecraftServerMixin.onGameRuleChanged` :
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
4. Le serveur envoie `StackSizeLimitSyncPayload` à tous les joueurs et exécute `player.containerMenu.broadcastFullState()`.
