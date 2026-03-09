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
import org.bukkit.Bukkit
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.metadata.MetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.plugin.Plugin
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

@Suppress("SameReturnValue")
open class ChoTenProxyEvent : Event(!Bukkit.isPrimaryThread()), Cancellable, Metadatable {

    private var isCancelled = false

    open val allowCancelled: Boolean
        get() = true

    override fun getHandlers(): HandlerList {
        return getHandlerList()
    }

    override fun isCancelled(): Boolean {
        return isCancelled
    }

    override fun setCancelled(value: Boolean) {
        if (allowCancelled) {
            isCancelled = value
        } else {
            throw IllegalArgumentException("This event cannot be cancelled.")
        }
    }

    fun call(): Boolean {
        Bukkit.getPluginManager().callEvent(this)
        return !isCancelled
    }

    val metadataMap = ConcurrentHashMap<String, MetadataValue>()

    override fun setMetadata(key: String, value: MetadataValue) {
        metadataMap[key] = value
    }

    override fun getMetadata(key: String): MutableList<MetadataValue> {
        return metadataMap[key]?.let { mutableListOf(it) } ?: mutableListOf()
    }

    override fun hasMetadata(key: String): Boolean {
        return metadataMap.containsKey(key)
    }

    override fun removeMetadata(key: String, plugin: Plugin) {
        metadataMap.remove(key)
    }

    companion object {

        @JvmField
        val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}

// --- Agent ---
class CheProxyAgent(val questContainer: QuestContainer, val playerProfile: PlayerProfile, val agentType: AgentType, val restrict: String) : ChoTenProxyEvent()

// --- Accept ---
class CheProxyAcceptPre(val quest: Template, val playerProfile: PlayerProfile, var reason: String?) : ChoTenProxyEvent()
class CheProxyAcceptPost(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()

// --- Fail ---
class CheProxyFailPre(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyFailPost(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()

// --- Restart ---
class CheProxyRestartPre(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyRestartPost(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()

// --- Complete ---
class CheProxyCompletePre(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyCompletePost(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()

// --- Registration ---
class CheProxyRegistered(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyUnregistered(val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()

// --- Scoreboard ---
class CheProxyScoreboardTrack(val content: MutableList<String>, val playerProfile: PlayerProfile) : ChoTenProxyEvent()

// --- DataSet ---
class CheProxyDataSetPre(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String, var value: Data) : ChoTenProxyEvent()
class CheProxyDataSetPost(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String, val value: Data) : ChoTenProxyEvent()

// --- DataRemove ---
class CheProxyDataRemovePre(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String) : ChoTenProxyEvent()
class CheProxyDataRemovePost(val player: Player, val playerProfile: PlayerProfile, val quest: Quest, val dataContainer: DataContainer, val key: String) : ChoTenProxyEvent()

// --- Objective Events ---
class CheProxyObjectiveContinuePre(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyObjectiveContinuePost(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyObjectiveCompletePre(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyObjectiveCompletePost(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyObjectiveRestartPre(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyObjectiveRestartPost(val objective: Objective<*>, val task: Task, val quest: Quest, val playerProfile: PlayerProfile) : ChoTenProxyEvent()

// --- Conversation Events ---
class CheProxyConvSelectReply(val player: Player, val session: Session, val reply: PlayerReply) : ChoTenProxyEvent()
class CheProxyConvPre(val conversation: Conversation, val session: Session, val relay: Boolean) : ChoTenProxyEvent()
class CheProxyConvPost(val conversation: Conversation, val session: Session, val relay: Boolean) : ChoTenProxyEvent()
class CheProxyConvBegin(val conversation: Conversation, val session: Session, val relay: Boolean) : ChoTenProxyEvent()
class CheProxyConvCancelled(val conversation: Conversation, val session: Session, val relay: Boolean) : ChoTenProxyEvent()
class CheProxyConvClose(val session: Session, val refuse: Boolean) : ChoTenProxyEvent() { val conversation = session.conversation }
class CheProxyConvClosed(val session: Session, val refuse: Boolean) : ChoTenProxyEvent() { val conversation = session.conversation }
class CheProxyConvReplyClosed(val session: Session) : ChoTenProxyEvent() { val conversation = session.conversation }

// --- Player Events ---
class CheProxyPlayerSelected(val player: Player, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyPlayerReleased(val player: Player) : ChoTenProxyEvent() {
    val awaitList = arrayListOf<CompletableFuture<*>>()
    fun await(runnable: Runnable) { awaitList += CompletableFuture.runAsync { try { runnable.run() } catch (ex: Throwable) { ex.printStackTrace() } } }
}
class CheProxyPlayerUpdated(val player: Player, val playerProfile: PlayerProfile) : ChoTenProxyEvent()
class CheProxyPlayerTrack(val player: Player, val playerProfile: PlayerProfile, val trackingQuest: Template?, val cancel: Boolean) : ChoTenProxyEvent()
class CheProxyPlayerLevelChange(val player: Player, val option: LevelOption, val oldLevel: Int, val oldExperience: Int, var newLevel: Int, var newExperience: Int) : ChoTenProxyEvent()
class CheProxyPlayerScenesBlockBreak(val player: Player, val blockData: ScenesBlockData) : ChoTenProxyEvent()
class CheProxyPlayerScenesBlockInteract(val player: Player, val blockData: ScenesBlockData) : ChoTenProxyEvent()
class CheProxyPlayerDataSetPre(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String, var value: Data) : ChoTenProxyEvent()
class CheProxyPlayerDataSetPost(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String, val value: Data) : ChoTenProxyEvent()
class CheProxyPlayerDataRemovePre(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String) : ChoTenProxyEvent()
class CheProxyPlayerDataRemovePost(val player: Player, val playerProfile: PlayerProfile, val dataContainer: DataContainer, val key: String) : ChoTenProxyEvent()

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