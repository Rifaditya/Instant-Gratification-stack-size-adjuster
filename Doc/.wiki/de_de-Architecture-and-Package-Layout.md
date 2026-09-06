# Architektur & Paketstruktur

## "1 Datei, 1 Zweck"-Architektur

Stack Size Adjuster folgt konsequent dem Prinzip klarer Zuständigkeiten. Jede Klasse erfüllt eine festgelegte Aufgabe: Netzwerksynchronisation, Konfiguration, Drop-Steuerung oder Rendering.

---

## 🌳 ASCII-Pakethierarchie

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Fabric-Haupteinstiegspunkt & GameRules
├── StackSizeAdjusterFabricClient.java # Client-Einstiegspunkt & S2C-Paketempfänger
├── config/
│   ├── ModMenuIntegration.java        # ModMenu API-Integration
│   ├── StackSizeConfig.java           # JSON-Konfigurationscontainer (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # YACL 3.9.5 Screen Builder via Reflection
├── mixin/
│   ├── AbstractContainerMenuMixin.java# Quick-Crafting mit doppelter Genauigkeit
│   ├── ContainerMixin.java            # Slot-Stapellimit-Override (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Abfangen von Containers.dropItemStack
│   ├── DataComponentsMixin.java       # DataComponents MAX_STACK_SIZE Codec-Erweiterung
│   ├── GiveCommandMixin.java          # Abfangen des /give-Befehls
│   ├── ItemInstanceMixin.java         # Hook in ItemInstance.getMaxStackSize
│   ├── ItemMixin.java                 # Hook in Item.getDefaultMaxStackSize
│   ├── ItemStackMixin.java            # Redirect für ItemStack.getMaxStackSize
│   ├── ItemStackTemplateMixin.java    # Redirect für ItemStackTemplate-Codec
│   ├── MinecraftServerMixin.java      # Listener für Server-GameRule-Änderungen
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # Skaliertes Item-Count-Rendering
├── network/
│   └── StackSizeLimitSyncPayload.java # S2C Payload Record (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # /give-Verteilung mit 100x Sicherheitsfaktor
    ├── InventoryDropHelper.java       # Kontrollierte Drop-Aufteilung bei Behälterbruch
    ├── ItemCountRenderer.java         # Dynamischer Matrix-Skalierungs-Renderer
    ├── ModVersionGuard.java           # Laufzeit-Klassenprüfung
    └── StackSizeManager.java          # Thread-sicherer Limit-Manager & Override-Registry
```

---

## 🔒 Threading- & Nebenläufigkeitsmodell

* **Lesezugriffe**: `StackSizeManager.getModifiedStackSize` ist sperrenfrei und thread-sicher über `volatile int`-Felder und eine `CopyOnWriteArrayList`.
* **Server-zu-Client-Sync**: Pakete werden im Hauptthread des Servers gesendet und am Client via `context.client().execute(...)` verarbeitet.
