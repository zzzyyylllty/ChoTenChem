package io.github.zzzyyylllty.chotenchem.data

import ink.ptms.chemdah.core.PlayerProfile
import ink.ptms.chemdah.core.quest.Quest
import ink.ptms.chemdah.core.quest.Task
import ink.ptms.chemdah.core.quest.addon.AddonStats.Companion.getProgress
import io.github.zzzyyylllty.chotenchem.function.ChemdahPlayerUtil
import io.github.zzzyyylllty.chotenchem.function.ChemdahQuestUtil
import org.bukkit.entity.Player


data class PapiSetting(
    val filter: Filter,
    val text: TaskStatText
) {
    fun parse(player: Player, quest: Quest, profile: PlayerProfile, task: String): String? {
        val tasks = when (filter) {
            Filter.ALL -> ChemdahPlayerUtil(player).getTracking()?.let { ChemdahQuestUtil(it) }?.getTasks()
            Filter.SMART -> ChemdahPlayerUtil(player).getTracking()?.let { ChemdahQuestUtil(it) }?.getSmartTasks(profile)
            Filter.PROGRESS -> ChemdahPlayerUtil(player).getTracking()?.let { ChemdahQuestUtil(it) }?.getUncompletedTasks(profile)
        } ?: return null
        val task = tasks[task] ?: return null
        return task.buildText(text, player ,quest, profile)?.joinToString("\n")
    }
}