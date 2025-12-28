package io.github.zzzyyylllty.chotenchem.data

import ink.ptms.chemdah.core.PlayerProfile
import ink.ptms.chemdah.core.quest.Quest
import io.github.zzzyyylllty.chotenchem.function.ChemdahPlayerUtil
import io.github.zzzyyylllty.chotenchem.function.ChemdahQuestUtil
import io.github.zzzyyylllty.chotenchem.papi.getTaskInTasks
import io.github.zzzyyylllty.chotenchem.papi.getTaskWithFormat
import org.bukkit.entity.Player


data class PapiSetting(
    val filter: Filter,
    val text: TaskStatText
) {
    fun parse(player: Player, quest: Quest, profile: PlayerProfile, task: String): String? {
        val tasks = when (filter) {
            Filter.ALL -> ChemdahPlayerUtil(player).getTracking()?.let { ChemdahQuestUtil(it) }?.getTasks()
            Filter.SMART -> ChemdahPlayerUtil(player).getTracking()?.let { ChemdahQuestUtil(it) }?.getSmartTasks(profile)
            Filter.RESERVEDSMART -> ChemdahPlayerUtil(player).getTracking()?.let { ChemdahQuestUtil(it) }?.getReservedSmartTasks(profile)
            Filter.PROGRESS -> ChemdahPlayerUtil(player).getTracking()?.let { ChemdahQuestUtil(it) }?.getUncompletedTasks(profile)
        } ?: return null
        val task = tasks.getTaskInTasks(task)
        return task?.buildText(text, player ,quest, profile)?.joinToString("\n")
    }
}