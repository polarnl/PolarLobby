package org.polarnl.polarLobby.util

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.polarnl.polarLobby.PolarLobby
import java.util.UUID

class AllowBlockBreak(private val plugin: PolarLobby) : Listener {
    // Players listed here are allowed to bypass the lobby build protection.
    // Shared instance is created in PolarLobby and used by the allowbreak command.
    val allowedPlayers: MutableSet<UUID> = mutableSetOf()

    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (allowedPlayers.contains(event.player.uniqueId)) {
            return
        }
        event.isCancelled = true
        PlayerMessage().send("<red><bold>You cannot break blocks in this server", event.player)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (allowedPlayers.contains(event.player.uniqueId)) {
            return
        }
        event.isCancelled = true
        PlayerMessage().send("<red><bold>You cannot place blocks in this server", event.player)
    }
}