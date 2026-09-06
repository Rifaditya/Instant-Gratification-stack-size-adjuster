# Versionskompatibilitätsmatrix

> 📌 **Haftungsausschluss für Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Stand des Quellcodes im Repository** wider. Sie kann unveröffentlichte Commits oder Entwicklungsfunktionen enthalten, die den offiziellen Veröffentlichungen auf CurseForge und Modrinth voraus sind.

---

## 📊 Kompatibilitätsmatrix

| Minecraft-Ziel | Mod-Version | Build-Status | DasikLibrary-Ziel | Abhängigkeitsgrenzen |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **Aktiv / Aktuell** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ Abhängigkeitsgrenzen & Regeln

Gemäß dem **1 Jar 1 Version**-Gesetz deklariert `fabric.mod.json` offene Untergrenzen:

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

### Richtlinien zur Versionierung:
1. **Keine Alt-Schemata**: Keine Beibehaltung von `1.21.x`-Versionsnummern für 26.x-Releases.
2. **Offene Untergrenzen**: `minecraft >=26.2-` erlaubt nahtlose Kompatibilität mit kleineren Patches.
3. **Classpath-Verifikation**: Beim Laden führt `ModVersionGuard.checkClass` Integritätsprüfungen durch.

---

## 📦 Verifizierte historische Archive

Alle bisherigen Releases sind im Verzeichnis `Archive Jar of all versions/` dauerhaft archiviert:

- `stack-size-adjuster-1.4.16+26.2.jar` (Aktueller Release)
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar` (Erster 26.2-Release)
