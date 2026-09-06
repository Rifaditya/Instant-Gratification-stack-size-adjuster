# Arquitetura e estrutura de pacotes

## Arquitetura de 1 arquivo, 1 propósito

O mod respeita a separação de responsabilidades. Cada classe gerencia um aspecto bem definido: sincronização de rede, configurações, divisão de quedas ou renderização visual.

---

## 🌳 Hierarquia de pacotes em ASCII

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Ponto de entrada Fabric e registro de GameRules
├── StackSizeAdjusterFabricClient.java # Ponto de entrada do cliente e receptor S2C
├── config/
│   ├── ModMenuIntegration.java        # Integração com ModMenu
│   ├── StackSizeConfig.java           # Contêiner de configuração JSON (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # Construtor reflexivo de telas com YACL 3.9.5
├── mixin/
│   ├── AbstractContainerMenuMixin.java# Cálculo de criação rápida em dupla precisão
│   ├── ContainerMixin.java            # Limite de slot do contêiner (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Interceptação de Containers.dropItemStack
│   ├── DataComponentsMixin.java       # Expansão de codec DataComponents MAX_STACK_SIZE
│   ├── GiveCommandMixin.java          # Interceptação do comando /give
│   ├── ItemInstanceMixin.java         # Hook em ItemInstance.getMaxStackSize
│   ├── ItemMixin.java                 # Hook em Item.getDefaultMaxStackSize
│   ├── ItemStackMixin.java            # Redirecionamento de intervalo ItemStack.getMaxStackSize
│   ├── ItemStackTemplateMixin.java    # Redirecionamento de codec ItemStackTemplate
│   ├── MinecraftServerMixin.java      # Ouvinte de alterações de GameRules no servidor
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # Renderizador com redução matricial de fonte
├── network/
│   └── StackSizeLimitSyncPayload.java # Registro de carga útil S2C (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # Lógica do /give com multiplicador seguro de 100x
    ├── InventoryDropHelper.java       # Divisão controlada de entidades ao quebrar contêineres
    ├── ItemCountRenderer.java         # Renderizador matricial de redução de texto
    ├── ModVersionGuard.java           # Validação de integridade em tempo de execução
    └── StackSizeManager.java          # Gerenciador de limites thread-safe e registros
```

---

## 🔒 Concorrência e threads

* **Operações de leitura**: `StackSizeManager.getModifiedStackSize` opera sem bloqueios com campos `volatile int` e `CopyOnWriteArrayList`.
* **Sincronização servidor-cliente**: Pacotes são despachados pela thread principal do servidor e executados na thread do cliente via `context.client().execute(...)`.
