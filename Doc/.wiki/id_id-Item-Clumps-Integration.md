# Integrasi Item Clumps

## Gambaran Umum

Stack Size Adjuster bekerja dalam sinergi langsung dengan **Item Clumps** (`item_clumps >=1.0.18+26.2`). Ketika Stack Size Adjuster mengatur batas tumpukan di inventaris dan drop wadah, Item Clumps bertugas menggabungkan entitas yang berserakan di tanah.

---

## 🤝 Diagram Alur Kerja Sinergis

```
[ Peristiwa Wadah Hancur ]
            |
            v
[ Stack Size Adjuster: InventoryDropHelper ]
Membagi item ke jumlah entitas terbatas (maks 8 per slot)
            |
            v
[ Item Terlempar ke Tanah ]
            |
            v
[ Mod Item Clumps: Penggabungan di Tanah ]
Memindai radius 3.5 blok dan menggabungkan item menjadi satu tumpukan
            |
            v
[ Satu Entitas Berjumlah Besar ] (Tanpa Lag!)
```

---

## 📊 Matriks Pembagian Tugas

| Lapisan Tanggung Jawab | Modul | Manfaat |
| :--- | :--- | :--- |
| **Batas Tumpukan Inventaris** | **Stack Size Adjuster** | Menerapkan batas 64/16/1 di peti & pemain |
| **Spawn Saat Wadah Pecah** | **Stack Size Adjuster** | Mencegah ribuan item keluar bersamaan |
| **Penggabungan di Tanah** | **Item Clumps** | Menyatukan item yang berserakan di tanah |
| **Pengambilan Item** | **Minecraft & Mixins** | Menampung tumpukan besar ke slot yang diperbesar |

---

## ⚙️ Rekomendasi GameRules

Saat bermain dengan **Item Clumps**, atur `max_drop_entities` ke **8** (atau **1** untuk performa maksimal):
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
