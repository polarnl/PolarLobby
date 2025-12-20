package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage
import org.polarnl.polarLobby.util.ServerConsole

class AllowBreakCommand(private val plugin: PolarLobby) : SubCommand {
    override val name: String = "allowbreak"
    override val description: String = "Allows you (or someone else) to break/place blocks in the lobby"
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl allowbreak <player>"

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        val target: Player? = when {
            args.isNotEmpty() -> plugin.server.getPlayerExact(args[0])
            sender is Player -> sender
            else -> null
        }

        if (target == null) {
            if (sender is Player) {
                PlayerMessage().send("<red>That player is not online.", sender)
            } else {
                ServerConsole.error("That player is not online.")
            }
            return true
        }

        val allowManager = plugin.allowBlockBreak
        val nowAllowed = if (allowManager.allowedPlayers.contains(target.uniqueId)) {
            allowManager.allowedPlayers.remove(target.uniqueId)
            false
        } else {
            allowManager.allowedPlayers.add(target.uniqueId)
            true
        }

        val statusText = (if (sender == target) "You" else target.name) + (if (nowAllowed) " <green>can now" else " <red>can no longer")
        if (sender is Player) {
            PlayerMessage().send("$statusText <white>break/place blocks in the lobby.", sender)
            if (sender.uniqueId != target.uniqueId) {
                PlayerMessage().send("$statusText <white>break/place blocks in the lobby.", target)
            }
        } else {
            ServerConsole.info("${target.name} ${if (nowAllowed) "can now" else "can no longer"} break/place blocks in the lobby.")
            PlayerMessage().send("$statusText <white>break/place blocks in the lobby.", target)
        }

        return true
    }
    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        if (args.isEmpty()) return emptyList()
        val prefix = args[0].lowercase()
        return plugin.server.onlinePlayers
            .map { it.name }
            .filter { it.lowercase().startsWith(prefix) }
            .sorted()
    }
}