# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

Welcome to the official encyclopedic documentation for **Instant Gratification: Stack Size Adjuster**. This Minecraft Fabric mod empowers server administrators and players to dynamically customize item stack sizes across three natural categories (64-stackable, 16-stackable, and non-stackable items) up to extreme quantities without NBT inflation, while providing container drop optimization and overflow protection.

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 📦 Minecraft Versions Directory

* [[Minecraft 26.2 Target Guide|Minecraft-26.2-Guide]] — Official installation, dependencies, and setup for Minecraft 26.2.
* [[Version Compatibility Matrix|Version-Compatibility]] — Supported versions, `ModVersionGuard` check, and dependency bounds.

---

## 🎮 Player & Administrator Guides

* [[Dynamic GameRules Reference|Dynamic-GameRules-Reference]] — In-game GameRules (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[Category-Based Stack Limits|Category-Based-Stack-Limits]] — Explanation of 64-stackable, 16-stackable, and 1-stackable item scaling.
* [[Container Drop Optimization|Container-Drop-Optimization]] — Entity spawn caps, slot split math, and container break lag prevention.
* [[Large Chest Overflow Protection|Large-Chest-Overflow-Protection]] — Integer overflow math ($39,768,215$ safety limit) and double-precision crafting logic.
* [[ModMenu & YACL Configuration|ModMenu-and-YACL-Configuration]] — Main menu GUI configuration (`stack-size-adjuster.json`) and YACL 3.9.5 screen integration.
* [[Item Count GUI Rendering|Item-Count-GUI-Rendering]] — Dynamic font scale-down for multi-digit stack numbers.
* [[Item Clumps Integration|Item-Clumps-Integration]] — Co-dependent entity merging synergy with Item Clumps.
* [[Troubleshooting & FAQ|Troubleshooting-and-FAQ]] — Common questions, overflow prevention, and server-client sync.

---

## 💻 Developer & Technical Reference

* [[Developer Setup & Building|Developer-Setup-and-Building]] — JDK 25 environment, Gradle 9.3+, Loom 1.15+, and compilation workflows.
* [[Architecture & Package Layout|Architecture-and-Package-Layout]] — System architecture tree, package organization, and thread-safety model.
* [[Mixin Reference & Hooks|Mixin-Reference-and-Hooks]] — Injection points across `Item`, `ItemStack`, `Container`, `GiveCommand`, and `DataComponents`.
* [[Addon Override API|Addon-Override-API]] — Registering custom stack size overrides via `StackSizeManager.registerOverride`.
* [[Network Sync Protocol|Network-Sync-Protocol]] — S2C payload `stack-size-adjuster:sync_limit` and live client menu updates.
* [[Give Command Handling|Give-Command-Handling]] — Custom `GiveCommandHelper` supporting large stack counts without crashes.
* [[Behavior Profiles & Conditions|Behavior-Profiles-and-Conditions]] — Dynamic GameRule synchronization state machine.
* [[Consumer Mods Integration Guide|Consumer-Mods-Integration-Guide]] — Complete integration guide for third-party addon developers.
* [[Performance & Memory Impact|Performance-and-Memory-Impact]] — Zero NBT inflation and memory footprint breakdown.
* [[Advancements & Badges|Advancements-and-Badges]] — Advancement matrix and vanilla parity guidelines.

---

## 📜 Copyright & Attribution

Developed by **Dasik (Rifaditya)** under the **GNU General Public License v3.0 (GPLv3)**.
