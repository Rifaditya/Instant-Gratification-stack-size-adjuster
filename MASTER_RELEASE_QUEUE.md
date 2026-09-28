# 🎛️ Master Release Queue: Instant Gratification — Stack Size Adjuster

> **Mod Project Master Ground-Truth Document**  
> **Modrinth ID**: `k73XOqgj` | **CurseForge ID**: `1599920` | **Lead SemVer**: `1.4.19`

---

## 📊 Multi-Version Release Matrix & Queue Status

| Target MC | Generational Era | Live on Platforms | Next Queued Version | Status & Cadence Action | Feature Highlights / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | Modern Lead | `1.4.18+26.3` | `(Up to date)` | 🟢 **Up to Date** | Snapshot-6 port targeting Fabric Loader 0.19.3. |
| **MC 26.2** | Modern Predecessor | `1.4.18+26.2` | `1.4.19+26.2` | 🟢 **Ready to Publish** | Added `#c:stack_size_exempt` conventional item tag support. |
| **MC 26.1.2** | Modern Predecessor | *(Unreleased)* | `1.4.19+26.1.2` | 🟢 **Ready to Publish** | Parity with 1.4.19+26.2 feature set. |
| **MC 1.21.11** | Legacy Anchor | *(Unreleased)* | `1.0.0+1.21.11` | 🟢 **Ready to Publish** | Ground-zero port with DataComponents unclamp (`1.0.1+1.21.11` queued next). |
| **MC 1.21.1** | Legacy Anchor | *(Unreleased)* | `1.0.0+1.21.1` | 🟢 **Ready to Publish** | Java 21 Loom port with DataComponents unclamp (`1.0.1+1.21.1` queued next). |
| **MC 1.20.1** | Legacy Anchor | *(Unreleased)* | `1.0.0+1.20.1` | 🟢 **Ready to Publish** | Java 17 Ground-zero port (`1.0.1+1.20.1` queued next). |

---

## 🏛️ Project Operating Rules & Architectural Invariants

1. **🏛️ Generational Era Separation**:
   - Modern Stream (`MC 26.x`) and Legacy Stream (`MC 1.20.1`, `MC 1.21.x`) operate with complete release autonomy under the 1 Jar 1 Version Law.
2. **📜 Subproject Changelog & Queue Segregation Law**:
   - Each version anchor subproject directory maintains its own dedicated `CHANGELOG.md` and `RELEASE_QUEUE.md` tracking solely that Minecraft version anchor with Strict Version Anchor Exclusivity.
