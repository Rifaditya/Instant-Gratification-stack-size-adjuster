# Matriz de compatibilidad de versiones

> 📌 **Descargo de responsabilidad sobre el código del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes aún no publicadas o características en desarrollo antes de las compilaciones públicas en CurseForge y Modrinth.

---

## 📊 Matriz de compatibilidad

| Objetivo de Minecraft | Versión del mod | Estado de compilación | DasikLibrary objetivo | Límites de dependencias |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **Activo / Actual** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ Límites de dependencias y reglas

Bajo el principio **1 Jar 1 Version**, las dependencias en `fabric.mod.json` aplican límites inferiores abiertos:

```json
"depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": "${minecraft_dependency}",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": "*",
    "item_clumps": ">=1.0.18+26.2"
}
```

### Reglas de versionado:
1. **Cero esquemas heredados**: No se mantienen números de versión `1.21.x` para versiones 26.x.
2. **Límites inferiores abiertos**: `minecraft >=26.2-` permite compatibilidad directa con versiones secundarias de parche.
3. **Verificación de classpath**: Se ejecuta `ModVersionGuard.checkClass` en la carga del mod.

---

## 📦 Archivos históricos verificados

Todos los binarios compilados de versiones anteriores se almacenan permanentemente en el directorio `Archive Jar of all versions/`:

- `stack-size-adjuster-1.4.16+26.2.jar` (Versión actual)
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar` (Versión inicial 26.2)
