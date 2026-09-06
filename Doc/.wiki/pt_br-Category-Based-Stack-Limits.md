# Limites de empilhamento por categoria

## Visão geral do sistema

O Minecraft organiza naturalmente os itens em três categorias através de `DataComponents.MAX_STACK_SIZE`:
1. **Empilháveis até 64**: Blocos de construção, minérios, itens comuns (pedregulho, terra, barras de ferro).
2. **Empilháveis até 16**: Pérolas do Ender, bolas de neve, baldes, ovos, placas.
3. **Não empilháveis (1 por pilha)**: Ferramentas, armas, armaduras, poções, selas, carrinhos.

---

## 🧮 Fluxograma de determinação do tamanho da pilha

```
                 +--------------------------------+
                 |    Cálculo de pilha do item    |
                 +--------------------------------+
                                  |
                                  v
                  [ Verificar sobreposições ativas ]
                  (ex: Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
        Sobreposição ativa?              Sem sobreposição
                 |                                 |
                 v                                 v
        Retornar limite custom          Checar valor original
                                           (DataComponents)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                Original >= 64                Original >= 16                Original == 1
                     |                             |                             |
                     v                             v                             v
           Retornar `items_64_limit`     Retornar `items_16_limit`     Retornar `items_1_limit`
```

---

## 💻 Lógica em Java

O dimensionamento de pilhas é tratado em `StackSizeManager.getModifiedStackSize`:

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // Aplica sobreposições registradas por addons (ex: Potion Stacker)
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ Recomendações de limites

| Categoria | Padrão | Máximo recomendado | Perfil de desempenho |
| :--- | :--- | :--- | :--- |
| **Empilháveis até 64** | `128` | $39.768.215$ | Altíssima eficiência. Milhões de itens sem travamentos. |
| **Empilháveis até 16** | `32` | $39.768.215$ | Excelente dimensionamento para pérolas e ovos. |
| **Não empilháveis** | `1` | $39.768.215$ | Permite empilhar poções e ferramentas. |
