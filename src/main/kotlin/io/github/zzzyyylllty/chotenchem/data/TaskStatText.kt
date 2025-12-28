package io.github.zzzyyylllty.chotenchem.data

import ink.ptms.chemdah.core.PlayerProfile
import ink.ptms.chemdah.core.quest.Quest
import ink.ptms.chemdah.core.quest.Task
import ink.ptms.chemdah.core.quest.addon.AddonStats.Companion.getProgress
import ink.ptms.chemdah.core.quest.meta.Meta
import io.github.zzzyyylllty.chotenchem.function.jsonUtils
import io.github.zzzyyylllty.chotenchem.kether.evalKetherString
import org.bukkit.entity.Player
import taboolib.platform.compat.replacePlaceholder
import kotlin.collections.component1
import kotlin.collections.component2

data class TaskStatText(
    val completed: List<String>,
    val inProgress: List<String>,
    val noProgress: List<String>
) {
    fun build(player: Player, quest: Quest, task: Task, profile: PlayerProfile): List<String>? {
        val texts =
            if (task.isCompleted(profile)) completed
            else if (task.getProgress(profile).get().isZero()) noProgress
            else inProgress
        val json = jsonUtils.toJson(texts).replacePlaceholder(player)
        val parsed = parseQuestPlaceholders(json, player, quest, task, profile)
        return jsonUtils.fromJson<List<String>>(parsed, List::class.java)
    }
}

fun Any.isZero(): Boolean {
    return if (this is Number) this.toDouble() == 0.0
    else if (this is String) this.toDouble() == 0.0
    else false
}

fun parseQuestPlaceholders(json: String, player: Player, quest: Quest, task: Task, profile: PlayerProfile): String {
    var json = json
    // var json = jsonUtils.toJson(this).replacePlaceholder(player)
    val repl = solvePlaceholders(json, player, quest, task ,profile)
    repl.forEach { (key, value) ->
        json = if (value != "null" && value != null) {
            // 如果替换的值不是空的
            json
                .replace("{$key}", value)
                .replace("\"{$key}\"", value)
        } else if (!key.endsWith("!!")) {
            // 如果key不被标记为非空，即可空，即数值为null时也会被替换成null。
            json
                .replace("{$key}", "null")
                .replace("\"{$key}\"", "null")
        } else {
            // 如果key被标记为非空，即数值为null时也会被替换成空字符串。
            json.replace("{$key}", "")
                .replace("\"{$key}\"", "")
        }
    }
    return json
    // return jsonUtils.fromJson<TaskStatText>(json, TaskStatText::class.java)
}


fun extractPlaceholders(input: String): Map<String, String?> {
    val regex = "\\{(.*?)}".toRegex() // 匹配 {xxx} 的正则表达式
    val result = mutableMapOf<String, String?>() // 创建一个Map来存储结果

    regex.findAll(input).forEach { matchResult ->
        val key = matchResult.groupValues[1]
        result[key] = null
    }

    return result
}

data class MetaInfo(
    val meta: String,
    val value: String? = null,
    val def: String? = null
)

fun solvePlaceholders(str: String, player: Player, quest: Quest, task: Task, profile: PlayerProfile): Map<String, String?> {

    val map = mutableMapOf<String, String?>()
    val query = extractPlaceholders(str)

    query.forEach { (key, value) ->

        val input = key.removeSuffix("!!")

        val parts = input.split("?:")

        val meta = when (parts.size) {
            1 -> {
                // 没有 "?:" 分隔符，可能是 "meta" 或 "meta:value"
                val subParts = parts[0].split(":", limit = 2)
                MetaInfo(
                    meta = subParts[0],
                    value = subParts.getOrNull(1)?.takeIf { it.isNotEmpty() }
                )
            }
            2 -> {
                // 有 "?:" 分隔符，格式是 "meta:value?:def"
                val subParts = parts[0].split(":", limit = 2)
                MetaInfo(
                    meta = subParts[0],
                    value = subParts.getOrNull(1)?.takeIf { it.isNotEmpty() },
                    def = parts[1].takeIf { it.isNotEmpty() }
                )
            }
            else -> MetaInfo(meta = input)
        }

        val result = when (meta.meta) {
            "meta" -> meta.value?.let { task.meta<Meta<String>>(it)?.source }
            "quest_meta" -> meta.value?.let { quest.template.metaMap[it]?.source.toString() }
            "value" -> meta.value
            "kether" -> meta.value?.evalKetherString(
                player,
                mapOf("@QuestSelected" to quest.id)
            )
            "progress" -> task.getProgress(profile).get().value.toString().removeSuffix(".0")
            "target" -> task.getProgress(profile).get().target.toString().removeSuffix(".0")
            "percent" -> task.getProgress(profile).get().percent.toString().removeSuffix(".0")
            else -> null
        } ?: meta.value

         map[key] = result
    }

    return map
}