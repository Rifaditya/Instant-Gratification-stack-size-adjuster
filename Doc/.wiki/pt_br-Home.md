# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

Bem-vindo à documentação enciclopédica oficial do **Instant Gratification: Stack Size Adjuster**. Este mod para Fabric no Minecraft permite que administradores de servidores e jogadores personalizem dinamicamente os limites de pilhas de itens em três categorias naturais (itens empilháveis até 64, até 16 e não empilháveis) até quantidades extremas sem inflação de NBT, além de otimizar a queda de contêineres e oferecer proteção contra transbordamento.

> 📌 **Isenção de responsabilidade sobre o código-fonte**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes dos lançamentos públicos no CurseForge e Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Diretório de versões do Minecraft

* [[Guia do Minecraft 26.2|pt_br-Minecraft-26.2-Guide]] — Instalação oficial, dependências e configuração para Minecraft 26.2.
* [[Matriz de compatibilidade de versões|pt_br-Version-Compatibility]] — Versões suportadas, verificação `ModVersionGuard` e limites de dependências.

---

## 🎮 Guias para jogadores e administradores

* [[Referência de GameRules dinâmicas|pt_br-Dynamic-GameRules-Reference]] — GameRules no jogo (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[Limites de empilhamento por categoria|pt_br-Category-Based-Stack-Limits]] — Funcionamento do dimensionamento de itens de 64, 16 e itens únicos.
* [[Otimização de quedas de contêineres|pt_br-Container-Drop-Optimization]] — Limites de entidades geradas, divisão de slots e prevenção de lag ao quebrar baús.
* [[Proteção contra transbordamento de baús grandes|pt_br-Large-Chest-Overflow-Protection]] — Matemática de transbordamento de inteiros (limite seguro de $39.768.215$) e criação rápida de dupla precisão.
* [[Configuração do ModMenu e YACL|pt_br-ModMenu-and-YACL-Configuration]] — Configuração no menu principal (`stack-size-adjuster.json`) e integração com tela YACL 3.9.5.
* [[Renderização da quantidade de itens na GUI|pt_br-Item-Count-GUI-Rendering]] — Redução dinâmica de escala de fonte para números de vários dígitos.
* [[Integração com Item Clumps|pt_br-Item-Clumps-Integration]] — Sinergia de mesclagem de entidades no chão com Item Clumps.
* [[Solução de problemas e Perguntas Frequentes|pt_br-Troubleshooting-and-FAQ]] — Dúvidas comuns, prevenção de transbordamento e sincronização servidor-cliente.

---

## 💻 Referência técnica e para desenvolvedores

* [[Configuração de desenvolvedor e compilação|pt_br-Developer-Setup-and-Building]] — Ambiente JDK 25, Gradle 9.3+, Loom 1.15+ e fluxos de compilação.
* [[Arquitetura e estrutura de pacotes|pt_br-Architecture-and-Package-Layout]] — Árvore de arquitetura do sistema, pacotes e segurança de concorrência.
* [[Referência de Mixins e pontos de injeção|pt_br-Mixin-Reference-and-Hooks]] — Pontos de injeção em `Item`, `ItemStack`, `Container`, `GiveCommand` e `DataComponents`.
* [[API de sobreposição para addons|pt_br-Addon-Override-API]] — Registro de sobreposições personalizadas via `StackSizeManager.registerOverride`.
* [[Protocolo de sincronização de rede|pt_br-Network-Sync-Protocol]] — Carga útil S2C `stack-size-adjuster:sync_limit` e atualização de inventário ao vivo.
* [[Processamento do comando Give|pt_br-Give-Command-Handling]] — `GiveCommandHelper` personalizado que suporta grandes pilhas sem travamentos.
* [[Perfis de comportamento e condições|pt_br-Behavior-Profiles-and-Conditions]] — Máquina de estados de sincronização de GameRules.
* [[Guia de integração para mods consumidores|pt_br-Consumer-Mods-Integration-Guide]] — Guia de integração completo para desenvolvedores de complementos.
* [[Desempenho e impacto na memória|pt_br-Performance-and-Memory-Impact]] — Zero inflação de NBT e benchmarks de memória.
* [[Progressos e distintivos|pt_br-Advancements-and-Badges]] — Matriz de progressos e paridade com o jogo original.

---

## 📜 Direitos autorais e atribuição

Desenvolvido por **Dasik (Rifaditya)** sob a **GNU General Public License v3.0 (GPLv3)**.
