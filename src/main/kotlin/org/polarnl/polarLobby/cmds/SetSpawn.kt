package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage

class SetSpawn(private val plugin: PolarLobby): SubCommand {
    override val name: String = "setspawn"
    override val description: String = "Sets the lobby spawn. Coordinates spawn coordinates will be where you are during execution of this command."
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl setspawn"

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        val player: Player = sender as Player

        plugin.config.set("spawn_coordinates", player.location)
        plugin.saveConfig()

        PlayerMessage().send("<green>Successfully set spawn!", player)
        return true
    }
}