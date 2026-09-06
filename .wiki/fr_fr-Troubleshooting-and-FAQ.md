# Dépannage et FAQ

> 📌 **Avertissement sur le code source du dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en cours de développement en avance sur les versions publiques de CurseForge et Modrinth.

---

## ❓ Foire aux questions

### Q1 : Pourquoi les mondes existants n'adoptent-ils pas les limites de `config/stack-size-adjuster.json` ?
**R** : Le fichier global (`stack-size-adjuster.json`) ne sert de modèle que pour les **mondes nouvellement créés**. Dans les mondes existants, les valeurs résident dans `level.dat`. Utilisez `/gamerule` en jeu :
```text
/gamerule stack-size-adjuster:items_64_limit 256
```

### Q2 : Que se passe-t-il si j'indique une limite supérieure à 39 768 215 ?
**R** : Dépasser $39\,768\,215$ expose à un **débordement d'entier dans les grands coffres**. Un coffre de 54 slots plein dépassera la limite de $2\,147\,483\,647$, causant des totaux négatifs et la suppression d'objets lors de déplacements. Nous recommandons vivement $\le 39\,768\,215$.

### Q3 : Pourquoi les coffres brisés ne lâchent-ils que 8 piles d'objets ?
**R** : C'est une optimisation contrôlée par `stack-size-adjuster:max_drop_entities` (défaut : `8`). Associé à **Item Clumps**, ces 8 piles fusionnent immédiatement en une seule.

---

## 🛠️ Diagnostic des incidents courants

| Symptôme | Origine | Solution |
| :--- | :--- | :--- |
| **L'inventaire affiche les limites vanilla** | Échec du paquet réseau ou version différente | Vérifiez que le mod est présent sur le **client** et le **serveur**. |
| **La commande Give indique "trop d'objets"** | `/give` excède $100 \times \text{maxStackSize}$ | Demandez moins d'objets à la fois ou fractionnez vos commandes. |
| **L'écran YACL est inaccessible dans ModMenu** | ModMenu ou YACL est absent | Installez **ModMenu** et **YetAnotherConfigLib (YACL v3)** sur le client. |
