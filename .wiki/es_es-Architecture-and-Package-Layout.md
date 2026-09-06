# Arquitectura y estructura de paquetes

## Arquitectura de 1 archivo, 1 propósito

Stack Size Adjuster aplica estrictamente el principio de responsabilidad única. Cada clase se enfoca en una tarea concreta: sincronización de red, configuración, dispersión de objetos o renderizado.

---

## 🌳 Jerarquía de paquetes ASCII

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Punto de entrada principal Fabric y registro de GameRules
├── StackSizeAdjusterFabricClient.java # Punto de entrada del cliente y receptor de paquetes S2C
├── config/
│   ├── ModMenuIntegration.java        # Integración con la API de ModMenu
│   ├── StackSizeConfig.java           # Contenedor de configuración JSON (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # Generador de pantalla GUI reflexivo con YACL 3.9.5
├── mixin/
│   ├── AbstractContainerMenuMixin.java# Cálculo de crafteo rápido de doble precisión
│   ├── ContainerMixin.java            # Anulación de límite de ranura de contenedor (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Intercepción de Containers.dropItemStack
│   ├── DataComponentsMixin.java       # Expansión de códec MAX_STACK_SIZE de DataComponents
│   ├── GiveCommandMixin.java          # Intercepción del comando /give
│   ├── ItemInstanceMixin.java         # Hook en ItemInstance.getMaxStackSize
│   ├── ItemMixin.java                 # Hook en Item.getDefaultMaxStackSize
│   ├── ItemStackMixin.java            # Redirección de rango de ItemStack.getMaxStackSize
│   ├── ItemStackTemplateMixin.java    # Redirección de códec de ItemStackTemplate
│   ├── MinecraftServerMixin.java      # Oyente de cambios de GameRules en el servidor
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # Anulación de renderizado de texto con escalado de matriz
├── network/
│   └── StackSizeLimitSyncPayload.java # Registro de carga útil S2C (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # Lógica de entrega de objetos con multiplicador de seguridad 100x
    ├── InventoryDropHelper.java       # Algoritmo de división controlada de entidades en roturas
    ├── ItemCountRenderer.java         # Renderizador de reducción de fuente matricial
    ├── ModVersionGuard.java           # Comprobación de integridad de clases en tiempo de ejecución
    └── StackSizeManager.java          # Administrador de límites seguro para subprocesos y registro
```

---

## 🔒 Modelo de concurrencia y subprocesos

* **Operaciones de lectura**: `StackSizeManager.getModifiedStackSize` opera sin bloqueos gracias a campos `volatile int` y una lista de anulaciones `CopyOnWriteArrayList`.
* **Sincronización servidor a cliente**: Los paquetes se despachan en el hilo principal del servidor y se procesan en el cliente mediante `context.client().execute(...)`.
