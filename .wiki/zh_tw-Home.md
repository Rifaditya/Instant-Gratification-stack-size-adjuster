# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

歡迎查閱 **Instant Gratification: Stack Size Adjuster** 官方百科文件。本 Minecraft Fabric 模組賦予伺服器管理員與玩家動態自訂物品堆疊上限的能力，涵蓋三大自然類別（64 堆疊、16 堆疊及不可堆疊物品），支援超大數值且絕不膨脹 NBT 資料，同時提供容器掉落物最佳化與溢位保護機制。

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 和 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Minecraft 版本目錄

* [[Minecraft 26.2 目標指南|zh_tw-Minecraft-26.2-Guide]] — Minecraft 26.2 的官方安裝、依賴需求與設定指南。
* [[版本相容性矩陣|zh_tw-Version-Compatibility]] — 支援版本、`ModVersionGuard` 執行時期檢查及依賴範圍。

---

## 🎮 玩家與管理員指南

* [[動態遊戲規則參考|zh_tw-Dynamic-GameRules-Reference]] — 遊戲內 GameRules 說明（`items_64_limit`、`items_16_limit`、`items_1_limit`、`max_drop_entities`）。
* [[基於類別的堆疊限制|zh_tw-Category-Based-Stack-Limits]] — 64 堆疊、16 堆疊與單物品縮放機制詳解。
* [[容器掉落最佳化|zh_tw-Container-Drop-Optimization]] — 實體生成上限、槽位拆分數學與破壞容器防卡頓機制。
* [[大型箱子防溢位保護|zh_tw-Large-Chest-Overflow-Protection]] — 整數溢位數學（$39,768,215$ 安全上限）與雙精度合成邏輯。
* [[ModMenu 與 YACL 設定|zh_tw-ModMenu-and-YACL-Configuration]] — 主選單 GUI 設定（`stack-size-adjuster.json`）與 YACL 3.9.5 介面整合。
* [[物品數量 GUI 渲染|zh_tw-Item-Count-GUI-Rendering]] — 針對多位數堆疊數量的動態字型縮放演算法。
* [[Item Clumps 連動整合|zh_tw-Item-Clumps-Integration]] — 與 Item Clumps 的地面實體合併協同機制。
* [[疑難排解與常見問題|zh_tw-Troubleshooting-and-FAQ]] — 常見疑問解答、溢位預防及伺服端/客戶端同步。

---

## 💻 開發者與技術參考

* [[開發者配置與構建指南|zh_tw-Developer-Setup-and-Building]] — JDK 25 環境、Gradle 9.3+、Loom 1.15+ 與編譯工作流程。
* [[架構與套件結構|zh_tw-Architecture-and-Package-Layout]] — 系統架構樹、套件組織與執行緒安全模型。
* [[Mixin 參考與注入點|zh_tw-Mixin-Reference-and-Hooks]] — 在 `Item`、`ItemStack`、`Container`、`GiveCommand` 與 `DataComponents` 上的注入點詳解。
* [[附屬覆蓋 API|zh_tw-Addon-Override-API]] — 透過 `StackSizeManager.registerOverride` 註冊自訂堆疊上限覆蓋規則。
* [[網路同步協定|zh_tw-Network-Sync-Protocol]] — 伺服端向客戶端發送的 S2C 封包 `stack-size-adjuster:sync_limit` 與客戶端選單即時重新整理機制。
* [[Give 指令處理|zh_tw-Give-Command-Handling]] — 自訂 `GiveCommandHelper`，支援超大堆疊數量且防止當機。
* [[行為設定檔與條件|zh_tw-Behavior-Profiles-and-Conditions]] — 動態 GameRule 同步狀態機與生命週期。
* [[取用端模組整合指南|zh_tw-Consumer-Mods-Integration-Guide]] — 面向第三方附屬開發者的完整整合指南。
* [[效能與記憶體影響|zh_tw-Performance-and-Memory-Impact]] — 零 NBT 膨脹與記憶體佔用基準測試。
* [[進度與徽章|zh_tw-Advancements-and-Badges]] — 進度系統規範與原版一致性指引。

---

## 📜 版權與歸屬

由 **Dasik (Rifaditya)** 基於 **GNU 通用公共授權條款第 3 版 (GPLv3)** 開發。
