# Referência de GameRules dinâmicas

## Visão geral

O Stack Size Adjuster registra regras com namespace através do `DynamicGameRuleManager` da **DasikLibrary** sob a categoria `stack-size-adjuster:stack-size-adjuster`.

---

## 📋 Diretório de GameRules

| Chave da GameRule | Tipo | Padrão | Intervalo de valores | Descrição |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ até } 2.147.483.647$ | Limite de pilha para itens naturalmente empilháveis até 64. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ até } 2.147.483.647$ | Limite de pilha para itens naturalmente empilháveis até 16. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ até } 2.147.483.647$ | Limite de pilha para itens naturalmente não empilháveis. |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ até } 64$ | Máximo de entidades por slot ao quebrar contêineres. |

---

## 💻 Comandos no jogo

### Verificar valor de GameRule
```text
/gamerule stack-size-adjuster:items_64_limit
```

### Alterar valor de GameRule
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 Sincronização de configuração bidirecional

Ao criar ou carregar um mundo:
1. O arquivo `StackSizeConfig` (`config/stack-size-adjuster.json`) define valores apenas para **mundos recém-criados**.
2. Em **mundos existentes**, alterar regras via `/gamerule` atualiza o `StackSizeManager` em tempo real.
3. Modificações acionam `MinecraftServerMixin.onGameRuleChanged`:
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
4. O servidor transmite o pacote `StackSizeLimitSyncPayload` e chama `player.containerMenu.broadcastFullState()`.
