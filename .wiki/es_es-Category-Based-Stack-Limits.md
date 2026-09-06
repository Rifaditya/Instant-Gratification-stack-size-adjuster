# Límites de apilamiento por categoría

## Resumen del sistema

Minecraft clasifica naturalmente los objetos en tres niveles de apilamiento mediante `DataComponents.MAX_STACK_SIZE`:
1. **Apilables hasta 64**: Bloques de construcción, recursos, objetos comunes (adoquines, tierra, lingotes).
2. **Apilables hasta 16**: Perlas de Ender, bolas de nieve, cubos, huevos, letreros.
3. **Apilables hasta 1 (No apilables)**: Herramientas, armas, armaduras, pociones, monturas, vagonetas.

---

## 🧮 Diagrama de flujo de cálculo de tamaño de pila

```
                 +--------------------------------+
                 |  Cálculo de tamaño de pila     |
                 +--------------------------------+
                                  |
                                  v
                  [ Comprobar anulaciones previas ]
                  (ej. Potion Stacker Addon)
                                  |
                 +----------------+----------------+
                 |                                 |
         ¿Anulación hallada?             Sin anulación
                 |                                 |
                 v                                 v
        Devolver límite modificado      Inspeccionar valor vanilla
                                            (DataComponents)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                Vanilla >= 64                 Vanilla >= 16                 Vanilla == 1
                     |                             |                             |
                     v                             v                             v
           Devolver `items_64_limit`     Devolver `items_16_limit`     Devolver `items_1_limit`
```

---

## 💻 Lógica Java fundamental

El cálculo del tamaño de apilamiento es gestionado por `StackSizeManager.getModifiedStackSize`:

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // Aplica anulaciones registradas de complementos (ej. Potion Stacker)
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ Recomendaciones de límites por categoría

| Categoría de objeto | Predeterminado | Máximo recomendado | Perfil de rendimiento |
| :--- | :--- | :--- | :--- |
| **Apilables hasta 64** | `128` | $39,768,215$ | Alta eficiencia. Admite millones con total fluidez. |
| **Apilables hasta 16** | `32` | $39,768,215$ | Escalado suave para perlas y huevos. |
| **No apilables** | `1` | $39,768,215$ | Permite apilar herramientas/pociones. |
