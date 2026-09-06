# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

Bienvenido a la documentación enciclopédica oficial de **Instant Gratification: Stack Size Adjuster**. Este mod de Minecraft Fabric permite a los administradores de servidores y a los jugadores personalizar dinámicamente los tamaños de apilamiento de objetos en tres categorías naturales (objetos apilables hasta 64, hasta 16 y no apilables) hasta cantidades extremas sin inflar los datos NBT, proporcionando además optimización en la caída de contenedores y protección contra desbordamientos.

> 📌 **Descargo de responsabilidad sobre el código del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes aún no publicadas o características en desarrollo antes de las compilaciones públicas en CurseForge y Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Directorio de versiones de Minecraft

* [[Guía de Minecraft 26.2|es_es-Minecraft-26.2-Guide]] — Instalación oficial, dependencias y configuración para Minecraft 26.2.
* [[Matriz de compatibilidad de versiones|es_es-Version-Compatibility]] — Versiones compatibles, comprobación `ModVersionGuard` y límites de dependencias.

---

## 🎮 Guías para jugadores y administradores

* [[Referencia de GameRules dinámicas|es_es-Dynamic-GameRules-Reference]] — GameRules en el juego (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[Límites de apilamiento por categoría|es_es-Category-Based-Stack-Limits]] — Explicación del escalado de objetos de 64, 16 y objetos individuales.
* [[Optimización de caída de contenedores|es_es-Container-Drop-Optimization]] — Límites de generación de entidades, matemáticas de división y prevención de lag al romper contenedores.
* [[Protección contra desbordamiento de cofres grandes|es_es-Large-Chest-Overflow-Protection]] — Matemáticas de desbordamiento de enteros (límite de seguridad de $39,768,215$) y lógica de crafteo rápido de doble precisión.
* [[Configuración de ModMenu y YACL|es_es-ModMenu-and-YACL-Configuration]] — Configuración de la interfaz en el menú principal (`stack-size-adjuster.json`) e integración con YACL 3.9.5.
* [[Renderizado de cantidad de objetos en GUI|es_es-Item-Count-GUI-Rendering]] — Reducción dinámica de escala de fuentes para números de varios dígitos.
* [[Integración con Item Clumps|es_es-Item-Clumps-Integration]] — Sinergia de fusión de entidades en el suelo con Item Clumps.
* [[Solución de problemas y preguntas frecuentes|es_es-Troubleshooting-and-FAQ]] — Preguntas habituales, prevención de desbordamiento y sincronización servidor-cliente.

---

## 💻 Referencia técnica y para desarrolladores

* [[Configuración para desarrolladores y compilación|es_es-Developer-Setup-and-Building]] — Entorno JDK 25, Gradle 9.3+, Loom 1.15+ y flujos de trabajo de compilación.
* [[Arquitectura y estructura de paquetes|es_es-Architecture-and-Package-Layout]] — Árbol de arquitectura del sistema, estructura de paquetes y concurrencia.
* [[Referencia de Mixins y puntos de inyección|es_es-Mixin-Reference-and-Hooks]] — Puntos de inyección en `Item`, `ItemStack`, `Container`, `GiveCommand` y `DataComponents`.
* [[API de anulación para complementos|es_es-Addon-Override-API]] — Registro de anulaciones personalizadas mediante `StackSizeManager.registerOverride`.
* [[Protocolo de sincronización de red|es_es-Network-Sync-Protocol]] — Carga útil S2C `stack-size-adjuster:sync_limit` y actualización en vivo del menú.
* [[Manejo del comando Give|es_es-Give-Command-Handling]] — `GiveCommandHelper` personalizado que admite grandes cantidades sin bloquear el juego.
* [[Perfiles de comportamiento y condiciones|es_es-Behavior-Profiles-and-Conditions]] — Máquina de estados de sincronización dinámica de GameRules.
* [[Guía de integración para mods consumidores|es_es-Consumer-Mods-Integration-Guide]] — Guía de integración completa para desarrolladores de addons.
* [[Rendimiento e impacto en la memoria|es_es-Performance-and-Memory-Impact]] — Cero inflación de NBT y análisis de consumo de memoria.
* [[Avances y distintivos|es_es-Advancements-and-Badges]] — Matriz de avances y directrices de paridad con vanilla.

---

## 📜 Derechos de autor y atribución

Desarrollado por **Dasik (Rifaditya)** bajo la **GNU General Public License v3.0 (GPLv3)**.
