package io.github.zzzyyylllty.chotenchem

import de.oliver.fancynpcs.api.Npc
import de.oliver.fancynpcs.api.events.NpcInteractEvent
import ink.ptms.chemdah.api.ChemdahAPI.conversationSession
import ink.ptms.chemdah.api.event.collect.ConversationEvents
import ink.ptms.chemdah.core.conversation.ConversationManager
import ink.ptms.chemdah.core.conversation.Source
import io.github.zzzyyylllty.chotenchem.ChoTenChem.config
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.unsafeLazy
import taboolib.module.ai.controllerLookAt

internal object TriggerFancyNPCs {

    val isFancyNPCsHooked by unsafeLazy {
        Bukkit.getPluginManager().isPluginEnabled("FancyNpcs") && config.getBoolean("features.fancynpc-support", false)
    }

    @SubscribeEvent
    fun onBegin(e: ConversationEvents.Begin) {
        if (!isFancyNPCsHooked) {
            return
        }
        val npc = e.session.source
        if (npc.entity is LivingEntity && e.conversation.hasFlag("LOOK_PLAYER")) {
            (npc.entity as LivingEntity).controllerLookAt(e.session.player)
        }
    }

    @SubscribeEvent(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onInteract(e: NpcInteractEvent) {
        if (!isFancyNPCsHooked) {
            return
        }
        if (e.player.conversationSession == null) {
            val mob = e.npc
            // info(mob.data.name) // test
            val conversation = ConversationManager.getConversation(e.player, "fancynpcs", null, mob.data.name)
            if (conversation != null) {
                e.isCancelled = true
                // 打开对话
                conversation.open(e.player, object : Source<Npc>(mob.data.displayName, mob) {
                    override fun getOriginLocation(entity: Npc): Location {
                        return entity.data.location
                    }

                })
            }
        }
    }
}