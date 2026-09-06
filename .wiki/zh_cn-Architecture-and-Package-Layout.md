# 架构与包布局

## 单一职责架构 (1 File, 1 Purpose)

Stack Size Adjuster 严格遵循**单一职责原则**。每个类专注于一个独立的子系统：网络同步、配置处理、掉落物拆分或图形渲染。

---

## 🌳 ASCII 代码包层次结构

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Fabric 主入口点与 GameRules 注册
├── StackSizeAdjusterFabricClient.java # 客户端入口点与 S2C 网络数据包接收器
├── config/
│   ├── ModMenuIntegration.java        # ModMenu API 接口集成入口
│   ├── StackSizeConfig.java           # JSON 配置实体 (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # YACL 3.9.5 反射式 GUI 构建器
├── mixin/
│   ├── AbstractContainerMenuMixin.java# 双精度快速合成防溢出计算
│   ├── ContainerMixin.java            # 容器槽位堆叠上限覆盖 (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Containers.dropItemStack 掉落物拦截
│   ├── DataComponentsMixin.java       # DataComponents MAX_STACK_SIZE Codec 上限扩展
│   ├── GiveCommandMixin.java          # /give 指令防卡顿拦截
│   ├── ItemInstanceMixin.java         # ItemInstance.getMaxStackSize 动态挂钩
│   ├── ItemMixin.java                 # Item.getDefaultMaxStackSize 挂钩
│   ├── ItemStackMixin.java            # ItemStack.getMaxStackSize 与数量范围重定向
│   ├── ItemStackTemplateMixin.java    # ItemStackTemplate 编解码器范围重定向
│   ├── MinecraftServerMixin.java      # 服务端 GameRule 修改事件监听器
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # 物品数量 GUI 渲染缩放拦截
├── network/
│   └── StackSizeLimitSyncPayload.java # S2C 数据包 Record (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # 带 100 倍安全乘数的 /give 物品分发逻辑
    ├── InventoryDropHelper.java       # 破坏容器时的受控掉落物拆分算法
    ├── ItemCountRenderer.java         # 动态字体矩阵缩放渲染器
    ├── ModVersionGuard.java           # 运行时类路径完整性校验
    └── StackSizeManager.java          # 核心线程安全上限管理器与覆盖注册表
```

---

## 🔒 线程与并发模型

* **读取操作**：`StackSizeManager.getModifiedStackSize` 通过 `volatile int` 原生变量与 `CopyOnWriteArrayList` 覆盖列表实现无锁高并发读取。
* **服务端至客户端同步**：网络数据包在服务端主线程调度发送，客户端通过 `context.client().execute(...)` 在客户端主线程安全消费更新。
