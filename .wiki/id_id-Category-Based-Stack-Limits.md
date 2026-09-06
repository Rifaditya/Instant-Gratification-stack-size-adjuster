# Batas Tumpukan Berbasis Kategori

## Gambaran Sistem

Minecraft secara alami membagi item menjadi tiga tingkatan tumpukan melalui `DataComponents.MAX_STACK_SIZE`:
1. **Tumpukan 64**: Blok bangunan, sumber daya, item umum (cobblestone, tanah, besi batangan).
2. **Tumpukan 16**: Ender pearl, bola salju, ember, telur, papan tanda.
3. **Item Tunggal (1 per tumpukan)**: Alat, senjata, baju zirah, ramuan, pelana, kereta tambang.

---

## 🧮 Alur Penentuan Ukuran Tumpukan

```
                 +--------------------------------+
                 |    Perhitungan Ukuran Stack    |
                 +--------------------------------+
                                  |
                                  v
                    [ Periksa Override Terdaftar ]
                     (misalnya Potion Stacker)
                                  |
                 +----------------+----------------+
                 |                                 |
         Override Ditemukan?                Tanpa Override
                 |                                 |
                 v                                 v
        Kembalikan Batas Custom           Periksa Default Vanilla
                                             (DataComponents)
                                                   |
                     +-----------------------------+-----------------------------+
                     |                             |                             |
                Vanilla >= 64                 Vanilla >= 16                 Vanilla == 1
                     |                             |                             |
                     v                             v                             v
           Ambil `items_64_limit`        Ambil `items_16_limit`        Ambil `items_1_limit`
```

---

## 💻 Logika Inti di Java

Perhitungan ukuran tumpukan ditangani oleh `StackSizeManager.getModifiedStackSize`:

```java
public static int getModifiedStackSize(Item item, int original) {
    if (original <= 0) {
        return original;
    }

    // Terapkan override terdaftar dari addon (misalnya Potion Stacker)
    int size = original;
    for (BiFunction<Item, Integer, Integer> override : OVERRIDES) {
        size = override.apply(item, size);
    }
    if (size != original) {
        return size;
    }

    if (original >= 64) {
        return limit64;
    } else if (original >= 16) {
        return limit16;
    } else if (original == 1) {
        return limit1;
    }
    return original;
}
```

---

## 🛠️ Rekomendasi Batas Kategori

| Kategori Target | Default | Batas Maksimum Rekomendasi | Profil Kinerja |
| :--- | :--- | :--- | :--- |
| **Tumpukan 64** | `128` | $39.768.215$ | Sangat efisien. Jumlah jutaan item berjalan mulus. |
| **Tumpukan 16** | `32` | $39.768.215$ | Penskalaan stabil untuk pearl dan telur. |
| **Item Tunggal** | `1` | $39.768.215$ | Memungkinkan ramuan/alat ditumpuk secara aman. |
