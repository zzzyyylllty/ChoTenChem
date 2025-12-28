package io.github.zzzyyylllty.chotenchem.function

import ink.ptms.chemdah.api.ChemdahAPI
import ink.ptms.chemdah.core.PlayerProfile
import ink.ptms.chemdah.core.quest.Quest
import ink.ptms.chemdah.core.quest.addon.AddonTrack.Companion.trackQuestId
import io.github.zzzyyylllty.chotenchem.ChoTenChem.defaultScoreboardSetting
import io.github.zzzyyylllty.chotenchem.data.ScoreBoardSettings
import org.bukkit.entity.Player

class ChemdahPlayerUtil(val p: Player) {
    fun getQuest(quest: String): Quest? {
        return ChemdahAPI.playerProfile[p.name]?.getQuestById(quest)
    }
    fun getTracking(): Quest? {
        return ChemdahAPI.playerProfile[p.name]?.trackQuestId?.let { getQuest(it) }
    }
    fun getProfile(): PlayerProfile? {
        return ChemdahAPI.playerProfile[p.name]
    }
    fun getScoreBoardSetting(): ScoreBoardSettings {
        return defaultScoreboardSetting.value
    }
}