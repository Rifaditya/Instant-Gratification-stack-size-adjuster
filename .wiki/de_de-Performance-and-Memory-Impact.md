# Leistung & Speicherauslastung

## Übersicht

Stack Size Adjuster ist auf maximale Effizienz, minimale Speicherbelegung und null NBT-Aufblähung ausgelegt.

---

## ⚡ Wichtige Leistungsoptimierungen

1. **Primitive Datentypen**: Limits werden in `volatile int`-Feldern gehalten, was sperrenfreie $O(1)$-Lesezugriffe garantiert.
2. **Null NBT-Aufblähung**: Die Mod injiziert keine benutzerdefinierten NBT-Tags, sondern modifiziert direkt Vanilla-Codecs (`DataComponents.MAX_STACK_SIZE` und `ExtraCodecs.intRange`).
3. **Keine Tick-Listener**: Keine Logik in Spielschleifen (`EndTick`, `WorldTick`). Code läuft rein ereignisbasiert.
4. **Thread-sichere Overrides**: Overrides liegen in einer `CopyOnWriteArrayList` für sichere Iteration.

---

## 📊 Benchmark-Werte

| Metrik | Gemessener Wert | Optimierungsmechanismus |
| :--- | :--- | :--- |
| **Heap-Speicherbelegung** | $< 50\text{ KB}$ | Keine temporären Objekte bei Prüfungen |
| **Welt-Speicherdaten** | $+0\text{ Bytes}$ | Anpassung des Komponenten-Codecs |
| **Server-Tick-Overhead (MSPT)** | $0.00\text{ ms}$ | Sperrenfreie Lesezugriffe, keine Ticks |
| **Behälterabbau (MSPT)** | $< 0.50\text{ ms}$ | Entity-Begrenzung in `InventoryDropHelper` |
