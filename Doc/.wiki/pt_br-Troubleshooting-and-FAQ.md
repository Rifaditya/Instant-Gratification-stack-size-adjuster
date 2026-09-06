# Solução de problemas e Perguntas Frequentes

> 📌 **Isenção de responsabilidade sobre o código-fonte**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes dos lançamentos públicos no CurseForge e Modrinth.

---

## ❓ Perguntas frequentes

### P1: Por que mundos existentes não aplicam os limites do `config/stack-size-adjuster.json`?
**R**: As configurações globais definem apenas modelos para **mundos recém-criados**. Em mundos existentes, as regras ficam salvas no `level.dat`. Altere-as no jogo com `/gamerule`:
```text
/gamerule stack-size-adjuster:items_64_limit 256
```

### P2: O que ocorre ao configurar limites maiores que 39.768.215?
**R**: Valores acima de $39.768.215$ podem gerar **estouro de inteiros em baús duplos**. Um baú duplo de 54 slots cheio excederá $2.147.483.647$, causando números negativos e apagando itens ao movê-los. Manter $\le 39.768.215$ é altamente recomendado.

### P3: Por que quebrar um baú gera apenas 8 montes de itens em vez de milhares?
**R**: É uma otimização regida por `stack-size-adjuster:max_drop_entities` (padrão: `8`) para evitar o congelamento do servidor. Em conjunto com o **Item Clumps**, esses 8 montes são unidos num instante.

---

## 🛠️ Diagnóstico de problemas comuns

| Sintoma | Causa | Solução |
| :--- | :--- | :--- |
| **Inventário exibe limites originais** | Falha de sincronização de rede ou versões divergentes | Verifique se o mod está no **cliente** e no **servidor**. |
| **Comando Give acusa "muitos itens"** | `/give` ultrapassa $100 \times \text{maxStackSize}$ | Solicite menos itens ou divida o comando em várias etapas. |
| **Tela YACL não abre no ModMenu** | Falta ModMenu ou biblioteca YACL | Instale o **ModMenu** e o **YetAnotherConfigLib (YACL v3)** no cliente. |
