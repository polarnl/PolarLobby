package org.polarnl.polarLobby

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin
import org.polarnl.polarLobby.cmds.MainCommand
import org.polarnl.polarLobby.util.AllowBlockBreak
import org.polarnl.polarLobby.util.Protocollib
import org.polarnl.polarLobby.util.ServerConsole

class PolarLobby : JavaPlugin() {
    override fun onEnable() {
        // Copies config.yml from jar -> plugins/PolarLobby/config.yml on the first run
        saveDefaultConfig()
        reloadConfig()

        val allowBreak = config.getBoolean("allowBreak", true)
        if (!allowBreak) {
            AllowBlockBreak(this).register()
        }

        getCommand("polarlobby")?.setExecutor(MainCommand(this))
        getCommand("polarlobby")?.tabCompleter = MainCommand(this)

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
