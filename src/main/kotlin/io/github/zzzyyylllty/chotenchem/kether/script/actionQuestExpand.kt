package io.github.zzzyyylllty.chotenchem.kether.script

import ink.ptms.chemdah.api.ChemdahAPI.chemdahProfile
import ink.ptms.chemdah.api.ChemdahAPI.isChemdahProfileLoaded
import ink.ptms.chemdah.core.quest.QuestContainer
import ink.ptms.chemdah.util.getBukkitPlayer
import ink.ptms.chemdah.util.getProfile
import ink.ptms.chemdah.util.getQuestSelected
import io.github.zzzyyylllty.chotenchem.kether.getBukkitPlayer
import taboolib.module.kether.KetherParser
import taboolib.module.kether.ScriptAction
import taboolib.module.kether.ScriptFrame
import taboolib.module.kether.actionNow
import taboolib.module.kether.scriptParser
import taboolib.module.kether.switch
import java.util.concurrent.CompletableFuture


class ActionQuestExpand(val str: String) : ScriptAction<Any?>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Any?> {
        val profile = if (frame.getBukkitPlayer().isChemdahProfileLoaded) frame.getBukkitPlayer().chemdahProfile else null
        return frame.newFrame(str).run<Any>().thenApply {
            when (it) {
                "unaccepted" -> {
                    profile?.isQuestCompleted(frame.getQuestSelected()) == false && (profile.getQuestById(frame.getQuestSelected()) == null)
                }
                else -> null
            }
        }
    }

    companion object {

        @KetherParser(["c-quest"], shared = true, namespace = "chemdah-quest")
        fun parser() = scriptParser {
            ActionQuestExpand(it.nextToken())
        }
    }
}

fun ScriptFrame.getQuestSelected(): String {
    return variables().get<Any?>("@QuestSelected").orElse(null)?.toString() ?: error("No quest selected.")
}