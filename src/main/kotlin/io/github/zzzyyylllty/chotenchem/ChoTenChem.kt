package io.github.zzzyyylllty.chotenchem

import io.github.zzzyyylllty.chotenchem.data.Filter
import io.github.zzzyyylllty.chotenchem.data.PapiSetting
import io.github.zzzyyylllty.chotenchem.data.ScoreBoard
import io.github.zzzyyylllty.chotenchem.data.ScoreBoardSettings
import io.github.zzzyyylllty.chotenchem.data.TaskStatText
import io.github.zzzyyylllty.chotenchem.function.asListEnhanced
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessage
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessageSp
import io.github.zzzyyylllty.chotenchem.function.toLines
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.info
import taboolib.common.util.ResettableLazy
import taboolib.common.util.resettableLazy
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import kotlin.text.uppercase
@RuntimeDependency(
    value = "!com.google.code.gson:gson:2.10.1",
    relocate = ["!com.google.gson", "!com.example.library.gson"]
)
object ChoTenChem : Plugin() {

    val boardMap: MutableMap<String, ScoreBoard> by lazy {
        LinkedHashMap()
    }

    val console by lazy { console() }

    val defaultScoreboardSetting = resettableLazy("config") {
        ScoreBoardSettings(
            title = config.getString("scoreboard.title")?.legacyToMiniMessage() ?: " ",
            lines = config.getMapList("scoreboard.lines").legacyToMiniMessageSp().toLines()
        )
    }

    val papiSetting = resettableLazy("config") {
        val map = linkedMapOf<String, PapiSetting>()
        for (key in config.getConfigurationSection("placeholders")?.getKeys(false) ?: emptyList()) {
            map[key] = PapiSetting(
                Filter.valueOf((config.getString("placeholders.$key.filter") ?: "ALL").uppercase()),
                TaskStatText (
                    config.getString("placeholders.$key.completed").asListEnhanced() ?: emptyList(),
                    config.getString("placeholders.$key.in-progress").asListEnhanced() ?: emptyList(),
                    config.getString("placeholders.$key.no-progress").asListEnhanced() ?: emptyList()
                )
            )
        }
        map
    }

    override fun onEnable() {
        info("ChoTenChem enabling...")
    }

    override fun onActive() {
        info("ChoTenChem enabled.")
    }

    @Config("config.yml")
    lateinit var config: Configuration

    fun reloadChoTenChem() {
        config.reload()
        ResettableLazy.reset("config")
    }

}
