package io.github.zzzyyylllty.chotenchem.function

import ink.ptms.chemdah.api.ChemdahAPI
import ink.ptms.chemdah.core.quest.Quest
import org.bukkit.entity.Player

class ChemdahPlayerUtil(val p: Player) {
    fun getQuest(quest: String): Quest? {
        return ChemdahAPI.playerProfile[p.name]?.getQuestById(quest)
    }
}