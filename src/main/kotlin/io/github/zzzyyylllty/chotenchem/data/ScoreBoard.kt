package io.github.zzzyyylllty.chotenchem.data

import ink.ptms.chemdah.core.PlayerProfile
import ink.ptms.chemdah.core.quest.Quest
import ink.ptms.chemdah.core.quest.Task
import io.github.zzzyyylllty.chotenchem.function.ChemdahPlayerUtil
import io.github.zzzyyylllty.chotenchem.function.ChemdahQuestUtil
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
    var lines: List<Line>?,
) {
    fun build(p: Player, quest: Quest, profile: PlayerProfile): ScoreBoard {
        val linesBuilded = mutableListOf<String>()
        lines?.forEach {
            it.build(p, quest, profile)?.let { elements -> linesBuilded.addAll(elements) }
        }
        return ScoreBoard(
            title = this.title,
            lines = linesBuilded,
            settings = this,
            identifier = p.uniqueId.toString().substring(0,16),
            scores = linkedMapOf(),
            lock = false
        )
    }
}

interface Line {
    val type: String
    fun build(p: Player, quest: Quest, profile: PlayerProfile): List<String>?
}

data class TextLine(
    override val type: String,
    val content: List<String>
) : Line {
    override fun build(p: Player, quest: Quest, profile: PlayerProfile): List<String> {
        return content
    }
}

data class TaskLine(
    override val type: String,
    val maximum: Int?,
    val filter: Filter,
    val text: TaskStatText,
): Line {
    val content = mutableListOf<String>()
    override fun build(p: Player, quest: Quest, profile: PlayerProfile): List<String>? {
        val profile = ChemdahPlayerUtil(p).getProfile() ?: return null
        val tasks =
            when (filter) {
                Filter.ALL -> ChemdahPlayerUtil(p).getTracking()?.let { ChemdahQuestUtil(it) }?.getTasks()
                Filter.SMART -> ChemdahPlayerUtil(p).getTracking()?.let { ChemdahQuestUtil(it) }?.getSmartTasks(profile)
                Filter.RESERVEDSMART -> ChemdahPlayerUtil(p).getTracking()?.let { ChemdahQuestUtil(it) }?.getReservedSmartTasks(profile)
                Filter.PROGRESS -> ChemdahPlayerUtil(p).getTracking()?.let { ChemdahQuestUtil(it) }?.getUncompletedTasks(profile)
            } ?: return null
        tasks.forEach { (key, value) ->
            value.buildText(text, p, quest, profile)?.let { content.addAll(it) }
        }
        return content
    }
}

enum class Filter {
    ALL,
    SMART,
    RESERVEDSMART,
    PROGRESS
}

fun Task.buildText(text: TaskStatText, player: Player, quest: Quest, profile: PlayerProfile): List<String>? {
    val task = this
    return text.build(player, quest, task, profile)
}