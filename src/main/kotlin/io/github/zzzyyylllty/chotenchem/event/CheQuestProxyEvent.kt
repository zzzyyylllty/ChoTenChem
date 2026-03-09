@file:Suppress("UNUSED")
package io.github.zzzyyylllty.chotenchem.event

import ink.ptms.chemdah.api.event.collect.QuestEvents
import ink.ptms.chemdah.core.Data
import ink.ptms.chemdah.core.DataContainer
import ink.ptms.chemdah.core.PlayerProfile
import ink.ptms.chemdah.core.conversation.Conversation
import ink.ptms.chemdah.core.conversation.PlayerReply
import ink.ptms.chemdah.core.conversation.Session
import ink.ptms.chemdah.core.quest.AgentType
import ink.ptms.chemdah.core.quest.Quest
import ink.ptms.chemdah.core.quest.QuestContainer
import ink.ptms.chemdah.core.quest.Template
import org.bukkit.entity.Player
import taboolib.common.platform.event.SubscribeEvent
import ink.ptms.chemdah.core.quest.Task
import ink.ptms.chemdah.core.quest.objective.Objective
import ink.ptms.chemdah.module.level.LevelOption
import ink.ptms.chemdah.module.scenes.ScenesBlockData
import taboolib.platform.type.BukkitProxyEvent
import java.util.concurrent.CompletableFuture


// --- Agent ---
class CheProxyAgent(val questContainer: QuestContainer, val playerProfile: PlayerProfile, val agentType: AgentType, val restrict: String) : BukkitProxyEvent()

// --- Accept ---
class CheProxyAcceptPre(val quest: Template, val playerProfile: PlayerProfile, var reason: String?) : BukkitProxyEvent()
class CheProxyAcceptPost(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()

// --- Fail ---
class CheProxyFailPre(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyFailPost(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()

// --- Restart ---
class CheProxyRestartPre(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyRestartPost(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()

// --- Complete ---
class CheProxyCompletePre(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyCompletePost(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()

// --- Registration ---
class CheProxyRegistered(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyUnregistered(val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()

// --- Scoreboard ---
class CheProxyScoreboardTrack(val content: MutableList<String>, val playerProfile: PlayerProfile) : BukkitProxyEvent()

// --- DataSet ---
class CheProxyDataSetPre(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String, var value: Data) : BukkitProxyEvent()
class CheProxyDataSetPost(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String, val value: Data) : BukkitProxyEvent()

// --- DataRemove ---
class CheProxyDataRemovePre(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String) : BukkitProxyEvent()
class CheProxyDataRemovePost(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String) : BukkitProxyEvent()

// --- Objective Events ---
class CheProxyObjectiveContinuePre(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyObjectiveContinuePost(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyObjectiveCompletePre(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyObjectiveCompletePost(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyObjectiveRestartPre(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyObjectiveRestartPost(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : BukkitProxyEvent()

// --- Conversation Events ---
class CheProxyConvSelectReply(val player: Player, val session: Session, val reply: PlayerReply) : BukkitProxyEvent()
class CheProxyConvPre(val conversation: Conversation, val session: Session, val relay: Boolean) : BukkitProxyEvent()
class CheProxyConvPost(val conversation: Conversation, val session: Session, val relay: Boolean) : BukkitProxyEvent()
class CheProxyConvBegin(val conversation: Conversation, val session: Session, val relay: Boolean) : BukkitProxyEvent()
class CheProxyConvCancelled(val conversation: Conversation, val session: Session, val relay: Boolean) : BukkitProxyEvent()
class CheProxyConvClose(val session: Session, val refuse: Boolean) : BukkitProxyEvent() { val conversation = session.conversation }
class CheProxyConvClosed(val session: Session, val refuse: Boolean) : BukkitProxyEvent() { val conversation = session.conversation }
class CheProxyConvReplyClosed(val session: Session) : BukkitProxyEvent() { val conversation = session.conversation }

// --- Player Events ---
class CheProxyPlayerSelected(val player: Player, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyPlayerReleased(val player: Player) : BukkitProxyEvent() {
    val awaitList = arrayListOf<CompletableFuture<*>>()
    fun await(runnable: Runnable) { awaitList += CompletableFuture.runAsync { try { runnable.run() } catch (ex: Throwable) { ex.printStackTrace() } } }
}
class CheProxyPlayerUpdated(val player: Player, val playerProfile: PlayerProfile) : BukkitProxyEvent()
class CheProxyPlayerTrack(val player: Player, val playerProfile: PlayerProfile, val trackingQuest: Template?, val cancel: Boolean) : BukkitProxyEvent()
class CheProxyPlayerLevelChange(val player: Player, val option: LevelOption, val oldLevel: Int, val oldExperience: Int, var newLevel: Int, var newExperience: Int) : BukkitProxyEvent()
class CheProxyPlayerScenesBlockBreak(val player: Player, val blockData: ScenesBlockData) : BukkitProxyEvent()
class CheProxyPlayerScenesBlockInteract(val player: Player, val blockData: ScenesBlockData) : BukkitProxyEvent()
class CheProxyPlayerDataSetPre(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String, var value: Data) : BukkitProxyEvent()
class CheProxyPlayerDataSetPost(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String, val value: Data) : BukkitProxyEvent()
class CheProxyPlayerDataRemovePre(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String) : BukkitProxyEvent()
class CheProxyPlayerDataRemovePost(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String) : BukkitProxyEvent()

object ChoTenChemListener {

    @SubscribeEvent
    fun onAgent(e: QuestEvents.Agent) {
        val event = CheProxyAgent(e.questContainer, e.playerProfile, e.agentType, e.restrict)
        event.call()
        if (event.isCancelled) e.isCancelled = true
    }

    // --- Accept ---
    @SubscribeEvent
    fun onAcceptPre(e: QuestEvents.Accept.Pre) {
        val event = CheProxyAcceptPre(e.quest, e.playerProfile, e.reason)
        event.call()
        e.reason = event.reason // 同步可能被修改的拒绝原因
        if (event.isCancelled) e.isCancelled = true
    }

    @SubscribeEvent
    fun onAcceptPost(e: QuestEvents.Accept.Post) {
        CheProxyAcceptPost(e.quest, e.playerProfile).call()
    }

    // --- Fail ---
    @SubscribeEvent
    fun onFailPre(e: QuestEvents.Fail.Pre) {
        val event = CheProxyFailPre(e.quest, e.playerProfile)
        event.call()
        if (event.isCancelled) e.isCancelled = true
    }

    @SubscribeEvent
    fun onFailPost(e: QuestEvents.Fail.Post) {
        CheProxyFailPost(e.quest, e.playerProfile).call()
    }

    // --- Restart ---
    @SubscribeEvent
    fun onRestartPre(e: QuestEvents.Restart.Pre) {
        val event = CheProxyRestartPre(e.quest, e.playerProfile)
        event.call()
        if (event.isCancelled) e.isCancelled = true
    }

    @SubscribeEvent
    fun onRestartPost(e: QuestEvents.Restart.Post) {
        CheProxyRestartPost(e.quest, e.playerProfile).call()
    }

    // --- Complete ---
    @SubscribeEvent
    fun onCompletePre(e: QuestEvents.Complete.Pre) {
        val event = CheProxyCompletePre(e.quest, e.playerProfile)
        event.call()
        if (event.isCancelled) e.isCancelled = true
    }

    @SubscribeEvent
    fun onCompletePost(e: QuestEvents.Complete.Post) {
        CheProxyCompletePost(e.quest, e.playerProfile).call()
    }

    // --- Register / Unregister ---
    @SubscribeEvent
    fun onRegistered(e: QuestEvents.Registered) {
        CheProxyRegistered(e.quest, e.playerProfile).call()
    }

    @SubscribeEvent
    fun onUnregistered(e: QuestEvents.Unregistered) {
        CheProxyUnregistered(e.quest, e.playerProfile).call()
    }

    // --- Scoreboard ---
    @SubscribeEvent
    fun onScoreboardTrack(e: QuestEvents.ScoreboardTrack) {
        CheProxyScoreboardTrack(e.content, e.playerProfile).call()
    }

    // --- DataSet ---
    @SubscribeEvent
    fun onDataSetPre(e: QuestEvents.DataSet.Pre) {
        val event = CheProxyDataSetPre(e.player, e.playerProfile, e.quest, e.dataContainer, e.key, e.value)
        event.call()
        e.value = event.value // 同步可能被修改的数据
        if (event.isCancelled) e.isCancelled = true
    }

    @SubscribeEvent
    fun onDataSetPost(e: QuestEvents.DataSet.Post) {
        CheProxyDataSetPost(e.player, e.playerProfile, e.quest, e.dataContainer, e.key, e.value).call()
    }

    // --- DataRemove ---
    @SubscribeEvent
    fun onDataRemovePre(e: QuestEvents.DataRemove.Pre) {
        val event = CheProxyDataRemovePre(e.player, e.playerProfile, e.quest, e.dataContainer, e.key)
        event.call()
        if (event.isCancelled) e.isCancelled = true
    }

    @SubscribeEvent
    fun onDataRemovePost(e: QuestEvents.DataRemove.Post) {
        CheProxyDataRemovePost(e.player, e.playerProfile, e.quest, e.dataContainer, e.key).call()
    }
}