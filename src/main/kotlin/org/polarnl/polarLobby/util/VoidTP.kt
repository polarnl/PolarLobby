package org.polarnl.polarLobby.util

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.polarnl.polarLobby.PolarLobby
import org.bukkit.entity.Player
import org.bukkit.Sound

/**
 * VoidTP - Void teleportatie systeem
 * 
 * Deze klasse voorkomt dat spelers in de void vallen door hen automatisch
 * terug te teleporteren naar spawn wanneer ze onder een bepaalde Y-waarde komen.
 * 
 * Dit is handig voor lobby's met afgronden of wanneer spelers per ongeluk
 * te ver naar beneden vallen.
 */
class VoidTP(private val plugin: PolarLobby): Listener {
    fun register()  {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    // De spawn locatie waarnaar spelers worden geteleporteerd
    private val spawnTo = plugin.config.getLocation("spawn_coordinates")

    /**
     * Detecteert wanneer een speler onder de void threshold komt en teleporteert hen terug.
     * Speelt ook een geluid en toont een bericht.
     */
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val to = event.to ?: return
        val from = event.from

        // Haal de Y-waarde op waaronder spelers worden geteleporteerd (standaard -64)
        val yThreshold = plugin.config.getDouble("voidtp_y", -64.0)

        // Controleer of de speler de threshold passeert (van boven naar beneden)
        if (from.y >= yThreshold && to.y < yThreshold) {
            val player: Player = event.player
            val destination = spawnTo ?: return
            
            // Teleporteer terug naar spawn
            player.teleport(destination)
            PlayerMessage().send("<red><bold>You went too far!", player)
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f)
        }
    }
}
