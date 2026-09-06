# Perfiles de comportamiento y condiciones

## Resumen

Stack Size Adjuster opera mediante una máquina de estados que vincula la configuración del mundo, las GameRules dinámicas y los paquetes de red entre el servidor y el cliente.

---

## 🔄 Diagrama de ciclo de vida de la máquina de estados

```text
               +----------------------------------+
               |   Inicio de servidor / Creación  |
               +----------------------------------+
                                |
                                v
               [ Cargar plantilla de configuración ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ Registrar GameRules dinámicas    ]
               (`items_64_limit`, `items_16_limit`, etc.)
                                |
                +---------------+---------------+
                |                               |
          Mundo nuevo                     Mundo cargado existente
                |                               |
                v                               v
        Aplicar valores por              Cargar GameRules desde
        defecto a las reglas             archivo `level.dat`
                |                               |
                +---------------+---------------+
                                |
                                v
               [ Inicializar StackSizeManager ]
                                |
                                v
               [ Jugador conecta / Modificación GameRule ]
                                |
                                v
               [ Enviar paquete S2C: sync_limit ]
                                |
                                v
               [ Forzar refresco de GUI en cliente ]
```

---

## ⚙️ Eventos de activación y manejadores

1. **Inicialización del servidor (`ServerLifecycleEvents.SERVER_STARTED`)**:
   - Recarga la configuración básica.
   - Si el mundo es nuevo (`!overworldData.isInitialized()`), aplica la configuración por defecto a las GameRules.
   - Inicializa los límites de `StackSizeManager`.
2. **Conexión de jugador (`ServerPlayConnectionEvents.JOIN`)**:
   - Envía el paquete `StackSizeLimitSyncPayload` al jugador que entra.
3. **Modificación de GameRule (`MinecraftServerMixin.onGameRuleChanged`)**:
   - Detecta cambios en las reglas `stack-size-adjuster:*`.
   - Actualiza los campos de `StackSizeManager`.
   - Transmite los límites a todos los jugadores en línea.
