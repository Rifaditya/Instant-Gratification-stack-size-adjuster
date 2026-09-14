# Architecture & Symbol Index: Stack Size Adjuster

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `stack-size-adjuster`
- **Main Entrypoint**: `net.instantgratification.stacksizeadjuster.StackSizeAdjusterFabric` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `net.instantgratification.stacksizeadjuster.StackSizeAdjusterFabricClient`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.ItemInstanceMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.ItemMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.MinecraftServerMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.ItemStackMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.ContainerMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.DataComponentsMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.ItemStackTemplateMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.ContainersMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.AbstractContainerMenuMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.stacksizeadjuster.mixin.GiveCommandMixin` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`stack-size-adjuster:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
