# Perfis de comportamento e condições

## Visão geral

O Stack Size Adjuster opera através de uma máquina de estados que conecta arquivos de configuração, GameRules e pacotes de rede entre o servidor e o cliente.

---

## 🔄 Diagrama de ciclo de vida

```text
               +----------------------------------+
               |  Inicialização / Criação mundo   |
               +----------------------------------+
                                |
                                v
               [ Carregar modelo de configuração  ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ Registrar GameRules dinâmicas    ]
               (`items_64_limit`, `items_16_limit`, etc.)
                                |
                +---------------+---------------+
                |                               |
          Mundo novo                     Mundo existente
                |                               |
                v                               v
        Aplicar modelo padrão            Carregar GameRules
        às regras do jogo                do `level.dat`
                |                               |
                +---------------+---------------+
                                |
                                v
               [ Inicializar StackSizeManager   ]
                                |
                                v
               [ Jogador entra / Alteração regra ]
                                |
                                v
               [ Enviar pacote S2C: sync_limit  ]
                                |
                                v
               [ Forçar atualização de telas    ]
```

---

## ⚙️ Gatilhos e tratadores

1. **Inicialização do servidor (`ServerLifecycleEvents.SERVER_STARTED`)**:
   - Recarrega o arquivo de configuração.
   - Em mundos novos, aplica os valores padrão às GameRules.
   - Inicializa os limites do `StackSizeManager`.
2. **Entrada de jogador (`ServerPlayConnectionEvents.JOIN`)**:
   - Envia o pacote `StackSizeLimitSyncPayload`.
3. **Modificação de GameRule (`MinecraftServerMixin.onGameRuleChanged`)**:
   - Identifica mudanças em `stack-size-adjuster:*`.
   - Atualiza as variáveis do `StackSizeManager`.
   - Transmite os dados para os jogadores conectados.
