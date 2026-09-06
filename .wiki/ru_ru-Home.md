# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

Добро пожаловать в официальную энциклопедическую документацию **Instant Gratification: Stack Size Adjuster**. Этот Fabric-мод для Minecraft позволяет администраторам серверов и игрокам динамически настраивать лимиты стаков предметов по трем естественным категориям (стакуемые до 64, стакуемые до 16 и нестакуемые предметы) до экстремальных значений без раздувания NBT, обеспечивая оптимизацию выпадения из контейнеров и защиту от переполнения.

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Вики отражает **текущее состояние исходного кода в репозитории**, которое может содержать недавние невыпущенные коммиты или разрабатываемые функции, опережающие публичные сборки на CurseForge и Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Каталог версий Minecraft

* [[Руководство по Minecraft 26.2|ru_ru-Minecraft-26.2-Guide]] — Официальная установка, зависимости и настройка для Minecraft 26.2.
* [[Матрица совместимости версий|ru_ru-Version-Compatibility]] — Поддерживаемые версии, проверка `ModVersionGuard` и границы зависимостей.

---

## 🎮 Руководства для игроков и администраторов

* [[Справочник динамических игровых правил|ru_ru-Dynamic-GameRules-Reference]] — Игровые правила (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[Ограничения стаков по категориям|ru_ru-Category-Based-Stack-Limits]] — Масштабирование стаков до 64, до 16 и единичных предметов.
* [[Оптимизация выпадения из контейнеров|ru_ru-Container-Drop-Optimization]] — Лимиты спавна сущностей, математика разделения слотов и защита от лагов при разрушении контейнеров.
* [[Защита от переполнения большого сундука|ru_ru-Large-Chest-Overflow-Protection]] — Математика целочисленного переполнения (безопасный лимит $39,768,215$) и расчет быстрого крафта с двойной точностью.
* [[Конфигурация ModMenu и YACL|ru_ru-ModMenu-and-YACL-Configuration]] — Настройка GUI в главном меню (`stack-size-adjuster.json`) и интеграция с экраном YACL 3.9.5.
* [[Рендеринг количества предметов в интерфейсе|ru_ru-Item-Count-GUI-Rendering]] — Динамическое уменьшение шрифта для многозначных чисел количества предметов.
* [[Интеграция с Item Clumps|ru_ru-Item-Clumps-Integration]] — Синергия объединения сущностей на земле совместно с Item Clumps.
* [[Устранение неполадок и FAQ|ru_ru-Troubleshooting-and-FAQ]] — Ответы на частые вопросы, предотвращение переполнения и синхронизация сервер-клиент.

---

## 💻 Справочник для разработчиков

* [[Настройка среды разработки и сборка|ru_ru-Developer-Setup-and-Building]] — Окружение JDK 25, Gradle 9.3+, Loom 1.15+ и процесс компиляции.
* [[Архитектура и структура пакетов|ru_ru-Architecture-and-Package-Layout]] — Дерево архитектуры системы, организация пакетов и потокобезопасность.
* [[Справочник Mixin и точки внедрения|ru_ru-Mixin-Reference-and-Hooks]] — Точки внедрения в `Item`, `ItemStack`, `Container`, `GiveCommand` и `DataComponents`.
* [[API переопределения для аддонов|ru_ru-Addon-Override-API]] — Регистрация пользовательских переопределений стаков через `StackSizeManager.registerOverride`.
* [[Протокол сетевой синхронизации|ru_ru-Network-Sync-Protocol]] — S2C-пакет `stack-size-adjuster:sync_limit` и мгновенное обновление меню клиента.
* [[Обработка команды Give|ru_ru-Give-Command-Handling]] — Пользовательский `GiveCommandHelper`, поддерживающий гигантские стаки без падений игры.
* [[Профили поведения и условия|ru_ru-Behavior-Profiles-and-Conditions]] — Конечный автомат синхронизации динамических GameRules.
* [[Руководство по интеграции сторонних модов|ru_ru-Consumer-Mods-Integration-Guide]] — Полное руководство по интеграции для разработчиков аддонов.
* [[Производительность и потребление памяти|ru_ru-Performance-and-Memory-Impact]] — Нулевое раздувание NBT и замеры потребления памяти.
* [[Достижения и значки|ru_ru-Advancements-and-Badges]] — Паритет с ванильными достижениями и значки интеграции.

---

## 📜 Авторские права и атрибуция

Разработано **Dasik (Rifaditya)** под лицензией **GNU General Public License v3.0 (GPLv3)**.
