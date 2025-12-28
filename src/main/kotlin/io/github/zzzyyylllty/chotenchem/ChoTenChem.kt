package io.github.zzzyyylllty.chotenchem

import io.github.zzzyyylllty.chotenchem.data.ScoreBoard
import io.github.zzzyyylllty.chotenchem.data.ScoreBoardSettings
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessage
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessageSp
import org.bukkit.entity.Player
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.common.util.resettableLazy
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object ChoTenChem : Plugin() {

    val playerData = LinkedHashMap<String, LinkedHashMap<String, String>>()
    val boardMap: MutableMap<String, ScoreBoard> by lazy {
        LinkedHashMap()
    }

    val defaultScoreboardSetting = resettableLazy("config") {
        ScoreBoardSettings(
            title = config.getString("scoreboard.title")?.legacyToMiniMessage() ?: " ",
            lines = config.getMapList("scoreboard.lines").legacyToMiniMessageSp()
        )
    }

    override fun onEnable() {
        info("ChoTenChem enabling...")
    }

    override fun onActive() {
        info("ChoTenChem enabled.")
    }

    @Config("config.yml")
    lateinit var config: Configuration

}
