import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.izzel.taboolib.gradle.*
import io.izzel.taboolib.gradle.Basic
import io.izzel.taboolib.gradle.Bukkit
import io.izzel.taboolib.gradle.BukkitFakeOp
import io.izzel.taboolib.gradle.BukkitHook
import io.izzel.taboolib.gradle.BukkitUI
import io.izzel.taboolib.gradle.BukkitUtil
import io.izzel.taboolib.gradle.BukkitNMS
import io.izzel.taboolib.gradle.BukkitNMSUtil
import io.izzel.taboolib.gradle.BukkitNMSItemTag
import io.izzel.taboolib.gradle.BukkitNMSEntityAI
import io.izzel.taboolib.gradle.BukkitNMSDataSerializer
import io.izzel.taboolib.gradle.I18n
import io.izzel.taboolib.gradle.Metrics
import io.izzel.taboolib.gradle.MinecraftChat
import io.izzel.taboolib.gradle.MinecraftEffect
import io.izzel.taboolib.gradle.CommandHelper
import io.izzel.taboolib.gradle.Database
import io.izzel.taboolib.gradle.DatabasePlayer
import io.izzel.taboolib.gradle.Ptc
import io.izzel.taboolib.gradle.PtcObject
import io.izzel.taboolib.gradle.Kether


plugins {
    java
    id("io.izzel.taboolib") version "2.0.23"
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
}

taboolib {
    env {
        install(Basic)
        install(Bukkit)
        install(BukkitFakeOp)
        install(BukkitHook)
        install(BukkitUI)
        install(BukkitUtil)
        install(BukkitNMS)
        install(BukkitNMSUtil)
        install(BukkitNMSDataSerializer)
        install(BukkitNMSItemTag)
        install(BukkitNMSEntityAI)
        install(Kether)
        install(JavaScript)
    }
    description {
        name = "ChoTenChem"
        desc("Chemdah Expansion.")
        contributors {
            name("AkaCandyKAngel")
        }
    }
    version {
        taboolib = "6.2.3-20d868d"
        coroutines = "1.8.1"
    }
    relocate("top.maplex.arim","io.github.zzzyyylllty.chotenchem.library.arim")
    relocate("ink.ptms.um","io.github.zzzyyylllty.chotenchem.library.um")
    // relocate("com.google", "io.github.zzzyyylllty.chotenchem.library.google")
    relocate("com.alibaba", "io.github.zzzyyylllty.chotenchem.library.alibaba")
    relocate("kotlinx.serialization", "kotlinx.serialization170")
    // relocate("de.tr7zw.changeme.nbtapi","io.github.zzzyyylllty.chotenchem.library.nbtapi")
    relocate("io.github.projectunified.uniitem","io.github.zzzyyylllty.chotenchem.library.uniitem")
    relocate("com.fasterxml.jackson","io.github.zzzyyylllty.chotenchem.library.jackson")
    relocate("com.mojang.datafixers","io.github.zzzyyylllty.chotenchem.library.datafixers")
    relocate("io.netty.handler.codec.http", "io.github.zzzyyylllty.chotenchem.library.http")
    relocate("io.netty.handler.codec.rtsp", "io.github.zzzyyylllty.chotenchem.library.rtsp")
    relocate("io.netty.handler.codec.spdy", "io.github.zzzyyylllty.chotenchem.library.spdy")
    relocate("io.netty.handler.codec.http2", "io.github.zzzyyylllty.chotenchem.library.http2")
    relocate("org.tabooproject.fluxon","io.github.zzzyyylllty.chotenchem.library.fluxon")
    relocate("com.github.benmanes.caffeine","io.github.zzzyyylllty.chotenchem.library.caffeine")
    relocate("org.kotlincrypto","io.github.zzzyyylllty.chotenchem.library.kotlincrypto")
}
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-snapshots/")
    maven {
        name = "adyeshach"
        url = uri("https://repo.tabooproject.org/repository/releases/")
    }
    maven {
        name = "mythicmobs"
        url = uri("https://mvn.lumine.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    maven("https://repo.fancyinnovations.com/releases")
    maven("https://repo.tabooproject.org/repository/releases")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public")
    maven("https://repo.rosewooddev.io/repository/public/")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.oraxen.com/releases")
    maven("https://nexus.phoenixdevt.fr/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://r.irepo.space/maven/")
    maven("https://repo.auxilor.io/repository/maven-public/")
    maven("https://repo.hibiscusmc.com/releases/")
    maven("https://repo.tabooproject.org/service/rest/repository/browse/releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-releases/")
}

dependencies {
    compileOnly("de.oliver:FancyNpcs:2.7.0")
    compileOnly("com.github.retrooper:packetevents-spigot:2.11.1")
    implementation("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT") { isTransitive = false }
    implementation("net.kyori:adventure-text-serializer-legacy:4.19.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.19.0")
    implementation("net.kyori:adventure-api:4.19.0")
    implementation("net.kyori:adventure-text-minimessage:4.19.0")
    compileOnly("com.google.code.gson:gson:2.10.1")
    compileOnly("ink.ptms.chemdah:api:1.1.17")
    // compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT") { isTransitive = false }
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
