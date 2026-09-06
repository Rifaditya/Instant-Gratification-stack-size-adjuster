# Solución de problemas y preguntas frecuentes

> 📌 **Descargo de responsabilidad sobre el código del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes aún no publicadas o características en desarrollo antes de las compilaciones públicas en CurseForge y Modrinth.

---

## ❓ Preguntas frecuentes

### P1: ¿Por qué los mundos existentes no aplican los límites configurados en `config/stack-size-adjuster.json`?
**R**: El archivo global (`stack-size-adjuster.json`) solo define valores de plantilla para **mundos recién creados**. En mundos ya existentes, los ajustes se guardan en `level.dat`. Cambia las reglas dentro del juego mediante `/gamerule`:
```text
/gamerule stack-size-adjuster:items_64_limit 256
```

### P2: ¿Qué sucede si configuro límites superiores a 39,768,215?
**R**: Ajustar límites por encima de $39,768,215$ genera riesgo de **desbordamiento de enteros en cofres grandes**. Un cofre grande de 54 ranuras lleno superará el límite del entero de 32 bits ($2,147,483,647$), haciendo que el contador se vuelva negativo y se borren objetos al moverlos. Se recomienda mantener los valores en $\le 39,768,215$.

### P3: ¿Por qué al romper un cofre solo caen 8 grupos de objetos en lugar de miles?
**R**: Es una optimización deliberada controlada por `stack-size-adjuster:max_drop_entities` (predeterminado: `8`) para evitar bloqueos del servidor. Junto con **Item Clumps**, esas 8 pilas se combinan inmediatamente en una sola.

---

## 🛠️ Diagnóstico de problemas comunes

| Síntoma | Causa | Solución |
| :--- | :--- | :--- |
| **El inventario muestra límites vanilla** | Falló la sincronización de red o hay discrepancia de versiones | Asegúrate de que el mod esté instalado tanto en el **cliente** como en el **servidor**. |
| **El comando Give indica "demasiados objetos"** | `/give` supera el tope de $100 \times \text{maxStackSize}$ | Solicita menos objetos por comando o repártelos en varios comandos. |
| **La pantalla YACL no se abre en ModMenu** | Falta ModMenu o la biblioteca YACL | Instala **ModMenu** y **YetAnotherConfigLib (YACL v3)** en el cliente. |
