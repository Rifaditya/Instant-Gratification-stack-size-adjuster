# ModMenu 및 YACL 구성

## 개요

Stack Size Adjuster는 리플렉션(`GuiHelper.getOptionalYaclFactory`)을 적용하여 **ModMenu** 및 **YetAnotherConfigLib (YACL v3)** 기반 설정 화면을 제공하므로, 전용 서버 환경에서도 안전하게 로드됩니다.

---

## ⚙️ 설정 파일 사양

* **경로**: `config/stack-size-adjuster.json`
* **형식**: 스키마 버전 관리가 적용된 JSON.

### JSON 스키마 및 기본값:
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

## 🖥️ YACL 3.9.5 화면 구성

클라이언트 설정 화면은 `YaclScreenHelper`에서 동적으로 구성됩니다:

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

### 안전 경고 툴팁
$39,768,215$보다 큰 값을 입력하면 대형 상자 오버플로 위험을 경고하는 안내 툴팁이 표시됩니다.
