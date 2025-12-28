# ChoTenChem

超天集团属 Chemdah 拓展插件

支持 1.21.4+ Paper

考虑到低版本有龙核，不太考虑兼容低版本...<s>除非打钱</s>

**本阿卡糖懒得写wiki，直接看代码吧**

## 过滤器类型

- 全部展示 ALL
- 优先展示未完成 SMART
- 优先展示已完成 RESERVEDSMART
- 只展示未完成 PROGRESS
- 只展示已完成 COMPLETED

## 额外 Papi

* %chotenchem_格式化类型_任务名_任务条目[可选:_输出类型]% - 格式化任务条目，详见config
* %chotenchem_questmeta_任务名[可选:_输出类型]% - 获取任务的 Meta
* %chotenchem_meta_任务名_条目[可选:_输出类型]% - 获取任务条目的 Meta

提示: 任务名为 TRACKING 则获取正在追踪的任务

注意:默认情况下获取任务条目为第X项。如果你要获取固定的任务条目，请使用*taskid。

例如：

* %chotenchem_task_ILoveChoTeN_10% - 所有任务条目转为列表，获取第10项。(从1起数)
* %chotenchem_task_ILoveChoTeN_*10% - 获取名称为"10"的任务条目

## 额外 Kether

* minitell <action> - 以 MiniMessage 形式向执行者发送消息。

## 额外功能

* 为 Chemdah 启用 FancyNPC 支持，可用 FancyNPC 作为 NPC 对话激活者。
* 覆盖追踪计分板 - 开发中
* 新任务UI - 开发中

## 输出类型

* adventure - 转成minimessage格式
* color/colored - 上色

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
