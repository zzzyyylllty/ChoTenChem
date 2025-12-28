package io.github.zzzyyylllty.chotenchem.command

import io.github.zzzyyylllty.chotenchem.ChoTenChem.defaultScoreboardSetting
import io.github.zzzyyylllty.chotenchem.ChoTenChem.papiSetting
import io.github.zzzyyylllty.chotenchem.function.ScoreboardUtil.addBoard
import io.github.zzzyyylllty.chotenchem.function.ScoreboardUtil.removeBoard
import io.github.zzzyyylllty.chotenchem.function.ScoreboardUtil.updateBoard
import io.github.zzzyyylllty.chotenchem.function.sendComponent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand

@CommandHeader(
    name = "chotenchem-debug",
    aliases = ["checdebug"],
    permission = "chotenchem.command.debug",
    description = "Debug Command for ChoTenTech.",
    permissionMessage = "",
    permissionDefault = PermissionDefault.OP,
    newParser = false,
)
object ChoTenDebugCommand {

    @CommandBody
    val main = mainCommand {
        createModernHelper()
    }

    @CommandBody
    val addBoard = subCommand {
        player("user") {
            execute<CommandSender> { sender, context, argument ->
                val user = context.player("user")
                // 转化为Bukkit的Player
                val player = user.castSafely<Player>()
                player?.let { addBoard(it) }
            }
        }
    }


    @CommandBody
    val updateBoard = subCommand {
        player("user") {
            execute<CommandSender> { sender, context, argument ->
                val user = context.player("user")
                // 转化为Bukkit的Player
                val player = user.castSafely<Player>()
                player?.let { updateBoard(it) }
            }
        }
    }


    @CommandBody
    val removeBoard = subCommand {
        player("user") {
            execute<CommandSender> { sender, context, argument ->
                val user = context.player("user")
                // 转化为Bukkit的Player
                val player = user.castSafely<Player>()
                player?.let { removeBoard(it) }
            }
        }
    }

    @CommandBody
    val defaultSettings = subCommand {
        execute<CommandSender> { sender, context, argument ->
            sender.sendComponent(defaultScoreboardSetting.value.toString())
        }
    }
    @CommandBody
    val papiSettings = subCommand {
        execute<CommandSender> { sender, context, argument ->
            sender.sendComponent(papiSetting.value.toString())
        }
    }

}
