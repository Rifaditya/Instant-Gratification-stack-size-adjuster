# 架構與套件結構

## 單一職責架構 (1 File, 1 Purpose)

Stack Size Adjuster 嚴格遵循**單一職責原則**。每個類別專注於一個獨立的子系統：網路同步、設定處理、掉落物拆分或圖形渲染。

---

## 🌳 ASCII 程式碼套件階層結構

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Fabric 主進入點與 GameRules 註冊
├── StackSizeAdjusterFabricClient.java # 客戶端進入點與 S2C 網路封包接收器
├── config/
│   ├── ModMenuIntegration.java        # ModMenu API 介面整合入口
│   ├── StackSizeConfig.java           # JSON 設定實體 (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # YACL 3.9.5 反射式 GUI 建構器
├── mixin/
│   ├── AbstractContainerMenuMixin.java# 雙精度快速合成防溢位計算
│   ├── ContainerMixin.java            # 容器槽位堆疊上限覆蓋 (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Containers.dropItemStack 掉落物攔截
│   ├── DataComponentsMixin.java       # DataComponents MAX_STACK_SIZE Codec 上限擴充
│   ├── GiveCommandMixin.java          # /give 指令防卡頓攔截
│   ├── ItemInstanceMixin.java         # ItemInstance.getMaxStackSize 動態掛鉤
│   ├── ItemMixin.java                 # Item.getDefaultMaxStackSize 掛鉤
│   ├── ItemStackMixin.java            # ItemStack.getMaxStackSize 與數量範圍重新導向
│   ├── ItemStackTemplateMixin.java    # ItemStackTemplate 編解碼器範圍重新導向
│   ├── MinecraftServerMixin.java      # 伺服端 GameRule 修改事件監聽器
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # 物品數量 GUI 渲染縮放攔截
├── network/
│   └── StackSizeLimitSyncPayload.java # S2C 封包 Record (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # 帶 100 倍安全乘數的 /give 物品分發邏輯
    ├── InventoryDropHelper.java       # 破壞容器時的受控掉落物拆分演算法
    ├── ItemCountRenderer.java         # 動態字型矩陣縮放渲染器
    ├── ModVersionGuard.java           # 執行時期類別路徑完整性校驗
    └── StackSizeManager.java          # 核心執行緒安全上限管理器與覆蓋註冊表
```

---

## 🔒 執行緒與並行模型

* **讀取操作**：`StackSizeManager.getModifiedStackSize` 透過 `volatile int` 原生變數與 `CopyOnWriteArrayList` 覆蓋列表實現無鎖高並行讀取。
* **伺服端至客戶端同步**：網路封包在伺服端主執行緒排程發送，客戶端透過 `context.client().execute(...)` 在客戶端主執行緒安全消費更新。
