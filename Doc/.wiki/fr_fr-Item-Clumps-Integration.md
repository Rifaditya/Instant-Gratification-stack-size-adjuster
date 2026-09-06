# Intégration avec Item Clumps

## Vue d'ensemble

Stack Size Adjuster fonctionne en synergie étroite avec **Item Clumps** (`item_clumps >=1.0.18+26.2`). Alors que Stack Size Adjuster régit les plafonds dans les coffres et lors des drops, Item Clumps regroupe les entités au sol.

---

## 🤝 Schéma de synergie mécanique

```
[ Événement de destruction de conteneur ]
                    |
                    v
[ Stack Size Adjuster: InventoryDropHelper ]
Divise les objets en nombre restreint d'entités (ex. max 8 par slot)
                    |
                    v
[ Entités générées au sol ]
                    |
                    v
[ Mod Item Clumps: Regroupement au sol ]
Scanne un rayon de 3,5 blocs et fusionne les objets en une seule entité
                    |
                    v
[ Entité unique à quantité massive ] (Zéro lag !)
```

---

## 📊 Matrice des responsabilités

| Domaine | Gestionnaire | Rôle |
| :--- | :--- | :--- |
| **Plafonds d'inventaire** | **Stack Size Adjuster** | Applique les plafonds 64/16/1 dans les conteneurs |
| **Génération lors de bris** | **Stack Size Adjuster** | Empêche l'apparition de milliers d'objets d'un coup |
| **Fusion au sol** | **Item Clumps** | Rassemble les entités éparpillées en un point |
| **Ramassage** | **Minecraft & Mixins** | Récupère directement les piles volumineuses |

---

## ⚙️ Configuration recommandée

Avec **Item Clumps**, nous recommandons de régler `max_drop_entities` sur **8** (ou **1** pour les performances ultimes) :
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
