package org.polarnl.polarLobby.util

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.polarnl.polarLobby.PolarLobby

class InterceptSpawn(private val plugin: PolarLobby): Listener {
    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.LOWEST)
    fun AsyncPlayer(event: PlayerJoinEvent) {
        val player: Player = event.player
        val spawnCoordsFromConfig = plugin.config.getLocation("spawn_coordinates")
        if (spawnCoordsFromConfig != null && plugin.config.get("spawn_coordinates") != "") {
            player.teleport(spawnCoordsFromConfig)
        } else {
            if (player.hasPermission("polarlobby.admin")) {
                return PlayerMessage().send("<yellow><bold>Spawn coordinates are not set! Please set them using <", player)
            }
            ServerConsole.error("Spawn coordinates are not set in config.yml!")
        }
    }
}