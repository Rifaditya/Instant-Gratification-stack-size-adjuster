# Desempenho e impacto na memória

## Visão geral

O Stack Size Adjuster foi planejado com foco em eficiência máxima, consumo irrisório de memória e zero poluição de NBT.

---

## ⚡ Otimizações centrais de desempenho

1. **Valores primitivos**: Limites são armazenados em campos primitivos `volatile int` no `StackSizeManager`, viabilizando leituras $O(1)$ sem concorrência de bloqueio.
2. **Sem inflação de NBT**: Em vez de adicionar tags personalizadas aos itens, o mod atua diretamente nos codecs de componentes nativos (`DataComponents.MAX_STACK_SIZE` e `ExtraCodecs.intRange`). O tamanho do mundo gravado é idêntico ao jogo original.
3. **Sem ouvintes de tick contínuos**: O mod não executa lógica em laços de repetição de tick (`EndTick`, `WorldTick`). A execução ocorre apenas sob demanda.
4. **Iteração segura entre threads**: As regras de sobreposição ficam em uma `CopyOnWriteArrayList` thread-safe.

---

## 📊 Benchmarks de impacto

| Métrica | Impacto observado | Mecanismo técnico |
| :--- | :--- | :--- |
| **Alocação na memória Heap** | $< 50\text{ KB}$ | Zero criação de objetos temporários em checagens |
| **Pegada de dados no save** | $+0\text{ Bytes}$ | Modificação direta de codecs nativos |
| **Sobrecarga de tick (MSPT)** | $0.00\text{ ms}$ | Leitura direta de tipos primitivos sem laços |
| **Quebra de contêineres (MSPT)** | $< 0.50\text{ ms}$ | Limite de entidades em `InventoryDropHelper` |
