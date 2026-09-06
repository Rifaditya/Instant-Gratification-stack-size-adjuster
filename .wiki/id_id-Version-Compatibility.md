# Matriks Kompatibilitas Versi

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup komit terbaru yang belum dirilis sebelum versi publik di CurseForge dan Modrinth.

---

## 📊 Matriks Kompatibilitas

| Target Minecraft | Versi Mod | Status Build | Target DasikLibrary | Batasan Dependensi |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **Aktif / Terkini** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ Aturan dan Batasan Dependensi

Berdasarkan aturan **1 Jar 1 Version**, dependensi di `fabric.mod.json` menetapkan batas bawah terbuka:

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

### Pedoman Identitas Versi:
1. **Nol Skema Warisan**: Tidak mempertahankan format versi lama `1.21.x` pada rilis Minecraft 26.x.
2. **Batas Bawah Terbuka**: `minecraft >=26.2-` mengizinkan kompatibilitas pembaruan patch kecil tanpa penguncian pre-release.
3. **Verifikasi Classpath**: Mod menjalankan `ModVersionGuard.checkClass` saat dimulai.

---

## 📦 Arsip Historis Terverifikasi

Semua biner rilis masa lalu disimpan secara permanen di direktori `Archive Jar of all versions/`:

- `stack-size-adjuster-1.4.16+26.2.jar` (Rilis Saat Ini)
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar` (Rilis Perdana 26.2)
