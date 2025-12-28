package io.github.zzzyyylllty.chotenchem.kether.script

import net.kyori.adventure.audience.Audience
import taboolib.module.kether.KetherParser
import taboolib.module.kether.script
import taboolib.module.kether.scriptParser
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import taboolib.module.kether.actionTake
import taboolib.module.kether.run
import taboolib.module.kether.str

// 由于动态加载依赖，combinationParser不能使用
//@KetherParser(["minitell", "mtell"], shared = true)
//fun actionMiniTell() = combinationParser {
//    val mm = MiniMessage.miniMessage()
//    it.group(text()).apply(it) { str ->
//        now {
//            val sender = script().sender?.castSafely<CommandSender>()
//            (sender as Audience).sendMessage(mm.deserialize(str))
//        }
//    }
//}
@KetherParser(["minitell", "mtell"], shared = true)
fun actionMiniTell() = scriptParser {
    val message = it.nextParsedAction()
    val mm = MiniMessage.miniMessage()
    actionTake {
        val sender = script().sender?.castSafely<CommandSender>()
        run(message).str { s -> (sender as Audience).sendMessage(mm.deserialize(s)) }
    }
}