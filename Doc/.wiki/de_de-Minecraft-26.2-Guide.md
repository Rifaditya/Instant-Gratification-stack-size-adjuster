# Minecraft 26.2 Leitfaden

| Attribut | Spezifikation |
| :--- | :--- |
| **Minecraft-Zielversion** | 26.2 (Stabiler Release) |
| **Mod-Version** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*` (Jede mit 26.2 kompatible Version) |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **Bezeichner-Format** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## Übersicht

**Stack Size Adjuster** für Minecraft 26.2 hebt starre Beschränkungen der Stapelgröße auf. Spieler und Administratoren können individuelle Maximalwerte für Gegenstände festlegen, die sich normalerweise auf 64, 16 oder 1 stapeln.

### Hauptmerkmale in 26.2:
1. **Dynamische Kategorieskalierung**: GameRules steuern die Obergrenzen für 64er-, 16er- und Einzelgegenstände/Tränke.
2. **Lag-Schutz bei Behälter-Drops**: Begrenzt das Spawnen von Gegenstands-Entities pro Inventar-Slot (`max_drop_entities`).
3. **Ganzzahl-Überlaufschutz**: Verhindert das Überschreiten des 32-Bit-Limits ($2,14\text{ Mrd.}$) in großen 54-Slot-Truhen durch eine Sicherheitsgrenze von $39.768.215$.
4. **Echtzeit-Synchronisation**: Das S2C-Paket `stack-size-adjuster:sync_limit` aktualisiert Inventare sofort ohne Neuverbindung.

---

## Installation & Einrichtung

1. Sicherstellen, dass **Fabric Loader 0.19.1+** und **Java 25** installiert sind.
2. `stack-size-adjuster-1.4.16+26.2.jar` in das `.minecraft/mods`-Verzeichnis kopieren.
3. Erforderliche Abhängigkeiten installieren:
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. Spiel oder Server starten.

---

## Laufzeit-Versionsüberwachung (ModVersionGuard)

Beim Start validiert `ModVersionGuard` die Umgebung:
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
Bei Inkompatibilitäten stoppt die Initialisierung sicher mit einem aussagekräftigen Log-Eintrag.
