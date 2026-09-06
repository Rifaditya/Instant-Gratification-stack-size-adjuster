# Guía de Minecraft 26.2

| Atributo | Especificación |
| :--- | :--- |
| **Objetivo de Minecraft** | 26.2 (Versión estable) |
| **Versión del mod** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*` (Cualquier versión compatible con 26.2) |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **Formato de identificador** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## Resumen

**Stack Size Adjuster** para Minecraft 26.2 está diseñado para eliminar las restricciones arbitrarias de apilamiento de objetos. Permite a los jugadores y administradores definir límites máximos personalizados para objetos que naturalmente se apilan en 64, 16 o 1.

### Capacidades clave en 26.2:
1. **Escalado dinámico por categorías**: Las GameRules controlan los topes de las categorías de 64, 16 y herramientas/pociones individuales.
2. **Prevención de lag al romper contenedores**: Limita la cantidad máxima de entidades generadas por ranura (`max_drop_entities`).
3. **Protección contra desbordamiento de enteros**: Previene desbordamientos de enteros de 32 bits ($2.14\text{ mil millones}$) en cofres grandes de 54 ranuras recomendando un límite seguro de $39,768,215$.
4. **Sincronización servidor-cliente en vivo**: El paquete S2C `stack-size-adjuster:sync_limit` actualiza los menús de inventario al instante.

---

## Instalación y configuración

1. Asegúrate de tener instalados **Fabric Loader 0.19.1+** y **Java 25**.
2. Coloca `stack-size-adjuster-1.4.16+26.2.jar` en la carpeta `.minecraft/mods`.
3. Asegúrate de incluir las dependencias requeridas:
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. Inicia el juego o el servidor.

---

## Guardia de versión en tiempo de ejecución (ModVersionGuard)

Durante la inicialización del mod, `ModVersionGuard` valida la resolución del classpath mediante:
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
Si se detecta un entorno incompatible, la inicialización se detiene de forma segura registrando la información en el log.
