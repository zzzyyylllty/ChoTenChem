package io.github.zzzyyylllty.chotenchem.function

import org.bukkit.command.CommandSender

fun CommandSender.sendComponent(message: String) {
    sendMessage(mmUtil.deserialize(message))
}