# 版本兼容性矩阵

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

---

## 📊 兼容性矩阵

| Minecraft 目标版本 | 模组版本 | 构建状态 | DasikLibrary 目标 | 依赖边界 |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **活跃 / 当前版本** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ 依赖边界与规则

遵循 **1 Jar 1 Version** 原则，`fabric.mod.json` 中的依赖声明采用开放式下界：

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

### 版本命名与标识规则：
1. **零遗留方案**：Minecraft 26.x 系列绝不混用 `1.21.x` 遗留版本号。
2. **开放式依赖下界**：`minecraft >=26.2-` 保证小补丁版本的无缝兼容，无预发布锁定限制。
3. **类路径验证**：模组初始化时使用 `Thread.currentThread().getContextClassLoader()` 执行 `ModVersionGuard.checkClass` 验证。

---

## 📦 已验证的历史存档

所有已编译的历史正式发布版本二进制文件永久保存在仓库的 `Archive Jar of all versions/` 目录中：

- `stack-size-adjuster-1.4.16+26.2.jar`（当前版本）
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar`（26.2 初始发布版本）
