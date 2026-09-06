# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

Willkommen zur offiziellen enzyklopädischen Dokumentation von **Instant Gratification: Stack Size Adjuster**. Diese Fabric-Mod für Minecraft ermöglicht es Server-Administratoren und Spielern, die Stapelgrößen von Gegenständen über drei natürliche Kategorien hinweg (64er-, 16er- und nicht stapelbare Gegenstände) dynamisch auf extreme Werte anzupassen – völlig ohne NBT-Aufblähung und mit integrierter Drop-Optimierung und Überlaufschutz.

> 📌 **Haftungsausschluss für Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Stand des Quellcodes im Repository** wider. Sie kann unveröffentlichte Commits oder Entwicklungsfunktionen enthalten, die den offiziellen Veröffentlichungen auf CurseForge und Modrinth voraus sind.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Minecraft-Versionsverzeichnis

* [[Minecraft 26.2 Leitfaden|de_de-Minecraft-26.2-Guide]] — Offizielle Installation, Abhängigkeiten und Einrichtung für Minecraft 26.2.
* [[Versionskompatibilitätsmatrix|de_de-Version-Compatibility]] — Unterstützte Versionen, `ModVersionGuard`-Prüfung und Abhängigkeitsgrenzen.

---

## 🎮 Leitfäden für Spieler & Administratoren

* [[Dynamische GameRules Referenz|de_de-Dynamic-GameRules-Reference]] — Spielregeln (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[Kategoriebasierte Stapelgrenzen|de_de-Category-Based-Stack-Limits]] — Skalierung von 64er-, 16er- und Einzelgegenständen im Detail.
* [[Optimierung von Behälter-Drops|de_de-Container-Drop-Optimization]] — Entity-Limits, Slot-Aufteilungsmathematik und Lag-Prävention bei zerstörten Behältern.
* [[Überlaufschutz für große Truhen|de_de-Large-Chest-Overflow-Protection]] — Ganzzahl-Überlauf ($39.768.215$ Sicherheitsgrenze) und Quick-Crafting mit doppelter Genauigkeit.
* [[ModMenu & YACL Konfiguration|de_de-ModMenu-and-YACL-Configuration]] — Hauptmenü-GUI (`stack-size-adjuster.json`) und YACL 3.9.5 Bildschirm-Integration.
* [[GUI-Rendering der Gegenstandsanzahl|de_de-Item-Count-GUI-Rendering]] — Dynamische Schriftenskalierung für mehrstellige Stapelzahlen.
* [[Integration mit Item Clumps|de_de-Item-Clumps-Integration]] — Zusammenführung von Boden-Entities im Verbund mit Item Clumps.
* [[Fehlerbehebung & FAQ|de_de-Troubleshooting-and-FAQ]] — Häufig gestellte Fragen, Überlaufprävention und Server-Client-Synchronisation.

---

## 💻 Entwickler- & Technische Referenz

* [[Entwickler-Setup & Build-Anleitung|de_de-Developer-Setup-and-Building]] — JDK 25 Umgebung, Gradle 9.3+, Loom 1.15+ und Kompilierungsprozess.
* [[Architektur & Paketstruktur|de_de-Architecture-and-Package-Layout]] — Systemarchitektur, Paketorganisation und Thread-Sicherheitsmodell.
* [[Mixin-Referenz & Injection-Points|de_de-Mixin-Reference-and-Hooks]] — Injektionspunkte in `Item`, `ItemStack`, `Container`, `GiveCommand` und `DataComponents`.
* [[Addon-Override-API|de_de-Addon-Override-API]] — Registrierung benutzerdefinierter Stapellimits über `StackSizeManager.registerOverride`.
* [[Netzwerksynchronisationsprotokoll|de_de-Network-Sync-Protocol]] — S2C-Paket `stack-size-adjuster:sync_limit` und Menü-Aktualisierungen in Echtzeit.
* [[Handhabung des Give-Befehls|de_de-Give-Command-Handling]] — Eigener `GiveCommandHelper` für riesige Stapelmengen ohne Spielabstürze.
* [[Verhaltensprofile & Bedingungen|de_de-Behavior-Profiles-and-Conditions]] — Zustandsautomat zur GameRule-Synchronisation.
* [[Integrationsleitfaden für Mod-Entwickler|de_de-Consumer-Mods-Integration-Guide]] — Leitfaden für Entwickler von Drittanbieter-Addons.
* [[Leistung & Speicherauslastung|de_de-Performance-and-Memory-Impact]] — Null NBT-Aufblähung und Speicher-Benchmarks.
* [[Fortschritte & Abzeichen|de_de-Advancements-and-Badges]] — Fortschrittsmatrix und Vanilla-Parität.

---

## 📜 Urheberrecht & Lizenz

Entwickelt von **Dasik (Rifaditya)** unter der **GNU General Public License v3.0 (GPLv3)**.
