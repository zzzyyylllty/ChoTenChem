package io.github.zzzyyylllty.chotenchem.papi
import io.github.zzzyyylllty.chotentech.ChoTenTech.config
import io.github.zzzyyylllty.chotentech.ChoTenTech.playerHudMap
import io.github.zzzyyylllty.chotentech.data.ChoTenDataHelper
import io.github.zzzyyylllty.chotentech.data.HUDManager
import io.github.zzzyyylllty.chotentech.data.PlayerData
import io.github.zzzyyylllty.chotentech.data.PlayerDataManager
import io.github.zzzyyylllty.chotentech.data.getData
import io.github.zzzyyylllty.chotentech.util.replacePlaceholderSafety
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion
import taboolib.platform.compat.replacePlaceholder
import kotlin.math.round

object ExamplePapiHook : PlaceholderExpansion {

    override val identifier: String = "chotenchem"

    override val en

    // 处理在线玩家请求
    override fun onPlaceholderRequest(player: Player?, args: String): String {

        var list = args.split("_")

        when (list[0].toLowerCase()) {
            "idexists" -> return "1"
        }
        return "Error"
    }

    // 处理离线玩家请求
    override fun onPlaceholderRequest(player: OfflinePlayer?, args: String): String {
        return onPlaceholderRequest(player?.player, args)
    }
}
