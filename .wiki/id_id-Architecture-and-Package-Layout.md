# Arsitektur & Tata Letak Paket

## Prinsip 1 File, 1 Tujuan

Stack Size Adjuster memegang teguh prinsip tanggung jawab tunggal. Setiap kelas difokuskan pada satu tugas mandiri: sinkronisasi, konfigurasi, pemecahan drop, atau visual rendering.

---

## 🌳 Struktur Paket ASCII

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Titik masuk Fabric & registrasi GameRules
├── StackSizeAdjusterFabricClient.java # Titik masuk klien & penerima paket S2C
├── config/
│   ├── ModMenuIntegration.java        # Integrasi ModMenu
│   ├── StackSizeConfig.java           # Model konfigurasi JSON (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # Pembangun UI YACL 3.9.5 berbasis refleksi
├── mixin/
│   ├── AbstractContainerMenuMixin.java# Kalkulasi crafting presisi ganda
│   ├── ContainerMixin.java            # Batas slot kontainer (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Pencegatan Containers.dropItemStack
│   ├── DataComponentsMixin.java       # Pelebaran codec DataComponents MAX_STACK_SIZE
│   ├── GiveCommandMixin.java          # Pencegatan perintah /give
│   ├── ItemInstanceMixin.java         # Hook ItemInstance.getMaxStackSize
│   ├── ItemMixin.java                 # Hook Item.getDefaultMaxStackSize
│   ├── ItemStackMixin.java            # Pengalihan rentang ItemStack.getMaxStackSize
│   ├── ItemStackTemplateMixin.java    # Pengalihan rentang codec ItemStackTemplate
│   ├── MinecraftServerMixin.java      # Pemantau perubahan GameRule server
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # Penyesuaian rendering font slot
├── network/
│   └── StackSizeLimitSyncPayload.java # Record data S2C (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # Logika /give dengan pengali keamanan 100x
    ├── InventoryDropHelper.java       # Logika pembagian drop saat wadah hancur
    ├── ItemCountRenderer.java         # Renderer pengecilan matriks font
    ├── ModVersionGuard.java           # Verifikasi lingkungan runtime
    └── StackSizeManager.java          # Pengelola limit thread-safe & registrasi override
```

---

## 🔒 Model Konkurensi Thread

* **Operasi Baca**: `StackSizeManager.getModifiedStackSize` bebas dari lock melalui variabel `volatile int` dan `CopyOnWriteArrayList`.
* **Sinkronisasi Jaringan**: Paket dikirim pada thread utama server dan diproses pada thread klien via `context.client().execute(...)`.
