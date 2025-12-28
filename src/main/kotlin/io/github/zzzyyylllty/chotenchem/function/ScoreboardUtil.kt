package io.github.zzzyyylllty.chotenchem.function

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.score.ScoreFormat
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisplayScoreboard
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerResetScore
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective.ObjectiveMode
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateScore
import io.github.retrooper.packetevents.adventure.serializer.gson.GsonComponentSerializer
import io.github.zzzyyylllty.chotenchem.ChoTenChem.boardMap
import io.github.zzzyyylllty.chotenchem.data.ScoreBoard
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


class ScoreboardUtil {


    fun sendScoreBoard(p: Player, scoreBoard: ScoreBoard) {

    }

    fun create(p: Player) {
        val board = boardMap[p.uniqueId.toString()] ?: return
        val b = PacketEventsBoard(p, board)
        b.sendPacket(p, b.createObjectivePacket(ObjectiveMode.CREATE, ""))
        b.sendPacket(p, b.createDisplayPacket())
    }

    fun update(p: Player) {
        val board = boardMap[p.uniqueId.toString()] ?: return
        val b = PacketEventsBoard(p, board)
        // Fixes waterfall kick issue where scoreboard tries to be registered twice on login for whatever reason.
        if (board.lock) return

        board.lock = true
        val title: String? = board.title
        val lines: MutableList<String?> = board.lines.toMutableList()

        val scores: MutableMap<Int, String> = HashMap<Int, String>()
        var index = lines.size

        for (line in lines) {
            scores[index--] = line ?: " "
        }
        
        b.sendPacket(p, b.createObjectivePacket(ObjectiveMode.UPDATE, title ?: " "))

        scores.forEach { (score: Int, text: String) ->
            val scoreId: String = "line_$score"
            b.sendPacket(p, b.createScorePacket(scoreId, score, text))
        }

        board.scores?.filter({ entry -> !scores.containsKey(entry.key) })?.forEach({ entry ->
            val score: Int = entry.key
            val scoreId: String = "line_$score"

            b.sendPacket(p, b.createResetScorePacket(scoreId))
        })

        board.scores?.clear()
        board.scores?.putAll(scores)
        board.lock = false
    }

    fun remove(p: Player) {
        val board = boardMap[p.uniqueId.toString()] ?: return
        val b = PacketEventsBoard(p, board)
        b.sendPacket(b.player, b.createObjectivePacket(ObjectiveMode.REMOVE, ""))

        board.scores?.forEach({ (score, text) ->
            b.sendPacket(b.player, b.createResetScorePacket("line_$score"))
        })

        board.scores?.clear()
        board.lock = false
    }

    class PacketEventsBoard(val player: Player, val board: ScoreBoard) {

        val identifier = player.name.substring(0, 16)
        fun createObjectivePacket(
            mode: ObjectiveMode,
            displayName: String,
        ): WrapperPlayServerScoreboardObjective {
            val objectiveMode = when (mode) {
                ObjectiveMode.CREATE -> ObjectiveMode.CREATE
                ObjectiveMode.REMOVE -> ObjectiveMode.REMOVE
                ObjectiveMode.UPDATE -> ObjectiveMode.UPDATE
            }

            return WrapperPlayServerScoreboardObjective(
                identifier,
                objectiveMode,
                GsonComponentSerializer.gson().deserialize(displayName),
                WrapperPlayServerScoreboardObjective.RenderType.INTEGER,
                ScoreFormat.blankScore()
            )
        }

        fun createResetScorePacket(scoreId: String): WrapperPlayServerResetScore {
            return WrapperPlayServerResetScore(scoreId, board.identifier)
        }

        fun createScorePacket(scoreId: String, score: Int, text: String): WrapperPlayServerUpdateScore {
            val scorePacket = WrapperPlayServerUpdateScore(
                scoreId,
                WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM,
                board.identifier,
                Optional.of<Int?>(score)
            )

            scorePacket.setEntityDisplayName(GsonComponentSerializer.gson().deserialize(text))
            scorePacket.setScoreFormat(ScoreFormat.blankScore())

            return scorePacket
        }

        fun createDisplayPacket(): WrapperPlayServerDisplayScoreboard {
            return WrapperPlayServerDisplayScoreboard(1, board.identifier)
        }

        fun sendPacket(player: Player, wrapper: PacketWrapper<*>) {
            PacketEvents.getAPI().playerManager.sendPacket(player, wrapper)
        }
    }
}