package org.polarnl.polarLobby

import org.bukkit.plugin.java.JavaPlugin
import org.polarnl.polarLobby.cmds.MainCommand
import org.polarnl.polarLobby.util.AllowBlockBreak
import org.polarnl.polarLobby.util.InterceptSpawn
import org.polarnl.polarLobby.util.ServerConsole

class PolarLobby : JavaPlugin() {
    lateinit var allowBlockBreak: AllowBlockBreak

    override fun onEnable() {
        // Copies config.yml from jar -> plugins/PolarLobby/config.yml on the first run
        saveDefaultConfig()
        reloadConfig()

        InterceptSpawn(this).register()

        // Shared instance so commands and listeners operate in the same state.
        allowBlockBreak = AllowBlockBreak(this)

        val allowBreak = config.getBoolean("allowBreak", true)
        if (!allowBreak) {
            allowBlockBreak.register()
        }

        val mainCommand = MainCommand(this)
        val cmd = getCommand("pl")
        if (cmd == null) {
            ServerConsole.error("FATAL: pl command does not exist, shutting down plugin!")
            ServerConsole.error("You have a faulty build of PolarLobby!")
            ServerConsole.error("Please try reinstalling the plugin. If you built PolarLobby yourself, create an issue on GitHub: https://github.com/PolarNL/PolarLobby/issues.")
            server.pluginManager.disablePlugin(this)
        } else {
            cmd.setExecutor(mainCommand)
            cmd.tabCompleter = mainCommand
        }

        ServerConsole.info(
            "<bold><#38bdf8>Polar<#e0f2fe>Lobby</bold><white> has been successfully enabled!"
        )
    }

    override fun onDisable() {
        ServerConsole.info(
            "<bold><#38bdf8>Polar<#e0f2fe>Lobby</bold><white> has been successfully disabled!"
        )
    }
}
