# Integration mit Item Clumps

## Übersicht

Stack Size Adjuster arbeitet Hand in Hand mit **Item Clumps** (`item_clumps >=1.0.18+26.2`). Während Stack Size Adjuster Inventare und Drops steuert, fasst Item Clumps herumliegende Gegenstände zusammen.

---

## 🤝 Synergie-Ablaufdiagramm

```
[ Behälter-Zerstörungs-Event ]
              |
              v
[ Stack Size Adjuster: InventoryDropHelper ]
Teilt Items in begrenzte Entity-Anzahl auf (z. B. max. 8 pro Slot)
              |
              v
[ Items spawnen auf dem Boden ]
              |
              v
[ Mod Item Clumps: Boden-Zusammenführung ]
Scannt 3,5 Blöcke Radius und vereint Items zu einer einzigen Entity
              |
              v
[ Einzelne Entity mit hoher Anzahl ] (Kein Lag!)
```

---

## 📊 Funktions-Synergiematrix

| Ebene | Zuständig | Vorteil |
| :--- | :--- | :--- |
| **Inventar-Stapelgrenzen** | **Stack Size Adjuster** | Dynamische 64/16/1-Grenzwerte in Kisten und Spielerinventaren |
| **Drop-Generierung** | **Stack Size Adjuster** | Verhindert das Spawnen Tausender Entities bei Kistenbruch |
| **Zusammenfassung am Boden** | **Item Clumps** | Bündelt herumliegende Drops im Radius zu einem Haufen |
| **Aufnahme-Mechanik** | **Minecraft & Mixins** | Nimmt gebündelte Stapel direkt in erweiterte Slots auf |

---

## ⚙️ Empfohlene Konfiguration

Bei gleichzeitiger Nutzung von **Item Clumps** empfiehlt sich `max_drop_entities` auf **8** (oder **1** für Höchstleistung) zu setzen:
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
