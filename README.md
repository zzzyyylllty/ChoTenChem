# ChoTenChem

超天集团属 Chemdah 拓展插件

## 额外 Papi

* %chotenchem_格式化类型_任务名_任务条目[可选:_输出类型]% - 格式化任务条目，详见config
* %chotenchem_questmeta_任务名[可选:_输出类型]% - 获取任务的 Meta
* %chotenchem_meta_任务名_条目[可选:_输出类型]% - 获取任务条目的 Meta

## 额外 Kether

* minitell <action> - 以 MiniMessage 形式向执行者发送消息。

## 额外功能

* 为 Chemdah 启用 FancyNPC 支持，可用 FancyNPC 作为 NPC 对话激活者。
* 覆盖追踪计分板 - 开发中
* 新任务UI - 开发中

## 构建发行版本 / 构建在游戏内运行的版本

发行版本用于正常使用, 不含 TabooLib 本体。

```
./gradlew build
```

## 构建开发版本

开发版本包含 TabooLib 本体, 用于开发者使用, 但不可运行。

```
./gradlew taboolibBuildApi -PDeleteCode
```

> 参数 -PDeleteCode 表示移除所有逻辑代码以减少体积。
