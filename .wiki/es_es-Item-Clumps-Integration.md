# Integración con Item Clumps

## Resumen

Stack Size Adjuster funciona en sinergia directa con **Item Clumps** (`item_clumps >=1.0.18+26.2`). Mientras que Stack Size Adjuster gestiona los límites en inventarios y contenedores, Item Clumps fusiona entidades caídas en el suelo.

---

## 🤝 Esquema de sinergia mecánica

```
[ Rotura de contenedor ]
           |
           v
[ Stack Size Adjuster: InventoryDropHelper ]
Divide los objetos en un número limitado de entidades (ej. máx 8 por ranura)
           |
           v
[ Entidades generadas en el suelo ]
           |
           v
[ Mod Item Clumps: Fusión de entidades en el suelo ]
Escanea un radio de 3.5 bloques y fusiona los objetos en una sola entidad
           |
           v
[ Entidad única con alta cantidad de objetos ] (¡Cero lag!)
```

---

## 📊 Matriz de sinergia de funciones

| Capa de responsabilidad | Módulo encargado | Ventaja |
| :--- | :--- | :--- |
| **Límites de inventario** | **Stack Size Adjuster** | Aplica dinámicamente límites de 64/16/1 |
| **Generación en rotura de contenedor** | **Stack Size Adjuster** | Evita la creación masiva de entidades al romper cofres |
| **Fusión en el suelo** | **Item Clumps** | Unifica objetos caídos en una sola entidad |
| **Recogida de objetos** | **Núcleo de Minecraft y Mixins** | Recoge entidades fusionadas directamente en ranuras ampliadas |

---

## ⚙️ Configuración recomendada de GameRules

Al jugar junto con **Item Clumps**, ajusta `max_drop_entities` en **8** (o **1** para un rendimiento máximo):
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
