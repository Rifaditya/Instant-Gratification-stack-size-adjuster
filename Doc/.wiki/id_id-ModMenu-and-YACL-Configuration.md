# Konfigurasi ModMenu & YACL

## Gambaran Umum

Stack Size Adjuster menyediakan menu pengaturan visual melalui **ModMenu** dan **YetAnotherConfigLib (YACL v3)** menggunakan refleksi (`GuiHelper.getOptionalYaclFactory`), menjaga server terdedikasi tetap aman tanpa crash loader.

---

## ⚙️ Spesifikasi File Konfigurasi

* **Lokasi**: `config/stack-size-adjuster.json`
* **Format**: JSON dengan penanda versi skema.

### Skema JSON & Nilai Default:
```json
{
  "configVersion": 1,
  "items64Limit": 128,
  "items16Limit": 32,
  "items1Limit": 1,
  "maxDropEntities": 8
}
```

---

## 🖥️ Arsitektur Layar YACL 3.9.5

Layar antarmuka klien disusun secara reflektif pada `YaclScreenHelper`:

```java
public class YaclScreenHelper {
    public static ConfigScreenFactory<?> createScreen() {
        return YaclScreenHelper::buildScreen;
    }

    private static Screen buildScreen(Screen parent) {
        StackSizeConfig config = StackSizeConfig.get();

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("config.stacksizeadjuster.title"))
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.stacksizeadjuster.category.general"))
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.stacksizeadjuster.group.categories"))
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.stacksizeadjuster.option.limit_64"))
                        .description(val -> {
                            if (val > 39768215) {
                                return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.warning", val));
                            }
                            return OptionDescription.of(Component.translatable("config.stacksizeadjuster.option.limit_64.description"));
                        })
                        .binding(128, () -> config.items64Limit, val -> config.items64Limit = val)
                        .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(Integer.MAX_VALUE))
                        .build()
                    )
                    ...
                )
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.stacksizeadjuster.group.container_drops"))
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.stacksizeadjuster.option.max_drop_entities"))
                        .binding(8, () -> config.maxDropEntities, val -> config.maxDropEntities = val)
                        .customController(opt -> new IntegerSliderController(opt, 1, 64, 1))
                        .build()
                    )
                )
                .build()
            )
            .save(StackSizeConfig::save)
            .build()
            .generateScreen(parent);
    }
}
```

### Peringatan Keamanan
Jika nilai yang dimasukkan melebihi $39.768.215$, YACL akan menampilkan kotak peringatan mengenai potensi luapan integer pada Peti Besar.
