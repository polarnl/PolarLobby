package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage

class VoidTP(private val plugin: PolarLobby): SubCommand {
    override val name: String = "voidtp"
    override val description: String = "Sets the y level for void teleportation to spawn. Y level will be where you are during execution of this command."
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl voidtp"
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if (plugin.config.get("spawn_coordinates") == null || plugin.config.get("spawn_coordinates") == "") {
            PlayerMessage().send("<red>In order to set a void teleporting threshold, you first need to set a spawn location using click:suggest_command:\"/pl setspawn\">/pl setspawn</click>.", sender as Player)
            return false
        }
        val player: Player = sender as Player
        plugin.config.set("voidtp_y", player.location.y)
        plugin.saveConfig()
        PlayerMessage().send("<green>Successfully set void teleportation threshold!", player)
        return true
    }
}