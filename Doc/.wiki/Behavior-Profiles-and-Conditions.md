# Behavior Profiles & Conditions

## Overview

Stack Size Adjuster operates under a state machine that bridges world configuration, dynamic GameRules, and network packets across server and client boundaries.

---

## 🔄 State Machine Lifecycle Diagram

```text
               +----------------------------------+
               |  Server Startup / World Creation |
               +----------------------------------+
                                |
                                v
               [ Load Baseline Config Template ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ Register Dynamic GameRules ]
               (`items_64_limit`, `items_16_limit`, etc.)
                                |
                +---------------+---------------+
                |                               |
        Newly Created World             Existing Loaded World
                |                               |
                v                               v
    Apply Config Defaults to Rules      Load GameRules from `level.dat`
                |                               |
                +---------------+---------------+
                                |
                                v
              [ Initialize Active StackSizeManager ]
                                |
                                v
              [ Player Join Event / GameRule Edit ]
                                |
                                v
             [ Dispatch S2C Packet: sync_limit ]
                                |
                                v
             [ Force Client Menu State Refresh ]
```

---

## ⚙️ Trigger Events & Handlers

1. **Server Initialization (`ServerLifecycleEvents.SERVER_STARTED`)**:
   - Reloads baseline config file.
   - If world is newly created (`!overworldData.isInitialized()`), populates world GameRules from baseline config.
   - Initializes `StackSizeManager` limits from current world GameRules.
2. **Player Connect (`ServerPlayConnectionEvents.JOIN`)**:
   - Sends initial `StackSizeLimitSyncPayload` to joining player.
3. **In-Game Rule Modification (`MinecraftServerMixin.onGameRuleChanged`)**:
   - Detects changes to `stack-size-adjuster:*` GameRules.
   - Updates `StackSizeManager` volatile fields.
   - Broadcasts updated limits to all online players and triggers `broadcastFullState()`.
