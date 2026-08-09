# Architecture & Package Layout

## 1 File, 1 Purpose Architecture

Stack Size Adjuster strictly adheres to the **1 File, 1 Function Law**. Each class is focused entirely on a single responsibility: network sync, config handling, item drop handling, or rendering.

---

## 🌳 ASCII Package Hierarchy

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Main Fabric entrypoint & GameRules registration
├── StackSizeAdjusterFabricClient.java # Client entrypoint & S2C network packet receiver
├── config/
│   ├── ModMenuIntegration.java        # ModMenu API integration entrypoint
│   ├── StackSizeConfig.java           # JSON config container (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # YACL 3.9.5 reflective GUI screen builder
├── mixin/
│   ├── AbstractContainerMenuMixin.java# Double-precision quick-crafting calculation
│   ├── ContainerMixin.java            # Max container stack size limit override (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Containers.dropItemStack interception
│   ├── DataComponentsMixin.java       # DataComponents MAX_STACK_SIZE codec expansion
│   ├── GiveCommandMixin.java          # /give command interception
│   ├── ItemInstanceMixin.java         # ItemInstance.getMaxStackSize hook
│   ├── ItemMixin.java                 # Item.getDefaultMaxStackSize hook
│   ├── ItemStackMixin.java            # ItemStack.getMaxStackSize & count range redirect
│   ├── ItemStackTemplateMixin.java    # ItemStackTemplate count range redirect
│   ├── MinecraftServerMixin.java      # Server GameRule modification listener
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # Item count GUI renderer override
├── network/
│   └── StackSizeLimitSyncPayload.java # S2C payload record (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # /give item loop with 100x stack safety multiplier
    ├── InventoryDropHelper.java       # Capped entity split logic on container breaks
    ├── ItemCountRenderer.java         # Dynamic font matrix scale-down renderer
    ├── ModVersionGuard.java           # Runtime class verification check
    └── StackSizeManager.java          # Central thread-safe limit manager & override registry
```

---

## 🔒 Threading & Concurrency Model

* **Read Operations**: `StackSizeManager.getModifiedStackSize` is lock-free and thread-safe via `volatile int` fields and a `CopyOnWriteArrayList` override list.
* **Server-to-Client Sync**: Network packets are dispatched on the main server thread and processed on the client main thread via `context.client().execute(...)`.
