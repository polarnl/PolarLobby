package org.polarnl.polarLobby.util

import org.bukkit.entity.Player
import net.kyori.adventure.text.minimessage.MiniMessage

class PlayerMessage {
    fun send(text: String, player: Player) {
        val encoded = MiniMessage.miniMessage().deserialize(
            "[<bold><#38bdf8>Polar<#e0f2fe>Lobby<white></bold>] | $text"
        )
        player.sendMessage(encoded)
    }
}