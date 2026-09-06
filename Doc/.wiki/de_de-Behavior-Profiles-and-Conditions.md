# Verhaltensprofile & Bedingungen

## Übersicht

Stack Size Adjuster arbeitet mit einem Zustandsautomaten, der Weltkonfiguration, dynamische GameRules und Netzwerkpakete über Server- und Clientgrenzen hinweg synchronisiert.

---

## 🔄 Lebenszyklus-Zustandsdiagramm

```text
               +----------------------------------+
               |    Serverstart / Welterstellung  |
               +----------------------------------+
                                |
                                v
               [ Basis-Konfigurationsvorlage laden ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ Dynamische GameRules registrieren ]
               (`items_64_limit`, `items_16_limit` etc.)
                                |
                +---------------+---------------+
                |                               |
          Neu erstellte Welt             Bestehende Welt
                |                               |
                v                               v
        Config-Werte anwenden            GameRules aus
                                        `level.dat` laden
                |                               |
                +---------------+---------------+
                                |
                                v
               [ StackSizeManager initialisieren ]
                                |
                                v
               [ Spieler-Join / GameRule-Änderung ]
                                |
                                v
               [ S2C-Paket sync_limit senden ]
                                |
                                v
               [ Client-Menüzustand aktualisieren ]
```

---

## ⚙️ Ereignisse & Handler

1. **Server-Start (`ServerLifecycleEvents.SERVER_STARTED`)**:
   - Lädt Konfigurationsvorlage.
   - Bei neuen Welten (`!overworldData.isInitialized()`) werden GameRules daraus initialisiert.
   - Initialisiert `StackSizeManager`-Werte.
2. **Spieler verbindet sich (`ServerPlayConnectionEvents.JOIN`)**:
   - Sendet initiales `StackSizeLimitSyncPayload`.
3. **GameRule-Änderung (`MinecraftServerMixin.onGameRuleChanged`)**:
   - Erkennt Änderungen an `stack-size-adjuster:*`.
   - Aktualisiert `StackSizeManager`.
   - Sendet Aktualisierungen an alle Spieler.
