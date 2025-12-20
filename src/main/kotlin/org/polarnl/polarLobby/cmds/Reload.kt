package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage

class Reload(private val plugin: PolarLobby) : SubCommand {
    override val name: String = "reload"
    override val description: String? = "Reloads the PolarLobby config"
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl reload"

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        plugin.reloadConfig()
        PlayerMessage().send("<green>Successfully reloaded config", sender as Player)
        return true
    }
    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        // No autocompletion(s) for this command needed, so we return an empty list
        return emptyList()
    }
}
