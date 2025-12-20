package org.polarnl.polarLobby.util

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.polarnl.polarLobby.PolarLobby

class AllowBlockBreak(private val plugin: PolarLobby) : Listener {

    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = true
        PlayerMessage().send("<red><bold>You cannot break blocks in this server", event.player)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        event.isCancelled = true
        PlayerMessage().send("<red><bold>You cannot place blocks in this server", event.player)
    }
}