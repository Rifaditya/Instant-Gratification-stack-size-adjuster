# Profils de comportement et conditions

## Vue d'ensemble

Stack Size Adjuster orchestre les configurations, règles dynamiques et paquets réseau au travers d'une machine à états robuste.

---

## 🔄 Cycle de vie de la machine à états

```text
               +----------------------------------+
               |  Démarrage serveur / Création    |
               +----------------------------------+
                                |
                                v
               [ Charger modèle de configuration  ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ Enregistrer GameRules dynamiques ]
               (`items_64_limit`, `items_16_limit`, etc.)
                                |
                +---------------+---------------+
                |                               |
          Monde nouveau                   Monde existant
                |                               |
                v                               v
        Appliquer valeurs par            Charger GameRules
        défaut aux règles                depuis `level.dat`
                |                               |
                +---------------+---------------+
                                |
                                v
               [ Initialiser StackSizeManager   ]
                                |
                                v
               [ Connexion joueur / Règle modifiée ]
                                |
                                v
               [ Envoi paquet S2C: sync_limit   ]
                                |
                                v
               [ Actualiser les menus clients   ]
```

---

## ⚙️ Événements et déclencheurs

1. **Démarrage serveur (`ServerLifecycleEvents.SERVER_STARTED`)** :
   - Recharge le fichier modèle.
   - Initialise les règles du monde à partir du fichier pour les nouveaux mondes.
   - Initialise `StackSizeManager`.
2. **Connexion joueur (`ServerPlayConnectionEvents.JOIN`)** :
   - Envoie le paquet `StackSizeLimitSyncPayload`.
3. **Changement de règle (`MinecraftServerMixin.onGameRuleChanged`)** :
   - Détecte les règles `stack-size-adjuster:*`.
   - Met à jour `StackSizeManager`.
   - Notifie tous les clients connectés.
