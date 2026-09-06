# ModMenu および YACL 設定

## 概要

Stack Size Adjuster は、リフレクション（`GuiHelper.getOptionalYaclFactory`）を用いて **ModMenu** および **YetAnotherConfigLib (YACL v3)** による設定画面を提供しており、YACL が導入されていない専用サーバーでもクラスローダーのクラッシュを引き起こしません。

---

## ⚙️ 設定ファイル仕様

* **ファイルパス**: `config/stack-size-adjuster.json`
* **形式**: スキーマバージョン付き JSON。

### JSON スキーマとデフォルト値：
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

## 🖥️ YACL 3.9.5 画面構築構造

クライアント設定画面は `YaclScreenHelper` 内で構築されます：

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

### 安全警告ツールチップ
$39,768,215$ を超える値を入力した場合、YACL はラージチェストのオーバーフローリスクを警告するツールチップを動的に表示します。
