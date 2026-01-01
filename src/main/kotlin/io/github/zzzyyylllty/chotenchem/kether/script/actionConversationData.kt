package io.github.zzzyyylllty.chotenchem.kether.script

import ink.ptms.chemdah.api.ChemdahAPI
import ink.ptms.chemdah.api.ChemdahAPI.conversationSession
import ink.ptms.chemdah.core.conversation.ConversationSwitch
import ink.ptms.chemdah.core.conversation.Session
import ink.ptms.chemdah.core.quest.QuestContainer
import ink.ptms.chemdah.util.getSession
import ink.ptms.chemdah.util.vars
import io.github.zzzyyylllty.chotenchem.kether.getBukkitPlayer
import org.bukkit.entity.Player
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture


class ActionConversationData(val str: String) : ScriptAction<Any?>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Any?> {
        val session = frame.getBukkitPlayer().conversationSession ?: error("No conversation session selected.")
        val content = when (str.lowercase()) {
            "title" -> session.title
            "conversation-id" -> session.conversation.id
            "conversation-flags" -> session.conversation.flags
            "npc-namespace" -> session.conversation.npcId.id.firstOrNull()?.namespace
            "npc-id" -> session.conversation.npcId.id.firstOrNull()?.value
            "source-name" -> session.source.name
            else -> null
        }
        return CompletableFuture.completedFuture(content)
    }

    companion object {

        @KetherParser(["conversation-data"], shared = true, namespace = "chemdah-conversation")
        fun parser() = scriptParser {
            ActionConversationData(it.nextToken())
        }
    }
}

//@KetherParser(["conversation-data"], shared = true, namespace = "chemdah-conversation")
//fun actionConversationData() = scriptParser {
//    val str = it.nextToken()
//            val session = str.rootFrame().rootVariables().getBukkitPlayer().conversationSession ?: error("no session selected!")
//            return when (str.lowercase()) {
//                "title" -> session.title
//                "conversation-id" -> session.conversation.id
//                "conversation-flags" -> session.conversation.flags
//                "npc-namespace" -> session.conversation.npcId.id.firstOrNull()?.namespace
//                "npc-id" -> session.conversation.npcId.id.firstOrNull()?.value
//                else -> null
//    }
//}