package io.github.zzzyyylllty.chotenchem.kether.script

import ink.ptms.chemdah.api.ChemdahAPI
import ink.ptms.chemdah.core.quest.QuestContainer
import ink.ptms.chemdah.core.quest.Task
import taboolib.module.kether.KetherParser
import taboolib.module.kether.scriptParser
import taboolib.module.kether.ScriptAction
import taboolib.module.kether.ScriptFrame
import taboolib.module.kether.player
import java.util.concurrent.CompletableFuture


class ActionMeta(val str: taboolib.library.kether.ParsedAction<*>?) : ScriptAction<Any?>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Any?> {
        val container = frame.variables().get<Any?>("@QuestContainer").orElse(null) as? QuestContainer ?: error("No quest container selected.")

        return frame.newFrame(str!!).run<Any>().thenApply {
            val str = str.toString()
            val future = CompletableFuture<Any>()
            future.complete(
                if (container is Task) {
                    container.metaMap[str]?.source
                } else {
                    val profile =
                        ChemdahAPI.playerProfile[frame.player().name] ?: return@thenApply CompletableFuture.completedFuture(null)
                    val quest = container.getQuest(profile) ?: return@thenApply CompletableFuture.completedFuture(null)
                    quest.template.metaMap[str]
                }
            )
        }
    }

    companion object {

        @KetherParser(["task-meta"], shared = true, namespace = "chemdah-quest")
        fun parser() = scriptParser {
            ActionMeta(it.nextParsedAction())
        }
    }
}

//@KetherParser(["task-meta"], shared = true, namespace = "chemdah-quest")
//fun actionMeta() = scriptParser {
//    it.group(
//        text()).apply(it) { str ->
//        now {
//            val container = (variables().get<Any?>("@QuestContainer").orElse(null) as? QuestContainer ?: error("No quest container selected."))
//            return@now if (container is Task) {
//                container.metaMap[str]?.source
//            } else {
//                val profile = ChemdahAPI.playerProfile[player().name] ?: return@now null
//                val quest = container.getQuest(profile) ?: return@now null
//                quest.template.metaMap[str]
//            }
//        }
//    }
//}