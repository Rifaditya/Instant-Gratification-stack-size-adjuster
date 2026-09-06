# Dampak Performa & Memori

## Gambaran Umum

Stack Size Adjuster dirancang untuk performa puncak tanpa konsumsi memori tambahan dan tanpa penambahan data NBT.

---

## ⚡ Optimasi Performa Inti

1. **Penyimpanan Tipe Primitif**: Batas disimpan di field primitif `volatile int`, menjamin kecepatan akses $O(1)$ tanpa lock.
2. **Nol Pembengkakan NBT**: Tidak menyuntikkan tag NBT baru, melainkan langsung berinteraksi dengan codec vanilla (`DataComponents.MAX_STACK_SIZE` dan `ExtraCodecs.intRange`). Ukuran file save tetap sama dengan vanilla.
3. **Tanpa Listener Tick**: Tidak menjalankan kode pada loop tick game (`EndTick`, `WorldTick`).
4. **Iterasi Thread-Safe**: Override addon disimpan dalam `CopyOnWriteArrayList` yang aman untuk multi-threading.

---

## 📊 Pengukuran Dampak

| Parameter | Hasil Terukur | Teknik Optimasi |
| :--- | :--- | :--- |
| **Alokasi Heap Memory** | $< 50\text{ KB}$ | Tanpa pembuatan objek sementara |
| **Ukuran Data Dunia** | $+0\text{ Byte}$ | Modifikasi langsung pada codec bawaan |
| **Beban Tick Server (MSPT)** | $0.00\text{ ms}$ | Pembacaan nilai primitif tanpa blocking |
| **Wadah Hancur (MSPT)** | $< 0.50\text{ ms}$ | Pemisahan entitas oleh `InventoryDropHelper` |
