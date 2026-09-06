# Guia do Minecraft 26.2

| Atributo | Especificação |
| :--- | :--- |
| **Versão alvo do Minecraft** | 26.2 (Lançamento estável) |
| **Versão do mod** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*` (Qualquer versão compatível com 26.2) |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **Formato de identificador** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## Visão geral

O **Stack Size Adjuster** para Minecraft 26.2 foi desenvolvido para remover as restrições arbitrárias de pilhas de itens. Ele permite que jogadores e administradores configurem limites máximos personalizados para itens que naturalmente acumulam até 64, 16 ou 1.

### Recursos principais no 26.2:
1. **Dimensionamento dinâmico por categoria**: GameRules controlam os limites de 64, 16 e ferramentas/poções de unidade única.
2. **Prevenção de lag na quebra de contêineres**: Limita o número de entidades criadas por slot (`max_drop_entities`).
3. **Proteção contra estouro de inteiros**: Impede que se ultrapasse o limite de inteiros sinalizados de 32 bits ($2,14\text{ bilhões}$) em baús duplos de 54 slots recomendando o limite seguro de $39.768.215$.
4. **Sincronização servidor-cliente instantânea**: Pacote S2C `stack-size-adjuster:sync_limit` atualiza inventários em tempo real.

---

## Instalação e configuração

1. Certifique-se de que o **Fabric Loader 0.19.1+** e o **Java 25** estejam instalados.
2. Coloque o arquivo `stack-size-adjuster-1.4.16+26.2.jar` na pasta `.minecraft/mods`.
3. Garanta a presença das dependências:
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. Inicie o cliente ou o servidor.

---

## Verificação de versão em tempo de execução (ModVersionGuard)

Durante a inicialização, o `ModVersionGuard` verifica o classpath:
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
Se for detectada uma incompatibilidade, a inicialização é interrompida com registro detalhado no log.
