package io.github.zzzyyylllty.chotenchem.papi

import ink.ptms.chemdah.core.quest.Quest
import ink.ptms.chemdah.core.quest.Task
import ink.ptms.chemdah.core.quest.meta.Meta
import io.github.zzzyyylllty.chotenchem.ChoTenChem.papiSetting
import io.github.zzzyyylllty.chotenchem.function.ChemdahPlayerUtil
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessage
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.module.chat.colored
import taboolib.platform.compat.PlaceholderExpansion

object ChoTenChemPapiHook : PlaceholderExpansion {

    override val identifier: String = "chotenchem"

    // 处理在线玩家请求
    override fun onPlaceholderRequest(player: Player?, args: String): String {

        var list = args.split("_")

        val key = list[0].lowercase()

        when (key) {
            "questmeta" -> {
                if (list.size >= 3) {
                    if (player == null) return "PLAYER_IS_NULL"
                    val profile = ChemdahPlayerUtil(player).getProfile() ?: return ""
                    val quest = list[1].let {
                        if (it == "TRACKING") ChemdahPlayerUtil(player).getTracking() else ChemdahPlayerUtil(player).getQuest(it)
                    } ?: return ""
                    val text = quest.template.meta<Meta<String>>(list[2])?.source ?: return ""
                    val format = if (list.size >= 4) list[3] else null
                    return when (format) {
                        "adventure" -> text.legacyToMiniMessage()
                        "color", "colored" -> text.colored()
                        null -> text
                        else -> text
                    }
                } else {
                    return "LENGTH_NOT_ENOUGH"
                }
            }
            "meta" -> {
                if (list.size >= 4) {
                    if (player == null) return "PLAYER_IS_NULL"
                    val profile = ChemdahPlayerUtil(player).getProfile() ?: return ""
                    val quest = list[1].let {
                        if (it == "TRACKING") ChemdahPlayerUtil(player).getTracking() else ChemdahPlayerUtil(player).getQuest(it)
                    } ?: return ""
                    val task = list[2]
                    val text = quest.getTaskWithFormat(task)?.template?.meta<Meta<String>>(list[3])?.source ?: return ""
                    val format = if (list.size >= 5) list[4] else null
                    return when (format) {
                        "adventure" -> text.legacyToMiniMessage()
                        "color", "colored" -> text.colored()
                        null -> text
                        else -> text
                    }
                } else {
                    return "LENGTH_NOT_ENOUGH"
                }
            }
        }
        if (list.size >= 3) {
            if (player == null) return "PLAYER_IS_NULL"
            val profile = ChemdahPlayerUtil(player).getProfile() ?: return ""
            val quest = list[1].let {
                if (it == "TRACKING") ChemdahPlayerUtil(player).getTracking() else ChemdahPlayerUtil(player).getQuest(it)
            } ?: return ""
            val task = list[2]
            val text = papiSetting.value[key]?.parse(player, quest, profile, task) ?: return ""
            val format = if (list.size >= 4) list[3] else null
            return when (format) {
                "adventure" -> text.legacyToMiniMessage()
                "color", "colored" -> text.colored()
                null -> text
                else -> text
            }
        } else {
            return "LENGTH_NOT_ENOUGH"
        }

    }

    // 处理离线玩家请求
    override fun onPlaceholderRequest(player: OfflinePlayer?, args: String): String {
        return onPlaceholderRequest(player?.player, args)
    }
}

fun Quest.getTaskWithFormat(task: String): Task? {

    if (task.startsWith("*")) {
        return this.getTask(task.substring(1))
    } else {
        val id =
            try {
                task.toIntOrNull() ?: return null
            } catch (e: NumberFormatException) {
                return this.getTask(task.substring(1))
            }
        val list = tasks.toList()
        return if (list.size >= id) {
            list[id-1]
        } else null
    }
}
fun LinkedHashMap<String, Task>.getTaskInTasks(task: String): Task? {

    if (task.startsWith("*")) {
        return this.get(task.substring(1))
    } else {
        val id =
            try {
                task.toIntOrNull() ?: return null
            } catch (e: NumberFormatException) {
                return this.get(task.substring(1))
            }
        val list = this.toList()
        return if (list.size >= id) {
            list[id-1].second
        } else null
    }
}