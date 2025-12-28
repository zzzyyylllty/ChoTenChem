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
            if (it.isCompleted(p)) tasks[it.id] = it
        }
        return tasks
    }
    fun getSmartTasks(p: PlayerProfile): LinkedHashMap<String, Task> {
        val (uncompleted, completed) = quest.tasks.partition { !it.isCompleted(p) }

        return (uncompleted + completed)
            .associateByTo(LinkedHashMap()) { it.id }
    }
    fun getTask(id: String): Task? {
        return quest.getTask(id)
    }
}