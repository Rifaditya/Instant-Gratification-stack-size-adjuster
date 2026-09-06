# Profil Perilaku & Kondisi

## Gambaran Umum

Stack Size Adjuster bekerja menggunakan mesin status yang menyelaraskan konfigurasi dunia, GameRules, dan paket jaringan.

---

## 🔄 Diagram Siklus Hidup Mesin Status

```text
               +----------------------------------+
               |    Start Server / Buat Dunia     |
               +----------------------------------+
                                |
                                v
               [ Muat Template Konfigurasi Dasar  ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ Daftarkan GameRules Dinamis      ]
               (`items_64_limit`, `items_16_limit` dll.)
                                |
                +---------------+---------------+
                |                               |
           Dunia Baru                      Dunia Lama
                |                               |
                v                               v
        Terapkan nilai default          Muat GameRules dari
        ke GameRules                    file `level.dat`
                |                               |
                +---------------+---------------+
                                |
                                v
               [ Inisialisasi StackSizeManager   ]
                                |
                                v
               [ Pemain Join / Modifikasi Rule   ]
                                |
                                v
               [ Kirim Paket S2C: sync_limit    ]
                                |
                                v
               [ Segarkan Tampilan Menu Klien    ]
```

---

## ⚙️ Event & Handler Utama

1. **Inisialisasi Server (`ServerLifecycleEvents.SERVER_STARTED`)**:
   - Memuat ulang template konfigurasi.
   - Jika dunia baru, menerapkan nilai awal ke GameRules.
   - Mengisi nilai pada `StackSizeManager`.
2. **Pemain Terhubung (`ServerPlayConnectionEvents.JOIN`)**:
   - Mengirimkan paket `StackSizeLimitSyncPayload` ke pemain.
3. **Perubahan Aturan (`MinecraftServerMixin.onGameRuleChanged`)**:
   - Mendeteksi perubahan aturan bernamespace `stack-size-adjuster:*`.
   - Memperbarui `StackSizeManager`.
   - Mengirim data terkini ke seluruh pemain.
