# ModMenu 與 YACL 設定

## 概述

Stack Size Adjuster 透過 **ModMenu** 與 **YetAnotherConfigLib (YACL v3)** 提供可選的客戶端設定介面，並透過反射門禁（`GuiHelper.getOptionalYaclFactory`）進行嚴格隔離。這可確保在沒有安裝 YACL 或 ModMenu 的獨立伺服端上執行時絕不發生類別載入當機。

---

## ⚙️ 設定檔規範

* **路徑**：`config/stack-size-adjuster.json`
* **格式**：帶版本追蹤的 JSON 檔案。

### JSON Schema 與預設數值：
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

## 🖥️ YACL 3.9.5 介面構建架構

客戶端設定介面在 `YaclScreenHelper` 中透過反射構建：

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

### 安全警告提醒機制
當使用者在輸入框中填寫的堆疊上限超過 $39,768,215$ 時，YACL 會動態呈現醒目警示懸浮提示，提醒玩家潛在的大箱子整數溢位風險。
