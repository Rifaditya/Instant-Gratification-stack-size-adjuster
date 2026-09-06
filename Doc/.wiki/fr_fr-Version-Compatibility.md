# Matrice de compatibilité des versions

> 📌 **Avertissement sur le code source du dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en cours de développement en avance sur les versions publiques de CurseForge et Modrinth.

---

## 📊 Matrice de compatibilité

| Cible Minecraft | Version du mod | État du build | Cible DasikLibrary | Limites de dépendances |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **Actif / Courant** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ Règles et limites de dépendances

Conformément au principe **1 Jar 1 Version**, `fabric.mod.json` utilise des bornes inférieures ouvertes :

```json
"depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": "${minecraft_dependency}",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": "*",
    "item_clumps": ">=1.0.18+26.2"
}
```

### Règles d'identification :
1. **Zéro schéma hérité** : Aucun numéro `1.21.x` n'est conservé pour les versions 26.x.
2. **Bornes inférieures ouvertes** : `minecraft >=26.2-` garantit la compatibilité directe avec les patchs mineurs.
3. **Vérification du classpath** : Exécution de `ModVersionGuard.checkClass` au chargement.

---

## 📦 Archives historiques vérifiées

Les binaires compilés de toutes les versions publiées sont archivés dans le répertoire `Archive Jar of all versions/` :

- `stack-size-adjuster-1.4.16+26.2.jar` (Version actuelle)
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar` (Première version 26.2)
