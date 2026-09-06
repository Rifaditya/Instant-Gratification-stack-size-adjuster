# ModMenu 与 YACL 配置

## 概述

Stack Size Adjuster 通过 **ModMenu** 与 **YetAnotherConfigLib (YACL v3)** 提供可选的客户端配置界面，并通过反射门禁（`GuiHelper.getOptionalYaclFactory`）进行严格隔离。这可确保在没有安装 YACL 或 ModMenu 的独立服务端上运行时绝不发生类加载崩溃。

---

## ⚙️ 配置文件规范

* **路径**：`config/stack-size-adjuster.json`
* **格式**：带版本追踪的 JSON 文件。

### JSON Schema 与默认数值：
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

## 🖥️ YACL 3.9.5 界面构建架构

客户端配置界面在 `YaclScreenHelper` 中通过反射构建：

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

### 安全警告提醒机制
当用户在输入框中填写的堆叠上限超过 $39,768,215$ 时，YACL 会动态呈现高亮警示悬浮提示，提醒玩家潜在的大箱子整数溢出风险。
