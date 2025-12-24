package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage

/**
 * SetSpawn - Commando om de spawn locatie in te stellen
 * 
 * Dit commando stelt de lobby spawn locatie in op de huidige positie
 * van de speler die het commando uitvoert. Deze locatie wordt opgeslagen
 * in config.yml en gebruikt voor:
 * - Player join teleportatie
 * - Respawn locatie
 * - Void teleportatie bestemming
 */
class SetSpawn(private val plugin: PolarLobby): SubCommand {
    override val name: String = "setspawn"
    override val description: String = "Sets the lobby spawn. Coordinates spawn coordinates will be where you are during execution of this command."
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl setspawn"

    /**
     * Slaat de huidige locatie van de speler op als spawn locatie.
     */
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        val player: Player = sender as Player

        // Sla de huidige locatie op in de config
        plugin.config.set("spawn_coordinates", player.location)
        plugin.saveConfig()

        PlayerMessage().send("<green>Successfully set spawn!", player)
        return true
    }
}