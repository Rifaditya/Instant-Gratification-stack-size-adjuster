# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

欢迎查阅 **Instant Gratification: Stack Size Adjuster** 官方百科文档。本 Minecraft Fabric 模组赋予服务器管理员和玩家动态自定义物品堆叠上限的能力，覆盖三大自然类别（64 堆叠、16 堆叠及不可堆叠物品），支持超大数值且绝不膨胀 NBT 数据，同时提供容器掉落物优化与溢出保护机制。

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Minecraft 版本目录

* [[Minecraft 26.2 目标指南|zh_cn-Minecraft-26.2-Guide]] — Minecraft 26.2 的官方安装、依赖要求与配置指南。
* [[版本兼容性矩阵|zh_cn-Version-Compatibility]] — 支持版本、`ModVersionGuard` 运行时检查及依赖范围。

---

## 🎮 玩家与管理员指南

* [[动态游戏规则参考|zh_cn-Dynamic-GameRules-Reference]] — 游戏内 GameRules 说明（`items_64_limit`、`items_16_limit`、`items_1_limit`、`max_drop_entities`）。
* [[基于类别的堆叠限制|zh_cn-Category-Based-Stack-Limits]] — 64 堆叠、16 堆叠与单物品缩放机制详解。
* [[容器掉落优化|zh_cn-Container-Drop-Optimization]] — 实体生成上限、槽位拆分数学与破坏容器防卡顿机制。
* [[大型箱子防溢出保护|zh_cn-Large-Chest-Overflow-Protection]] — 整数溢出数学（$39,768,215$ 安全上限）与双精度合成逻辑。
* [[ModMenu 与 YACL 配置|zh_cn-ModMenu-and-YACL-Configuration]] — 主菜单 GUI 配置（`stack-size-adjuster.json`）与 YACL 3.9.5 界面集成。
* [[物品数量 GUI 渲染|zh_cn-Item-Count-GUI-Rendering]] — 针对多位数堆叠数量的动态字体缩放算法。
* [[Item Clumps 联动整合|zh_cn-Item-Clumps-Integration]] — 与 Item Clumps 的地面实体合并协同机制。
* [[故障排除与常见问题|zh_cn-Troubleshooting-and-FAQ]] — 常见疑问解答、溢出预防及服务端/客户端同步。

---

## 💻 开发者与技术参考

* [[开发者配置与构建指南|zh_cn-Developer-Setup-and-Building]] — JDK 25 环境、Gradle 9.3+、Loom 1.15+ 与编译工作流。
* [[架构与包布局|zh_cn-Architecture-and-Package-Layout]] — 系统架构树、代码包组织与线程安全模型。
* [[Mixin 参考与注入点|zh_cn-Mixin-Reference-and-Hooks]] — 在 `Item`、`ItemStack`、`Container`、`GiveCommand` 和 `DataComponents` 上的注入点详解。
* [[附属覆盖 API|zh_cn-Addon-Override-API]] — 通过 `StackSizeManager.registerOverride` 注册自定义堆叠上限覆盖规则。
* [[网络同步协议|zh_cn-Network-Sync-Protocol]] — 服务端向客户端发送的 S2C 数据包 `stack-size-adjuster:sync_limit` 与客户端菜单实时刷新机制。
* [[Give 命令处理|zh_cn-Give-Command-Handling]] — 自定义 `GiveCommandHelper`，支持超大堆叠数量且防止崩溃。
* [[行为配置文件与条件|zh_cn-Behavior-Profiles-and-Conditions]] — 动态 GameRule 同步状态机与生命周期。
* [[消费者模组整合指南|zh_cn-Consumer-Mods-Integration-Guide]] — 面向第三方附属开发者的完整集成指南。
* [[性能与内存影响|zh_cn-Performance-and-Memory-Impact]] — 零 NBT 膨胀与内存占用基准测试。
* [[进度与徽章|zh_cn-Advancements-and-Badges]] — 进度系统规范与原版一致性指引。

---

## 📜 版权与归属

由 **Dasik (Rifaditya)** 基于 **GNU 通用公共许可证第 3 版 (GPLv3)** 开发。
