# Version Compatibility Matrix

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 📊 Multi-Era Compatibility Matrix

| Minecraft Target | Mod Version | Build Status | Java Target | Loader Target | Dependencies & Bounds |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | `1.4.19+26.3` | **Active / Current** | `Java 25` | `>=0.19.1` | `fabric-api`, `item_clumps (>=1.0.18+26.2)` |
| **MC 26.2** | `1.4.19+26.2` | **Active / Current** | `Java 25` | `>=0.19.1` | `fabric-api`, `item_clumps (>=1.0.18+26.2)` |
| **MC 26.1.2** | `1.4.19+26.1.2` | **Active / Current** | `Java 25` | `>=0.19.1` | `fabric-api` (item_clumps optional/suggested) |
| **MC 1.21.11** | `1.0.0+1.21.11` | **Active / Current** | `Java 21` | `>=0.18.4` | `fabric-api` (item_clumps optional/suggested) |
| **MC 1.21.1** | `1.0.0+1.21.1` | **Active / Current** | `Java 21` | `>=0.16.0` | `fabric-api` (item_clumps optional/suggested) |
| **MC 1.20.1** | `1.0.0+1.20.1` | **Active / Current** | `Java 17` | `>=0.16.0` | `fabric-api` (item_clumps optional/suggested) |

---

## 🛡️ "1 Jar 1 Version" Policy & Dependency Bounds

Under the **1 Jar 1 Version** policy:
- We compile dedicated binaries per targeted Minecraft version (e.g. `MC 26.3`, `MC 26.2`, `MC 26.1.2`, `MC 1.21.11`, `MC 1.21.1`, and `MC 1.20.1`).
- Please download the exact build matching your Minecraft installation.

### Java Toolchain Architecture by Era

1. **Modern 26.x Era (MC 26.1.2, 26.2, 26.3)**:
   - **Java Target**: Java 25.
   - **Architecture**: `DataComponents` unclamp, `ItemInstance`, plural `EntityTypes`, Mojang unmapped names.
2. **Intermediate 1.21.x Era (MC 1.21.1, 1.21.11)**:
   - **Java Target**: Java 21.
   - **Architecture**: `DataComponents.MAX_STACK_SIZE` codec unclamp, `CustomPacketPayload` records with `StreamCodec`, singular `EntityType.ITEM`.
3. **Legacy 1.20.1 Era (MC 1.20.1)**:
   - **Java Target**: Java 17.
   - **Architecture**: Zero `DataComponents`. Direct method injection on `Item.getMaxStackSize()` and `ItemStack.getMaxStackSize()`, classic `PacketByteBuf` networking via `ServerPlayNetworking` / `ClientPlayNetworking`.

---

## 📦 Verified Historical Archives

All compiled historical release binaries are stored permanently in the repository's `Archive Jar of all versions/` directory:

- `Archive Jar of all versions/MC 26.3/stack-size-adjuster-1.4.19+26.3.jar`
- `Archive Jar of all versions/MC 26.2/stack-size-adjuster-1.4.19+26.2.jar`
- `Archive Jar of all versions/MC 26.1.2/stack-size-adjuster-1.4.19+26.1.2.jar`
- `Archive Jar of all versions/MC 1.21.11/stack-size-adjuster-1.0.0+1.21.11.jar`
- `Archive Jar of all versions/MC 1.21.1/stack-size-adjuster-1.0.0+1.21.1.jar`
- `Archive Jar of all versions/MC 1.20.1/stack-size-adjuster-1.0.0+1.20.1.jar`
