package io.github.zzzyyylllty.chotenchem.data

import io.github.zzzyyylllty.chotenchem.ChoTenChem.config
import taboolib.common.util.resettableLazy

data class ScoreBoard(
    val title: String,
)

object ScoreBoardSettings {
    var title = resettableLazy("config") {
        config.getString("scoreboard.title") ?: " "
    }
    var simpleComponent = resettableLazy("config") {
        config.getBoolean("scoreboard.simple-component")
    }
    var adventureComponent = resettableLazy("config") {
        config.getBoolean("scoreboard.adventure-component")
    }
    var usePacketEvents = resettableLazy("config") {
        config.getBoolean("scoreboard.packetevents")
    }
}
