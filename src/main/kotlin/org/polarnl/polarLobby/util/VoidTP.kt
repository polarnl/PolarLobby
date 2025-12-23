package org.polarnl.polarLobby.util

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.polarnl.polarLobby.PolarLobby
import org.bukkit.entity.Player
import org.bukkit.Sound

class VoidTP(private val plugin: PolarLobby): Listener {
    fun register()  {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    private val spawnTo = plugin.config.getLocation("spawn_coordinates")

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val to = event.to ?: return
        val from = event.from

        val yThreshold = plugin.config.getDouble("voidtp_y", -64.0)

        if (from.y >= yThreshold && to.y < yThreshold) {
            val player: Player = event.player
            val destination = spawnTo ?: return
            player.teleport(destination)
            PlayerMessage().send("<red><bold>You went too far!", player)
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f)
        }
    }
}
