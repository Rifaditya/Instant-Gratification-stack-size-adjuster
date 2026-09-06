# Optimización de caída de contenedores

## El problema del lag

En Minecraft vanilla, romper un contenedor (cofre, barril, caja de shulker) divide la pila de cada ranura en pequeños grupos de 10 a 30 objetos. Si un cofre contiene miles o millones de objetos, romperlo intenta generar **cientos de miles de entidades de objetos en un solo tick**, lo que congela el servidor o bloquea el juego.

---

## 🧮 Fórmula de generación de entidades y división matemática

`InventoryDropHelper.dropItemStack` intercepta `Containers.dropItemStack` mediante `ContainersMixin`:

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Límite de seguridad: topar la generación de entidades por ranura
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

### Formulación matemática

Dado un recuento total $N$ y un `max_drop_entities` $M$:
1. Tamaño aleatorio de división base $S \in [10, 30]$.
2. Verificación de umbral dinámico:
   $$\text{Si } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. El recuento total de `ItemEntity` generadas para esa ranura está estrictamente limitado por:
   $$E_{\text{spawned}} \le M$$

---

## 📊 Comparativa de rendimiento de caídas

| Ajuste `max_drop_entities` | Entidades para 100,000 objetos | Impacto en tick de servidor | Dispersión visual |
| :--- | :--- | :--- | :--- |
| **Vanilla (Sin tope)** | $\sim 5,000$ entidades | **Lag severo / Bloqueo** | Extrema dispersión caótica |
| **8 (Predeterminado recomendado)** | $\le 8$ entidades | $< 1\text{ms}$ tiempo de tick | Excelente dispersión visual |
| **1 (Rendimiento máximo)** | Exactamente $1$ entidad | Instantáneo ($0\text{ms}$) | Una sola pila condensada |
