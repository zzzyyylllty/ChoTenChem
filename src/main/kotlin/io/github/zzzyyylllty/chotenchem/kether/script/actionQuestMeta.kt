package io.github.zzzyyylllty.chotenchem.kether.script

import ink.ptms.chemdah.api.ChemdahAPI
import ink.ptms.chemdah.api.ChemdahAPI.conversationSession
import ink.ptms.chemdah.core.quest.QuestContainer
import ink.ptms.chemdah.core.quest.Task
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

class ActionQuestMeta(val str: taboolib.library.kether.ParsedAction<*>?) : ScriptAction<Any?>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Any?> {
        val container = frame.variables().get<Any?>("@QuestContainer").orElse(null) as? QuestContainer ?: error("No quest container selected.")

        return frame.newFrame(str!!).run<Any>().thenApply {
            val str = str.toString()
            return@thenApply container.metaMap[str]?.source
        }
    }

    companion object {

        @KetherParser(["quest-meta"], shared = true, namespace = "chemdah-quest")
        fun parser() = scriptParser {
            ActionQuestMeta(it.nextParsedAction())
        }
    }
}

//class ActionQuestMeta(val str: String) : ScriptAction<Any?>() {
//
//    override fun run(frame: ScriptFrame): CompletableFuture<Any?> {
//        val container = frame.variables().get<Any?>("@QuestContainer").orElse(null) as? QuestContainer ?: error("No quest container selected.")
//        return CompletableFuture.completedFuture(container.metaMap[str]?.source)
//    }
//
//    companion object {
//
//        @KetherParser(["quest-meta"], shared = true, namespace = "chemdah-quest")
//        fun parser() = scriptParser {
//            ActionQuestMeta(it.nextToken())
//        }
//    }
//}

//@KetherParser(["quest-meta"], shared = true, namespace = "chemdah-quest")
//fun actionQuestMeta() = scriptParser {
//    it.group(text()).apply(it) { str ->
//        now {
//            val container = (variables().get<Any?>("@QuestContainer").orElse(null) as? QuestContainer ?: error("No quest container selected."))
//            return@now container.metaMap[str]?.source
//        }
//    }
//}