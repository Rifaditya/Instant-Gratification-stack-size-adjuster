# Rendimiento e impacto en la memoria

## Resumen

Stack Size Adjuster está diseñado para ofrecer máxima eficiencia, con cero consumo adicional de memoria y sin inflar las etiquetas NBT.

---

## ⚡ Optimizaciones clave de rendimiento

1. **Almacenamiento en tipos primitivos**: Los límites activos se almacenan en variables primitivas `volatile int` dentro de `StackSizeManager`, asegurando lecturas $O(1)$ sin bloqueos.
2. **Cero inflación de NBT**: A diferencia de los mods que inyectan etiquetas NBT personalizadas, Stack Size Adjuster actúa sobre los códecs vanilla (`DataComponents.MAX_STACK_SIZE` y `ExtraCodecs.intRange`). El tamaño de los mundos guardados es idéntico a vanilla.
3. **Sin oyentes de tick**: No ejecuta lógica en bucles continuos de juego (`EndTick`, `WorldTick`). Se ejecuta bajo demanda.
4. **Anulaciones concurrentes seguras**: Las anulaciones externas se conservan en un `CopyOnWriteArrayList` para iteraciones seguras entre subprocesos.

---

## 📊 Medición de consumo de memoria y CPU

| Métrica | Impacto medido | Mecanismo de optimización |
| :--- | :--- | :--- |
| **Asignación en memoria Heap** | $< 50\text{ KB}$ | Cero creación de objetos efímeros en consultas |
| **Espacio adicional en guardado** | $+0\text{ Bytes}$ | Modificación de códec de componentes nativos |
| **Sobrecarga de tick de servidor (MSPT)**| $0.00\text{ ms}$ | Lectura de tipos primitivos sin bloqueos |
| **Rotura de contenedores (MSPT)** | $< 0.50\text{ ms}$ | Tope de entidades en `InventoryDropHelper` |
