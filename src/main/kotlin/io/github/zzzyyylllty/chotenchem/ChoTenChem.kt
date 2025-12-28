package io.github.zzzyyylllty.chotenchem

import io.github.zzzyyylllty.chotenchem.data.Filter
import io.github.zzzyyylllty.chotenchem.data.PapiSetting
import io.github.zzzyyylllty.chotenchem.data.ScoreBoard
import io.github.zzzyyylllty.chotenchem.data.ScoreBoardSettings
import io.github.zzzyyylllty.chotenchem.data.TaskStatText
import io.github.zzzyyylllty.chotenchem.function.asListEnhanded
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessage
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessageSp
import io.github.zzzyyylllty.chotenchem.function.toLines
import org.bukkit.entity.Player
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.common.util.ResettableLazy
import taboolib.common.util.resettableLazy
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import kotlin.text.uppercase

object ChoTenChem : Plugin() {

    val boardMap: MutableMap<String, ScoreBoard> by lazy {
        LinkedHashMap()
    }

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
                    config.getString("placeholders.$key.completed").asListEnhanded() ?: emptyList(),
                    config.getString("placeholders.$key.in-progress").asListEnhanded() ?: emptyList(),
                    config.getString("placeholders.$key.no-progress").asListEnhanded() ?: emptyList()
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
        ResettableLazy.reset("config")
    }

}
