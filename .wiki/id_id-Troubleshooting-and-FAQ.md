# Pemecahan Masalah & Tanya Jawab

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup komit terbaru yang belum dirilis sebelum versi publik di CurseForge dan Modrinth.

---

## ❓ Tanya Jawab Umum

### Q1: Mengapa dunia yang sudah ada tidak menerapkan nilai batas dari `config/stack-size-adjuster.json`?
**J**: File konfigurasi global hanya berlaku untuk **dunia yang baru dibuat**. Pada dunia yang sudah ada, pengaturan tersimpan di `level.dat`. Ubah pengaturan lewat perintah `/gamerule`:
```text
/gamerule stack-size-adjuster:items_64_limit 256
```

### Q2: Apa yang terjadi jika batas tumpukan diatur di atas 39.768.215?
**J**: Pengaturan di atas $39.768.215$ berisiko memicu **luapan integer Peti Besar**. Peti 54 slot yang penuh akan melebihi angka $2.147.483.647$, mengakibatkan nilai berbalik negatif dan menghapus item saat digeser. Nilai $\le 39.768.215$ sangat disarankan.

### Q3: Mengapa saat peti dihancurkan hanya keluar 8 tumpukan item?
**J**: Ini adalah fitur optimasi yang diatur oleh `stack-size-adjuster:max_drop_entities` (default: `8`) untuk menghindari crash server. Bersama **Item Clumps**, 8 tumpukan tersebut segera menyatu menjadi satu entitas.

---

## 🛠️ Panduan Diagnostik Masalah

| Gejala | Penyebab | Solusi |
| :--- | :--- | :--- |
| **Inventaris masih batas vanilla** | Paket jaringan gagal terkirim atau beda versi | Pastikan mod terpasang di **klien** dan **server**. |
| **Perintah Give gagal: "terlalu banyak item"** | Permintaan melebihi batas $100 \times \text{maxStackSize}$ | Minta lebih sedikit item atau jalankan dalam beberapa perintah. |
| **Layar YACL tidak mau terbuka** | ModMenu atau dependensi YACL belum terpasang | Pasang **ModMenu** dan **YetAnotherConfigLib (YACL v3)** di klien. |
