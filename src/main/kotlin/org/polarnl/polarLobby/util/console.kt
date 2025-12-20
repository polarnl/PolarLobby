package org.polarnl.polarLobby.util

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit

object ServerConsole {
    fun info(message: String) {
        val miniMessage = MiniMessage.miniMessage()
        val component = miniMessage.deserialize(
            "[<#38bdf8>Polar<#e0f2fe>Lobby<white> | <blue>INFO<white>]: $message"
        )
        Bukkit
            .getConsoleSender()
            .sendMessage(component)
    }
    fun error(message: String) {
        val miniMessage = MiniMessage.miniMessage()
        val component = miniMessage.deserialize(
            "[<#38bdf8>Polar<#e0f2fe>Lobby<white> | <red>ERROR</red>]: $message"
        )
        Bukkit
            .getConsoleSender()
            .sendMessage(component)
    }
}