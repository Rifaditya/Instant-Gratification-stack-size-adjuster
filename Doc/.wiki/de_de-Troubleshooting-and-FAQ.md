# Fehlerbehebung & FAQ

> 📌 **Haftungsausschluss für Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Stand des Quellcodes im Repository** wider. Sie kann unveröffentlichte Commits oder Entwicklungsfunktionen enthalten, die den offiziellen Veröffentlichungen auf CurseForge und Modrinth voraus sind.

---

## ❓ Häufig gestellte Fragen

### F1: Warum übernehmen bestehende Welten nicht die Werte aus `config/stack-size-adjuster.json`?
**A**: Die Konfigurationsdatei definiert Standardwerte ausschließlich für **neu erstellte Welten**. In bestehenden Welten sind Regeln in `level.dat` gespeichert. Nutzen Sie ingame `/gamerule`:
```text
/gamerule stack-size-adjuster:items_64_limit 256
```

### F2: Was passiert bei Werten über 39.768.215?
**A**: Werte über $39.768.215$ bergen das Risiko eines **Ganzzahl-Überlaufs in großen Truhen**. Eine volle 54-Slot-Kiste übersteigt das 32-Bit-Limit ($2.147.483.647$), was zu negativem Zählerstand und gelöschten Gegenständen führt. Werte $\le 39.768.215$ werden dringend angeraten.

### F3: Warum droppen beim Kistenabbau nur 8 Haufen statt Tausender Items?
**A**: Dies ist die Lag-Prävention durch `stack-size-adjuster:max_drop_entities` (Standard: `8`). Zusammen mit **Item Clumps** verschmelzen diese 8 Haufen sofort zu einem einzigen.

---

## 🛠️ Problemdiagnose

| Symptom | Ursache | Lösung |
| :--- | :--- | :--- |
| **Inventar zeigt Vanilla-Limits** | Netzwerk-Sync fehlgeschlagen oder Versionsunterschied | Sicherstellen, dass die Mod auf Client **und** Server installiert ist. |
| **Give-Befehl meldet "zu viele Items"** | `/give` übersteigt $100 \times \text{maxStackSize}$ | Weniger Items auf einmal anfordern oder aufteilen. |
| **YACL-Bildschirm öffnet sich nicht** | ModMenu oder YACL fehlen | **ModMenu** und **YetAnotherConfigLib (YACL v3)** auf dem Client installieren. |
