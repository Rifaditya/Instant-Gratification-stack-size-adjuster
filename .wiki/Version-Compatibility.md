# Version Compatibility Matrix

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 📊 Compatibility Matrix

| Minecraft Target | Mod Version | Build Status | DasikLibrary Target | Dependency Bounds |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **Active / Current** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ Dependency Bounds & Rules

Under the **1 Jar 1 Version** law, dependency entries in `fabric.mod.json` enforce open-ended lower bounds:

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

### Version Identity Rules:
1. **Zero Legacy Schemes**: No `1.21.x` version numbers are retained for Minecraft 26.x releases.
2. **Open-Ended Dependency Lower Bounds**: `minecraft >=26.2-` permits patch release compatibility without pre-release locks.
3. **Classpath Verification**: Mod initialization executes `ModVersionGuard.checkClass` using `Thread.currentThread().getContextClassLoader()`.

---

## 📦 Verified Historical Archives

All compiled historical release binaries are stored permanently in the repository's `Archive Jar of all versions/` directory.

- `stack-size-adjuster-1.4.16+26.2.jar` (Current Release)
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar` (Initial 26.2 Release)
