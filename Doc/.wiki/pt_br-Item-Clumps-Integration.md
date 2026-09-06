# Integração com Item Clumps

## Visão geral

O Stack Size Adjuster atua em conjunto com o **Item Clumps** (`item_clumps >=1.0.18+26.2`). Enquanto o Stack Size Adjuster gerencia os limites nos inventários e nas quebras de baús, o Item Clumps agrupa entidades caídas no chão.

---

## 🤝 Diagrama de sinergia

```
[ Evento de quebra de contêiner ]
                |
                v
[ Stack Size Adjuster: InventoryDropHelper ]
Divide os itens em número controlado de entidades (máx 8 por slot)
                |
                v
[ Entidades geradas no chão ]
                |
                v
[ Mod Item Clumps: Mesclagem no chão ]
Varre raio de 3.5 blocos e une itens em uma única entidade
                |
                v
[ Entidade única com alta contagem ] (Zero lag!)
```

---

## 📊 Matriz de responsabilidades

| Camada | Responsável | Benefício |
| :--- | :--- | :--- |
| **Limites de inventário** | **Stack Size Adjuster** | Aplica limites 64/16/1 em baús e jogadores |
| **Geração na quebra de baús** | **Stack Size Adjuster** | Evita a criação de milhares de itens de uma só vez |
| **Mesclagem no chão** | **Item Clumps** | Junta itens soltos no raio em pilhas únicas |
| **Mecânica de coleta** | **Minecraft & Mixins** | Coleta itens agrupados diretamente em slots ampliados |

---

## ⚙️ Configuração recomendada

Ao jogar com o **Item Clumps**, configure `max_drop_entities` como **8** (ou **1** para desempenho extremo):
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
