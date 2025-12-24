package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage

/**
 * VoidTP - Commando om de void teleportatie threshold in te stellen
 * 
 * Dit commando stelt de Y-waarde in waaronder spelers automatisch
 * terug naar spawn worden geteleporteerd. Dit voorkomt dat spelers
 * eindeloos in de void vallen.
 * 
 * De threshold wordt ingesteld op de Y-coördinaat waar de speler
 * zich bevindt wanneer het commando wordt uitgevoerd.
 */
class VoidTP(private val plugin: PolarLobby): SubCommand {
    override val name: String = "voidtp"
    override val description: String = "Sets the y level for void teleportation to spawn. Y level will be where you are during execution of this command."
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl voidtp"
    
    /**
     * Stelt de Y-waarde voor void teleportatie in op de huidige Y-positie van de speler.
     * Vereist dat spawn eerst is ingesteld via /pl setspawn.
     */
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        // Controleer of spawn al is ingesteld
        if (plugin.config.get("spawn_coordinates") == null || plugin.config.get("spawn_coordinates") == "") {
            PlayerMessage().send("<red>In order to set a void teleporting threshold, you first need to set a spawn location using click:suggest_command:\"/pl setspawn\">/pl setspawn</click>.", sender as Player)
            return false
        }
        
        val player: Player = sender as Player
        // Sla de huidige Y-positie op als void teleportatie threshold
        plugin.config.set("voidtp_y", player.location.y)
        plugin.saveConfig()
        PlayerMessage().send("<green>Successfully set void teleportation threshold!", player)
        return true
    }
}