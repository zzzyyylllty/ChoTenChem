package io.github.zzzyyylllty.chotenchem.kether.script

import ink.ptms.chemdah.util.getSession
import ink.ptms.chemdah.api.ChemdahAPI
import ink.ptms.chemdah.api.ChemdahAPI.conversationSession
import ink.ptms.chemdah.core.quest.QuestContainer
import ink.ptms.chemdah.taboolib.library.kether.ParsedAction
import ink.ptms.chemdah.util.getQuestContainer
import io.github.zzzyyylllty.chotenchem.kether.getBukkitPlayer
import taboolib.module.kether.KetherParser
import taboolib.module.kether.ParserHolder.now
import taboolib.module.kether.ParserHolder.text
import taboolib.module.kether.scriptParser
import taboolib.module.kether.ScriptAction
import taboolib.module.kether.ScriptFrame
import taboolib.module.kether.combinationParser
import taboolib.module.kether.player
import taboolib.module.kether.script
import java.util.concurrent.CompletableFuture
import kotlin.math.abs

class ActionQuestInstanceData(val str: String) : ScriptAction<Any?>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Any?> {
        val container = frame.variables().get<Any?>("@QuestContainer").orElse(null) as? QuestContainer ?: error("No quest container selected.")
        val content = when (str.lowercase()) {
            "id" -> container.id
            "node" -> container.node
            "path" -> container.path
            else -> null
        }
        return CompletableFuture.completedFuture(content)
    }

    companion object {

        @KetherParser(["quest-instance-data"], shared = true, namespace = "chemdah-quest")
        fun parser() = scriptParser {
            ActionQuestInstanceData(it.nextToken())
        }
    }
}

//@KetherParser(["quest-instance-data"], shared = true, namespace = "chemdah-quest")
//fun actionQuestInstanceData() = scriptParser {
//    it.group(text()).apply(it) { str ->
//        now {
//            val container = (variables().get<Any?>("@QuestContainer").orElse(null) as? QuestContainer ?: error("No quest container selected."))
//            val profile = ChemdahAPI.playerProfile[player().name] ?: return@now null
//            val quest = container.getQuest(profile) ?: return@now null
//            return@now when (str.lowercase()) {
//                "start-time" -> quest.startTime
//                "isFreeze" -> quest.isFreeze
//                "isCompleted" -> quest.isCompleted
//                "isValid" -> quest.isValid
//                "isTimeout" -> quest.isTimeout
//                else -> null
//            }
//        }
//    }
//}