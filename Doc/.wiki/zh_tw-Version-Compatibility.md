# 版本相容性矩陣

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 和 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

---

## 📊 相容性矩陣

| Minecraft 目標版本 | 模組版本 | 構建狀態 | DasikLibrary 目標 | 依賴邊界 |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **活躍 / 當前版本** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ 依賴邊界與規則

遵循 **1 Jar 1 Version** 原則，`fabric.mod.json` 中的依賴聲明採用開放式下界：

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

### 版本命名與識別規則：
1. **零遺留方案**：Minecraft 26.x 系列絕不混用 `1.21.x` 舊版號。
2. **開放式依賴下界**：`minecraft >=26.2-` 保證小修正版本的無縫相容，無預先發布鎖定限制。
3. **類別路徑驗證**：模組初始化時使用 `Thread.currentThread().getContextClassLoader()` 執行 `ModVersionGuard.checkClass` 驗證。

---

## 📦 已驗證的歷史存檔

所有已編譯的歷史正式發布版本二進位檔案永久保存在倉庫的 `Archive Jar of all versions/` 目錄中：

- `stack-size-adjuster-1.4.16+26.2.jar`（當前版本）
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar`（26.2 初始發布版本）
