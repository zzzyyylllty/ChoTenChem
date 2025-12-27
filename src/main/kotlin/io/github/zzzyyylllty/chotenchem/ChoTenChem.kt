package io.github.zzzyyylllty.chotenchem

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.ui.type.Linked

object ChoTenChem : Plugin() {

    val playerData = LinkedHashMap<String, LinkedHashMap<String, String>>()

    override fun onEnable() {
        info("ChoTenChem enabling...")
    }

    override fun onActive() {
        info("ChoTenChem enabled.")
    }

    @Config("config.yml")
    lateinit var config: Configuration

}
