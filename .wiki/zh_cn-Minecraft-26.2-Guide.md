# Minecraft 26.2 目标指南

| 属性 | 规范说明 |
| :--- | :--- |
| **Minecraft 目标版本** | 26.2（正式稳定版） |
| **模组版本** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*`（任意 26.2 兼容版本） |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **标识符格式** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## 概述

适用于 Minecraft 26.2 的 **Stack Size Adjuster** 旨在解除原版严苛的物品堆叠限制。它允许玩家和服务器管理员针对原本可堆叠至 64、16 或 1 的物品分别设定自定义最大堆叠上限。

### 26.2 核心特性：
1. **动态类别缩放**：通过 GameRules 独立调控 64 堆叠、16 堆叠以及单物品工具/药水的上限。
2. **容器掉落卡顿防护**：破坏箱子或容器时，限制每个物品栏槽位生成实体数量上限（`max_drop_entities`）。
3. **整数溢出安全防护**：针对 54 槽位大箱子，建议将上限限制在 $39,768,215$ 以内，防止超过 32 位有符号整数上限（$21.4\text{ 亿}$）。
4. **实时服务端-客户端同步**：自定义 S2C 数据包 `stack-size-adjuster:sync_limit` 瞬间同步物品栏菜单，无需玩家重新连接。

---

## 安装与配置

1. 确保已安装 **Fabric Loader 0.19.1+** 与 **Java 25**。
2. 将 `stack-size-adjuster-1.4.16+26.2.jar` 放入 `.minecraft/mods` 目录中。
3. 确保安装了前置依赖：
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. 启动客户端或服务端。

---

## 运行时版本守卫 (Runtime Version Guard)

在模组初始化期间，`ModVersionGuard` 会通过以下逻辑验证类路径完整性：
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
如果检测到不兼容的加载器上下文或不匹配的 Minecraft 运行时环境，初始化将安全终止并输出详尽日志。
