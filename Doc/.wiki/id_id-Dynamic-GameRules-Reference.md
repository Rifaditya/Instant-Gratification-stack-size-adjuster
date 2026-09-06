# Referensi GameRules Dinamis

## Gambaran Umum

Stack Size Adjuster mendaftarkan aturan permainan bernamespace melalui `DynamicGameRuleManager` dari **DasikLibrary**. Semua aturan berada di bawah kategori `stack-size-adjuster:stack-size-adjuster`.

---

## 📋 Daftar GameRule

| Kunci GameRule | Tipe | Nilai Default | Rentang Nilai | Deskripsi |
| :--- | :--- | :--- | :--- | :--- |
| `stack-size-adjuster:items_64_limit` | Integer | `128` | $1 \text{ hingga } 2.147.483.647$ | Batas tumpukan item yang aslinya bertumpuk hingga 64. |
| `stack-size-adjuster:items_16_limit` | Integer | `32` | $1 \text{ hingga } 2.147.483.647$ | Batas tumpukan item yang aslinya bertumpuk hingga 16. |
| `stack-size-adjuster:items_1_limit` | Integer | `1` | $1 \text{ hingga } 2.147.483.647$ | Batas tumpukan item yang aslinya tidak dapat ditumpuk. |
| `stack-size-adjuster:max_drop_entities` | Integer | `8` | $1 \text{ hingga } 64$ | Maksimal entitas item yang muncul per slot saat wadah hancur. |

---

## 💻 Perintah Dalam Game

### Melihat Nilai Aturan
```text
/gamerule stack-size-adjuster:items_64_limit
```

### Mengubah Nilai Aturan
```text
/gamerule stack-size-adjuster:items_64_limit 512
/gamerule stack-size-adjuster:max_drop_entities 4
```

---

## 🔄 Sinkronisasi Konfigurasi Dua Arah

Saat dunia dibuat atau dimuat:
1. File konfigurasi (`config/stack-size-adjuster.json`) menetapkan nilai awal hanya untuk **dunia yang baru dibuat**.
2. Untuk **dunia yang sudah ada**, perubahan melalui `/gamerule` memperbarui `StackSizeManager` secara langsung.
3. Perubahan aturan memicu `MinecraftServerMixin.onGameRuleChanged`:
   ```java
   @Inject(method = "onGameRuleChanged", at = @At("TAIL"))
   private <T> void onGameRuleChanged(GameRule<T> rule, T value, CallbackInfo ci) {
       Identifier ruleId = rule.getIdentifier();
       if (ruleId != null && ruleId.getNamespace().equals("stack-size-adjuster")) {
           if (value instanceof Integer intVal) {
               StackSizeManager.setLimit(ruleId.getPath(), intVal, (MinecraftServer) (Object) this);
           }
       }
   }
   ```
4. Server secara otomatis mengirim `StackSizeLimitSyncPayload` dan memanggil `player.containerMenu.broadcastFullState()`.
