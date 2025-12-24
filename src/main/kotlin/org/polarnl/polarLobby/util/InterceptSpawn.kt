package org.polarnl.polarLobby.util

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.polarnl.polarLobby.PolarLobby

/**
 * InterceptSpawn - Event listener voor spawn beheer
 * 
 * Deze klasse onderschept player join en respawn events om spelers
 * automatisch naar de ingestelde spawn locatie te teleporteren.
 * Dit zorgt ervoor dat alle spelers op dezelfde plek starten.
 */
class InterceptSpawn(private val plugin: PolarLobby): Listener {
    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    /**
     * Wordt aangeroepen wanneer een speler de server joint.
     * Teleporteert de speler naar de ingestelde spawn locatie.
     */
    @EventHandler(priority = org.bukkit.event.EventPriority.LOWEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player: Player = event.player
        val spawnCoordsFromConfig = plugin.config.getLocation("spawn_coordinates")

        if (spawnCoordsFromConfig != null) {
            // Teleporteer speler naar spawn en zet gamemode op survival
            player.teleport(spawnCoordsFromConfig)
            player.gameMode = org.bukkit.GameMode.SURVIVAL
        } else {
            // Als spawn niet is ingesteld, waarschuw admins
            if (player.hasPermission("polarlobby.admin")) {
                PlayerMessage().send("<yellow><bold>Spawn coordinates are not set! Please set them using /setspawn", player)
            }
            ServerConsole.error("Spawn coordinates are not set in config.yml!")
        }
    }

    /**
     * Wordt aangeroepen wanneer een speler respawnt (na dood).
     * Zorgt ervoor dat de speler op de lobby spawn respawnt.
     */
    @EventHandler(priority = org.bukkit.event.EventPriority.LOWEST)
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player: Player = event.player
        val spawnCoordsFromConfig = plugin.config.getLocation("spawn_coordinates")

        if (spawnCoordsFromConfig != null) {
            // Zet de respawn locatie naar de ingestelde spawn
            event.respawnLocation = spawnCoordsFromConfig
        } else {
            // Als spawn niet is ingesteld, waarschuw admins
            if (player.hasPermission("polarlobby.admin")) {
                PlayerMessage().send("<yellow><bold>Spawn coordinates are not set! Please set them using /setspawn", player)
            }
            ServerConsole.error("Spawn coordinates are not set in config.yml!")
        }
    }
}