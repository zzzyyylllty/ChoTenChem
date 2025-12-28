package io.github.zzzyyylllty.chotenchem.data

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player



data class ScoreBoard(
    val title: String,
    val lines: List<String>,
    val settings: ScoreBoardSettings,
    val identifier: String? = null,
    val scores: MutableMap<Int, String>? = null,
    var lock: Boolean = false
) {
}

data class ScoreBoardSettings(
//    var title = resettableLazy("config") {
//        config.getString("scoreboard.title") ?: " "
//    }
//    var simpleComponent = resettableLazy("config") {
//        config.getBoolean("scoreboard.simple-component")
//    }
//    var adventureComponent = resettableLazy("config") {
//        config.getBoolean("scoreboard.adventure-component")
//    }
//    var usePacketEvents = resettableLazy("config") {
//        config.getBoolean("scoreboard.packetevents")
//    }
//    var lines = resettableLazy("config") {
//        config.getMapList("scoreboard.lines")
//    }
    var title: String,
    var lines: List<Map<String, String>?>?,
)
