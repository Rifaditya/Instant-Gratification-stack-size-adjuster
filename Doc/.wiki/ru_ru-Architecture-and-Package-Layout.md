# Архитектура и структура пакетов

## Архитектура «1 файл — 1 назначение»

Stack Size Adjuster строго следует принципу единственной ответственности. Каждый класс отвечает за конкретную область: сетевую синхронизацию, конфиги, обработку выпадения или рендеринг.

---

## 🌳 Иерархия пакетов

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Основная точка входа Fabric и регистрация GameRules
├── StackSizeAdjusterFabricClient.java # Клиентская точка входа и прием S2C пакетов
├── config/
│   ├── ModMenuIntegration.java        # Интеграция с ModMenu API
│   ├── StackSizeConfig.java           # Контейнер конфигурации JSON (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # Рефлексивный строитель интерфейса YACL 3.9.5
├── mixin/
│   ├── AbstractContainerMenuMixin.java# Расчет быстрого крафта с двойной точностью
│   ├── ContainerMixin.java            # Переопределение лимита слота контейнера (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Перехват Containers.dropItemStack
│   ├── DataComponentsMixin.java       # Расширение кодека DataComponents MAX_STACK_SIZE
│   ├── GiveCommandMixin.java          # Перехват команды /give
│   ├── ItemInstanceMixin.java         # Хук ItemInstance.getMaxStackSize
│   ├── ItemMixin.java                 # Хук Item.getDefaultMaxStackSize
│   ├── ItemStackMixin.java            # Перенаправление диапазона ItemStack.getMaxStackSize
│   ├── ItemStackTemplateMixin.java    # Перенаправление диапазона кодека ItemStackTemplate
│   ├── MinecraftServerMixin.java      # Слушатель изменения GameRule на сервере
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # Переопределение рендера количества предметов
├── network/
│   └── StackSizeLimitSyncPayload.java # Запись сетевого пакета S2C (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # Логика команды /give с безопасным множителем 100x
    ├── InventoryDropHelper.java       # Разделение сущностей при разрушении контейнера
    ├── ItemCountRenderer.java         # Рендерер с матричным масштабированием шрифта
    ├── ModVersionGuard.java           # Проверка среды выполнения
    └── StackSizeManager.java          # Потокобезопасный менеджер лимитов и реестр переопределений
```

---

## 🔒 Многопоточность и модель конкурентности

* **Операции чтения**: Метод `StackSizeManager.getModifiedStackSize` свободен от блокировок благодаря `volatile int` полям и `CopyOnWriteArrayList`.
* **Синхронизация сервер-клиент**: Пакеты отправляются в основном серверном потоке и обрабатываются в клиенте через `context.client().execute(...)`.
