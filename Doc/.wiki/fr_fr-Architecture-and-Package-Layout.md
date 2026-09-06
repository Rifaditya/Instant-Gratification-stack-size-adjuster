# Architecture et structure des packages

## Principe d'un fichier, un but (1 File, 1 Purpose)

Stack Size Adjuster suit rigoureusement le principe de responsabilité unique. Chaque classe est consacrée à un sous-système dédié : synchronisation, configuration, gestion des drops ou affichage.

---

## 🌳 Arborescence des packages

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Point d'entrée Fabric & enregistrement des GameRules
├── StackSizeAdjusterFabricClient.java # Point d'entrée client & récepteur des paquets S2C
├── config/
│   ├── ModMenuIntegration.java        # Intégration API ModMenu
│   ├── StackSizeConfig.java           # Conteneur JSON (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # Générateur d'écran YACL 3.9.5 réflexif
├── mixin/
│   ├── AbstractContainerMenuMixin.java# Calcul de craft rapide en double précision
│   ├── ContainerMixin.java            # Remplacement de limite de slot (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Interception de Containers.dropItemStack
│   ├── DataComponentsMixin.java       # Expansion de codec DataComponents MAX_STACK_SIZE
│   ├── GiveCommandMixin.java          # Interception de la commande /give
│   ├── ItemInstanceMixin.java         # Hook ItemInstance.getMaxStackSize
│   ├── ItemMixin.java                 # Hook Item.getDefaultMaxStackSize
│   ├── ItemStackMixin.java            # Redirection de plage ItemStack.getMaxStackSize
│   ├── ItemStackTemplateMixin.java    # Redirection de codec ItemStackTemplate
│   ├── MinecraftServerMixin.java      # Écouteur de modifications des GameRules serveur
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # Remplacement de l'affichage du compteur d'items
├── network/
│   └── StackSizeLimitSyncPayload.java # Record de paquet S2C (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # Distribution /give avec multiplicateur sécurisé 100x
    ├── InventoryDropHelper.java       # Découpage maîtrisé des drops de conteneurs
    ├── ItemCountRenderer.java         # Rendu avec mise à l'échelle matricielle de police
    ├── ModVersionGuard.java           # Validation de compatibilité d'exécution
    └── StackSizeManager.java          # Gestionnaire de limites thread-safe et registre
```

---

## 🔒 Modèle de concurrence et threads

* **Opérations de lecture** : `StackSizeManager.getModifiedStackSize` est sans verrou et thread-safe grâce aux champs `volatile int` et une `CopyOnWriteArrayList`.
* **Synchronisation serveur-client** : Les paquets sont expédiés depuis le thread serveur et traités sur le thread client par `context.client().execute(...)`.
