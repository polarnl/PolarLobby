package org.polarnl.polarLobby.util

import net.kyori.adventure.sound.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.polarnl.polarLobby.PolarLobby
import org.bukkit.entity.Player

class VoidTP(private val plugin: PolarLobby): Listener {
    fun register()  {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    private val ythredshold = plugin.config.getDouble("voidtp_y")
    private val spawnTo = plugin.config.getLocation("spawn_coordinates")

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player: Player = event.player
        val y: Double = player.getLocation().y

        if (event.from.y < ythredshold && y < ythredshold) {
            player.teleport(spawnTo!!)
            PlayerMessage().send("<red><bold>You went too far!", player)
            player.playSound(Sound.sound({}))
        }
    }
}