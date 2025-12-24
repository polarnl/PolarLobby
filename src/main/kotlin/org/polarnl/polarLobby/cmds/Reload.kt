package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage
import org.polarnl.polarLobby.util.ServerConsole

/**
 * Reload - Commando om de plugin configuratie te herladen
 * 
 * Dit commando herlaadt de config.yml file zonder de server te hoeven restarten.
 * Dit is handig wanneer je wijzigingen aan de configuratie hebt gemaakt
 * en deze direct wilt toepassen.
 * 
 * Let op: Sommige wijzigingen (zoals het in-/uitschakelen van listeners)
 * vereisen mogelijk nog steeds een server restart of plugin reload.
 */
class Reload(private val plugin: PolarLobby) : SubCommand {
    override val name: String = "reload"
    override val description: String? = "Reloads the PolarLobby config"
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl reload"

    /**
     * Herlaadt de configuratie file en stuurt een bevestigingsbericht.
     */
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        plugin.reloadConfig()
        if (sender is Player) {
            PlayerMessage().send("<green>Successfully reloaded config", sender)
        } else {
            ServerConsole.info("Successfully reloaded config")
        }
        return true
    }
    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        // No autocompletion(s) for this command exist, so we return an empty list
        return emptyList()
    }
}
