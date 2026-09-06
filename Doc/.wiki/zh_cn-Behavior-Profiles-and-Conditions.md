# 行为配置文件与条件

## 概述

Stack Size Adjuster 依靠一套状态机架构，在服务端与客户端边界之间无缝衔接世界配置、动态游戏规则与网络数据包。

---

## 🔄 状态机生命周期图

```text
               +----------------------------------+
               |        服务端启动 / 世界创建        |
               +----------------------------------+
                                |
                                v
               [ 加载基础配置模板 ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ 注册动态游戏规则 ]
               (`items_64_limit`, `items_16_limit` 等)
                                |
                +---------------+---------------+
                |                               |
          全新创建的世界                     已存在的加载世界
                |                               |
                v                               v
        应用配置默认值至规则              从 `level.dat` 加载已存规则
                |                               |
                +---------------+---------------+
                                |
                                v
              [ 初始化运行期 StackSizeManager ]
                                |
                                v
              [ 玩家加入事件 / GameRule 编辑修改 ]
                                |
                                v
             [ 派发 S2C 数据包: sync_limit ]
                                |
                                v
             [ 强制刷新客户端菜单界面状态 ]
```

---

## ⚙️ 核心触发事件与处理器

1. **服务端启动完成 (`ServerLifecycleEvents.SERVER_STARTED`)**：
   - 重新加载基础配置文件。
   - 若世界为新生成（`!overworldData.isInitialized()`），从基础配置填入世界 GameRules 默认值。
   - 依据当前世界 GameRules 初始化 `StackSizeManager` 上限。
2. **玩家连接 (`ServerPlayConnectionEvents.JOIN`)**：
   - 向刚加入的玩家发送初始 `StackSizeLimitSyncPayload`。
3. **游戏内规则修改 (`MinecraftServerMixin.onGameRuleChanged`)**：
   - 侦测 `stack-size-adjuster:*` 规则的改动。
   - 更新 `StackSizeManager` 的 `volatile` 变量。
   - 向所有在线玩家广播更新数据包并触发 `broadcastFullState()`。
