package io.github.zzzyyylllty.chotenchem.kether.script

import ink.ptms.chemdah.api.ChemdahAPI.conversationSession
import ink.ptms.chemdah.taboolib.library.kether.ParsedAction
import io.github.zzzyyylllty.chotenchem.kether.getBukkitPlayer
import taboolib.module.kether.KetherParser
import taboolib.module.kether.ParserHolder.now
import taboolib.module.kether.ParserHolder.text
import taboolib.module.kether.scriptParser
import taboolib.module.kether.ScriptAction
import taboolib.module.kether.ScriptFrame
import taboolib.module.kether.combinationParser
import taboolib.module.kether.script
import java.util.concurrent.CompletableFuture
import kotlin.math.abs

@KetherParser(["conversation-data"], shared = true, namespace = "chemdah-conversation")
fun actionConversationData() = combinationParser {
    it.group(text()).apply(it) { str ->
        now {
            val session = script().rootFrame().getBukkitPlayer().conversationSession ?: error("no session selected!")
            return@now when (str.lowercase()) {
                "title" -> session.title
                "conversation-id" -> session.conversation.id
                "conversation-flags" -> session.conversation.flags
                "npc-namespace" -> session.conversation.npcId.id.firstOrNull()?.namespace
                "npc-id" -> session.conversation.npcId.id.firstOrNull()?.value
                else -> null
            }
        }
    }
}