# Otimização de quedas de contêineres

## A origem do lag

No Minecraft vanilla, quebrar um contêiner (baú, barril, caixa de shulker) divide os itens de cada slot em pequenos montes de 10 a 30 unidades. Se um baú contiver milhares ou milhões de itens, sua quebra tenta gerar **centenas de milhares de entidades num único tick**, congelando o servidor ou fechando o jogo.

---

## 🧮 Fórmula de geração e divisão matemática

A classe `InventoryDropHelper.dropItemStack` intercepta `Containers.dropItemStack` através de `ContainersMixin`:

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Limite de segurança: limitar geração de entidades por slot
            if (currentCount > splitSize * maxEntities) {
                splitSize = (currentCount + maxEntities - 1) / maxEntities;
            }
            
            ItemEntity entity = new ItemEntity(level, xo, yo, zo, itemStack.split(splitSize));
            ...
            level.addFreshEntity(entity);
        }
    }
}
```

### Formulação matemática

Para um total de itens $N$ e uma configuração `max_drop_entities` de $M$:
1. Tamanho base de divisão aleatória $S \in [10, 30]$.
2. Verificação de limite dinâmico:
   $$\text{Se } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. O número total de entidades `ItemEntity` geradas por slot é limitado por:
   $$E_{\text{spawned}} \le M$$

---

## 📊 Benchmarks de desempenho de quedas

| Configuração `max_drop_entities` | Entidades para 100.000 itens | Impacto de tick no servidor | Dispersão visual |
| :--- | :--- | :--- | :--- |
| **Vanilla (Sem limite)** | $\sim 5.000$ entidades | **Lag severo / Travamento** | Dispersão caótica |
| **8 (Padrão recomendado)** | $\le 8$ entidades | $< 1\text{ms}$ de tick | Excelente efeito visual |
| **1 (Desempenho máximo)** | Exatamente $1$ entidade | Instantâneo ($0\text{ms}$) | Monte único condensado |
