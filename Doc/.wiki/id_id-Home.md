# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

Selamat datang di dokumentasi ensiklopedia resmi untuk **Instant Gratification: Stack Size Adjuster**. Mod Fabric Minecraft ini memberdayakan administrator server dan pemain untuk menyesuaikan batas tumpukan item secara dinamis di tiga kategori alami (item tumpukan 64, 16, dan item tunggal) hingga jumlah ekstrem tanpa pembengkakan NBT, sembari menyediakan optimasi drop wadah dan perlindungan dari luapan.

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup komit terbaru yang belum dirilis sebelum versi publik di CurseForge dan Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Direktori Versi Minecraft

* [[Panduan Minecraft 26.2|id_id-Minecraft-26.2-Guide]] — Instalasi resmi, dependensi, dan panduan pengaturan untuk Minecraft 26.2.
* [[Matriks Kompatibilitas Versi|id_id-Version-Compatibility]] — Versi yang didukung, pemeriksaan `ModVersionGuard`, dan batasan dependensi.

---

## 🎮 Panduan Pemain & Administrator

* [[Referensi GameRules Dinamis|id_id-Dynamic-GameRules-Reference]] — GameRules dalam game (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[Batas Tumpukan Berbasis Kategori|id_id-Category-Based-Stack-Limits]] — Penjelasan penskalaan item tumpukan 64, 16, dan item tunggal.
* [[Optimasi Drop Wadah|id_id-Container-Drop-Optimization]] — Batas spawn entitas, perhitungan pemisahan slot, dan pencegahan lag saat wadah dihancurkan.
* [[Perlindungan Luapan Peti Besar|id_id-Large-Chest-Overflow-Protection]] — Matematika luapan integer (batas aman $39.768.215$) dan logika crafting cepat presisi ganda.
* [[Konfigurasi ModMenu & YACL|id_id-ModMenu-and-YACL-Configuration]] — Antarmuka konfigurasi (`stack-size-adjuster.json`) dan integrasi layar YACL 3.9.5.
* [[Rendering GUI Jumlah Item|id_id-Item-Count-GUI-Rendering]] — Pengecilan font dinamis untuk jumlah tumpukan multi-digit.
* [[Integrasi Item Clumps|id_id-Item-Clumps-Integration]] — Sinergi penggabungan entitas di tanah bersama Item Clumps.
* [[Pemecahan Masalah & Tanya Jawab|id_id-Troubleshooting-and-FAQ]] — Pertanyaan umum, pencegahan luapan, dan sinkronisasi server-klien.

---

## 💻 Referensi Pengembang & Teknis

* [[Pengaturan Pengembang & Kompilasi|id_id-Developer-Setup-and-Building]] — Lingkungan JDK 25, Gradle 9.3+, Loom 1.15+, dan alur kompilasi.
* [[Arsitektur & Tata Letak Paket|id_id-Architecture-and-Package-Layout]] — Pohon arsitektur sistem, struktur paket, dan model keamanan thread.
* [[Referensi Mixin & Hook|id_id-Mixin-Reference-and-Hooks]] — Titik injeksi pada `Item`, `ItemStack`, `Container`, `GiveCommand`, dan `DataComponents`.
* [[API Override Pengaya|id_id-Addon-Override-API]] — Mendaftarkan aturan override tumpukan khusus melalui `StackSizeManager.registerOverride`.
* [[Protokol Sinkronisasi Jaringan|id_id-Network-Sync-Protocol]] — Payload S2C `stack-size-adjuster:sync_limit` dan pembaruan menu instan.
* [[Penanganan Perintah Give|id_id-Give-Command-Handling]] — `GiveCommandHelper` kustom yang menangani jumlah besar tanpa crash.
* [[Profil Perilaku & Kondisi|id_id-Behavior-Profiles-and-Conditions]] — Mesin status sinkronisasi GameRule dinamis.
* [[Panduan Integrasi Mod Konsumen|id_id-Consumer-Mods-Integration-Guide]] — Panduan integrasi lengkap untuk pengembang addon pihak ketiga.
* [[Dampak Performa & Memori|id_id-Performance-and-Memory-Impact]] — Nol pembengkakan NBT dan tolok ukur penggunaan memori.
* [[Kemajuan & Lencana|id_id-Advancements-and-Badges]] — Matriks kemajuan dan pedoman paritas vanilla.

---

## 📜 Hak Cipta & Atribusi

Dikembangkan oleh **Dasik (Rifaditya)** di bawah lisensi **GNU General Public License v3.0 (GPLv3)**.
