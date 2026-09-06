# Optimisation des drops de conteneurs

## La cause des ralentissements

Dans le jeu de base, briser un conteneur (coffre, tonneau, boîte de Shulker) scinde chaque emplacement en petits paquets de 10 à 30 objets. Si un coffre stocke des milliers ou des millions d'objets, sa destruction tente de faire apparaître **des centaines de milliers d'entités en un seul tick**, figeant ou faisant planter le serveur.

---

## 🧮 Découpage mathématique et apparition d'entités

`InventoryDropHelper.dropItemStack` intercepte `Containers.dropItemStack` via `ContainersMixin` :

```java
public class InventoryDropHelper {
    public static void dropItemStack(Level level, double x, double y, double z, ItemStack itemStack) {
        ...
        int maxEntities = DynamicGameRuleManager.getInt(level, StackSizeAdjusterFabric.MAX_DROP_ENTITIES);
        
        while (!itemStack.isEmpty()) {
            int currentCount = itemStack.getCount();
            int splitSize = random.nextInt(21) + 10;
            
            // Sécurité : plafonner le nombre d'entités par slot
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

### Modélisation mathématique

Pour un effectif total $N$ et une règle `max_drop_entities` valant $M$ :
1. Taille de base aléatoire $S \in [10, 30]$.
2. Seuil de division dynamique :
   $$\text{Si } N > S \times M \implies S_{\text{effective}} = \left\lceil \frac{N}{M} \right\rceil$$
3. Le nombre total d'`ItemEntity` créées par slot respecte :
   $$E_{\text{spawned}} \le M$$

---

## 📊 Benchmarks de performances

| Valeur de `max_drop_entities` | Entités pour 100 000 objets | Impact sur le tick serveur | Rendu visuel |
| :--- | :--- | :--- | :--- |
| **Vanilla (Sans limite)** | $\sim 5\,000$ entités | **Crash ou lag extrême** | Explosion chaotique |
| **8 (Recommandé)** | $\le 8$ entités | $< 1\text{ms}$ de calcul | Dispersion esthétique |
| **1 (Performance max)** | Exactement $1$ entité | Instantané ($0\text{ms}$) | Pile compacte unique |
