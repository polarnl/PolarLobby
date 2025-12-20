package org.polarnl.polarLobby.util

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.polarnl.polarLobby.PolarLobby

class InterceptSpawn(private val plugin: PolarLobby): Listener {
    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.LOWEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player: Player = event.player
        val spawnCoordsFromConfig = plugin.config.getLocation("spawn_coordinates")

        if (spawnCoordsFromConfig != null) {
            player.teleport(spawnCoordsFromConfig)
        } else {
            if (player.hasPermission("polarlobby.admin")) {
                PlayerMessage().send("<yellow><bold>Spawn coordinates are not set! Please set them using /setspawn", player)
            }
            ServerConsole.error("Spawn coordinates are not set in config.yml!")
        }
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.LOWEST)
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player: Player = event.player
        val spawnCoordsFromConfig = plugin.config.getLocation("spawn_coordinates")

        if (spawnCoordsFromConfig != null) {
            event.respawnLocation = spawnCoordsFromConfig
        } else {
            if (player.hasPermission("polarlobby.admin")) {
                PlayerMessage().send("<yellow><bold>Spawn coordinates are not set! Please set them using /setspawn", player)
            }
            ServerConsole.error("Spawn coordinates are not set in config.yml!")
        }
    }
}