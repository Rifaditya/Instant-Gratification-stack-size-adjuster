# Panduan Minecraft 26.2

| Atribut | Spesifikasi |
| :--- | :--- |
| **Target Minecraft** | 26.2 (Rilis Stabil) |
| **Versi Mod** | `1.4.16+26.2` |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `*` (Kompatibel dengan 26.2) |
| **DasikLibrary** | `1.8.3` |
| **Item Clumps** | `>=1.0.18+26.2` |
| **Java JDK** | JDK 25 |
| **Format Identifier** | `Identifier.fromNamespaceAndPath("stack-size-adjuster", path)` |

---

## Gambaran Umum

**Stack Size Adjuster** untuk Minecraft 26.2 dirancang untuk menghapus batasan tumpukan item yang kaku. Mod ini memungkinkan pemain dan pengelola server mengatur batas tumpukan khusus untuk item yang biasanya bertumpuk hingga 64, 16, atau 1.

### Kemampuan Utama di 26.2:
1. **Penskalaan Kategori Dinamis**: GameRules mengontrol batas kategori untuk item 64, 16, dan perkakas/ramuan tunggal.
2. **Pencegahan Lag Drop Wadah**: Membatasi entitas item yang muncul per slot (`max_drop_entities`) saat peti dihancurkan.
3. **Perlindungan Luapan Integer**: Mencegah luapan batas integer bertanda 32-bit ($2,14\text{ miliar}$) pada Peti Besar 54 slot dengan batas aman rekomendasi $39.768.215$.
4. **Sinkronisasi Server-Klien Langsung**: Paket S2C kustom `stack-size-adjuster:sync_limit` memperbarui menu inventaris seketika tanpa perlu reconnect.

---

## Pemasangan & Pengaturan

1. Pastikan **Fabric Loader 0.19.1+** dan **Java 25** telah terpasang.
2. Masukkan file `stack-size-adjuster-1.4.16+26.2.jar` ke folder `.minecraft/mods`.
3. Pastikan dependensi yang diperlukan tersedia:
   - `dasik-library-1.8.3.jar`
   - `item-clumps-1.0.18+26.2.jar`
4. Jalankan game atau server.

---

## Pemeriksaan Versi Runtime (ModVersionGuard)

Saat inisialisasi mod, `ModVersionGuard` memvalidasi classpath:
```java
ModVersionGuard.checkClass("Stack Size Adjuster", "net.minecraft.world.item.ItemStack");
```
Jika lingkungan runtime tidak sesuai, proses inisialisasi dihentikan secara aman dengan catatan log informatif.
