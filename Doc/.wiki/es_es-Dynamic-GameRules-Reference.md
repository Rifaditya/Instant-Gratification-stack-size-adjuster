# Referencia de GameRules dinámicas

## Resumen

Stack Size Adjuster registra GameRules con espacio de nombres a través de `DynamicGameRuleManager` de **DasikLibrary**. Todas las reglas pertenecen a la categoría personalizada `stack-size-adjuster:stack-size-adjuster`.

---

## 📋 Directorio de GameRules

| Clave de GameRule | Tipo | Predeterminado | Rango de valores | Descripción |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ a } 2,147,483,647$ | Límite de apilamiento para objetos naturalmente de 64. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ a } 2,147,483,647$ | Límite de apilamiento para objetos naturalmente de 16. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ a } 2,147,483,647$ | Límite de apilamiento para objetos no apilables. |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ a } 64$ | Máximo de entidades generadas por ranura al romper contenedores. |

---

## 💻 Comandos en el juego

### Ver valor de GameRule
```text
/gamerule stack-size-adjuster:items_64_limit
```

### Modificar valor de GameRule
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 Sincronización de configuración bidireccional

Cuando se crea o se carga un mundo:
1. `StackSizeConfig` (`config/stack-size-adjuster.json`) establece los valores predeterminados solo para **mundos recién creados**.
2. Para **mundos existentes**, los cambios en las GameRules actualizan `StackSizeManager` en tiempo real.
3. Modificar una GameRule activa `MinecraftServerMixin.onGameRuleChanged`:
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
4. El servidor transmite automáticamente `StackSizeLimitSyncPayload` a todos los jugadores y llama a `player.containerMenu.broadcastFullState()`.
