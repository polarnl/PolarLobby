package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender

interface SubCommand {
    val name: String
    val description: String?
    val aliases: Set<String>
        get() = emptySet()

    val permission: String?
    val usage: String

    fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean
    fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> = emptyList()
}
