# Protection contre le débordement des grands coffres

## Le problème du débordement d'entier

En Java et Minecraft, le calcul des quantités utilise des entiers signés de 32 bits (`int`). La valeur maximale possible est :
$$I_{\max} = 2^{31} - 1 = 2\,147\,483\,647$$

Dans un **grand coffre** (54 emplacements) rempli de piles massives, calculer la somme totale des objets additionne les 54 cases.

---

## 🧮 Calcul du seuil de sécurité

Si chaque case du grand coffre contient une pile de taille $L$ :
$$\text{Total dans le coffre} = 54 \times L$$

Pour éviter d'excéder $I_{\max}$ :
$$54 \times L \le 2\,147\,483\,647$$
$$L \le \frac{2\,147\,483\,647}{54} \approx 39\,768\,215,68$$

Le **seuil de sécurité absolue** est donc :
$$L_{\text{safe}} = 39\,768\,215$$

### Tableau d'impact des débordements

| Limite de pile ($L$) | Total coffre 54 slots | Statut de l'entier | Risque encouru |
| :--- | :--- | :--- | :--- |
| **128 (Défaut)** | $6\,912$ | Sécurisé ($< 2,14\text{ Md}$) | Aucun risque |
| **1 000 000** | $54\,000\,000$ | Sécurisé ($< 2,14\text{ Md}$) | Aucun risque |
| **39 768 215** | $2\,147\,483\,610$ | Sécurisé ($< 2,14\text{ Md}$) | **Plafond maximal recommandé** |
| **40 000 000** | $2\,160\,000\,000$ | **Débordement !** (Devient $-2\,134\,967\,296$) | Perte d'objets / Conteneur corrompu |

---

## 🛠️ Craft rapide en double précision

Dans `AbstractContainerMenuMixin`, le calcul de division lors du glisser-déposer rapide est converti en calcul flottant 64 bits (`double`) pour prévenir les nombres négatifs :

```java
@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @Overwrite
    public static int getQuickCraftPlaceCount(int quickCraftSlotsSize, int quickCraftingType, ItemStack itemStack) {
        return switch (quickCraftingType) {
            case 0 -> (int) ((double) itemStack.getCount() / (double) quickCraftSlotsSize);
            case 1 -> 1;
            case 2 -> itemStack.getMaxStackSize();
            default -> itemStack.getCount();
        };
    }
}
```
