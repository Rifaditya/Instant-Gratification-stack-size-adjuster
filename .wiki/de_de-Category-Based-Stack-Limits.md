# Kategoriebasierte Stapelgrenzen

## Systemübersicht

Minecraft teilt Gegenstände über `DataComponents.MAX_STACK_SIZE` in drei primäre Kategorien ein:
1. **64er-Stapel**: Baublöcke, Erze, Standard-Gegenstände (Bruchstein, Erde, Eisenbarren).
2. **16er-Stapel**: Enderperlen, Schneebälle, Eimer, Eier, Schilder.
3. **1er-Stapel (nicht stapelbar)**: Werkzeuge, Waffen, Rüstungen, Tränke, Sättel, Loren.

---

## 🧮 Ablaufdiagramm zur Stapelgrößen-Ermittlung

```
                 +--------------------------------+
                 |    Stapelgrößen-Berechnung     |
                 +--------------------------------+
                                  |
                                  v
                  [ Registrierte Overrides prüfen ]
                  (z.B. Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
           Override gefunden?                 Kein Override
                 |                                 |
                 v                                 v
        Eigenes Limit zurück           Natürlichen Vanilla-Wert
                                        (DataComponents) prüfen
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                Natürlich >= 64               Natürlich >= 16               Natürlich == 1
                     |                             |                             |
                     v                             v                             v
           `items_64_limit` zurück       `items_16_limit` zurück       `items_1_limit` zurück
```

---

## 💻 Java-Berechnungslogik

Die Berechnung erfolgt über `StackSizeManager.getModifiedStackSize`:

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // Registrierte Overrides von Addons anwenden (z. B. Potion Stacker)
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ Empfehlungen für Kategoriegrenzen

| Kategorie | Standard | Empfohlenes Maximum | Performance-Profil |
| :--- | :--- | :--- | :--- |
| **64er-Stapel** | `128` | $39.768.215$ | Sehr effizient. Millionen-Limits laufen flüssig. |
| **16er-Stapel** | `32` | $39.768.215$ | Reibungslose Skalierung für Perlen und Eier. |
| **1er-Stapel** | `1` | $39.768.215$ | Erlaubt Stapeln von Werkzeugen/Tränken. |
