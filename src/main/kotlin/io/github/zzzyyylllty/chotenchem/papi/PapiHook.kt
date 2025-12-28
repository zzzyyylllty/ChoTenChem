package io.github.zzzyyylllty.chotenchem.papi

import ink.ptms.chemdah.core.quest.meta.Meta
import io.github.zzzyyylllty.chotenchem.ChoTenChem.papiSetting
import io.github.zzzyyylllty.chotenchem.data.Filter
import io.github.zzzyyylllty.chotenchem.data.buildText
import io.github.zzzyyylllty.chotenchem.function.ChemdahPlayerUtil
import io.github.zzzyyylllty.chotenchem.function.ChemdahQuestUtil
import io.github.zzzyyylllty.chotenchem.function.legacyToMiniMessage
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored
import taboolib.platform.compat.PlaceholderExpansion
import taboolib.platform.compat.replacePlaceholder
import kotlin.math.round

object ExamplePapiHook : PlaceholderExpansion {

    override val identifier: String = "chotenchem"

    // 处理在线玩家请求
    override fun onPlaceholderRequest(player: Player?, args: String): String {

        var list = args.split("_")

        val key = list[0].toLowerCase()

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
                    val text = quest.getTask(task)?.template?.meta<Meta<String>>(list[3])?.source ?: return ""
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
