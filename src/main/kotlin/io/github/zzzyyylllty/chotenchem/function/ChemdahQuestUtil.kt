package io.github.zzzyyylllty.chotenchem.function

import ink.ptms.chemdah.api.ChemdahAPI
import ink.ptms.chemdah.core.PlayerProfile
import ink.ptms.chemdah.core.quest.Quest
import ink.ptms.chemdah.core.quest.Task
import ink.ptms.chemdah.core.quest.addon.AddonStats.Companion.getProgress
import org.bukkit.entity.Player

class ChemdahQuestUtil(val quest: Quest) {
    fun getTasks(): LinkedHashMap<String, Task> {
        val tasks = LinkedHashMap<String, Task>()
        quest.tasks.forEach {
            tasks[it.id] = it
        }
        return tasks
    }
    fun getUncompletedTasks(p: PlayerProfile): LinkedHashMap<String, Task> {
        val tasks = LinkedHashMap<String, Task>()
        quest.tasks.forEach {
            if (!it.isCompleted(p)) tasks[it.id] = it
        }
        return tasks
    }
    fun getCompletedTasks(p: PlayerProfile): LinkedHashMap<String, Task> {
        val tasks = LinkedHashMap<String, Task>()
        quest.tasks.forEach {
            if (it.isCompleted(p)) tasks[it.id] = it
        }
        return tasks
    }
    fun getSmartTasks(p: PlayerProfile): LinkedHashMap<String, Task> {
        return quest.tasks
            .sortedWith(compareBy { it.isCompleted(p) })  // false 排在 true 前面
            .associateByTo(LinkedHashMap()) { it.id }
    }
    fun getReservedSmartTasks(p: PlayerProfile): LinkedHashMap<String, Task> {
        return quest.tasks
            .sortedWith(compareBy { !it.isCompleted(p) })  // false 排在 true 前面
            .associateByTo(LinkedHashMap()) { it.id }
    }
}