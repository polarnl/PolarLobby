package org.polarnl.polarLobby

import org.bukkit.plugin.java.JavaPlugin
import org.polarnl.polarLobby.cmds.MainCommand
import org.polarnl.polarLobby.util.AllowBlockBreak
import org.polarnl.polarLobby.util.AntiDamage
import org.polarnl.polarLobby.util.InterceptSpawn
import org.polarnl.polarLobby.util.ServerConsole
import org.polarnl.polarLobby.util.VoidTP

/**
 * PolarLobby - Hoofdklasse van de plugin
 * 
 * Deze plugin beheert de lobby ervaring voor de PolarMC Minecraft server.
 * Het registreert verschillende event listeners en commando's om:
 * - Spelers naar spawn te teleporteren bij join/respawn
 * - Schade en honger te voorkomen (indien ingeschakeld)
 * - Blok plaatsen/breken te beperken (tenzij toegestaan door admin)
 * - Spelers te teleporteren wanneer ze te ver in de void vallen
 */
class PolarLobby : JavaPlugin() {
    // Shared instance voor blok break/plaats permissie beheer
    // Deze wordt gedeeld tussen commando's en event listeners
    lateinit var allowBlockBreak: AllowBlockBreak

    /**
     * Wordt aangeroepen wanneer de plugin wordt ingeschakeld.
     * Hier gebeurt de volledige initialisatie van de plugin.
     */
    override fun onEnable() {
        // Kopieer config.yml van jar -> plugins/PolarLobby/config.yml bij de eerste run
        // Dit zorgt ervoor dat er altijd een standaard configuratie beschikbaar is
        saveDefaultConfig()
        reloadConfig()

        // Registreer spawn interceptor - teleporteert spelers naar spawn bij join en respawn
        InterceptSpawn(this).register()

        // Registreer anti-damage listener indien ingeschakeld in config
        // Dit voorkomt PvP en honger in de lobby
        if (config.getBoolean("antidamage", true)) {
            AntiDamage(this).register()
        }

        // Initialiseer block break manager
        // Deze shared instance houdt bij welke spelers toestemming hebben om blokken te plaatsen/breken
        allowBlockBreak = AllowBlockBreak(this)

        // Registreer block break/place listener alleen als allowBreak false is
        // Als allowBreak true is, kunnen alle spelers blokken plaatsen/breken
        val allowBreak = config.getBoolean("allowBreak", true)
        if (!allowBreak) {
            allowBlockBreak.register()
        }

        // Registreer void teleportatie - teleporteert spelers terug naar spawn
        // wanneer ze onder een bepaalde Y-waarde komen (standaard -64)
        VoidTP(this).register()

        // Registreer het hoofdcommando (/pl of /polarlobby)
        // Dit commando geeft toegang tot alle subcommando's zoals setspawn, allowbreak, etc.
        val mainCommand = MainCommand(this)
        val cmd = getCommand("pl")
        if (cmd == null) {
            // Als het commando niet bestaat, is er iets mis met de build
            // Dit zou normaal nooit moeten gebeuren
            ServerConsole.error("FATAL: pl command does not exist, shutting down plugin!")
            ServerConsole.error("You have a faulty build of PolarLobby!")
            ServerConsole.error("Please try reinstalling the plugin. If you built PolarLobby yourself, create an issue on GitHub: https://github.com/PolarNL/PolarLobby/issues .")
            server.pluginManager.disablePlugin(this)
        } else {
            cmd.setExecutor(mainCommand)
            cmd.tabCompleter = mainCommand
        }

        // Laat weten dat de plugin succesvol is ingeschakeld
        ServerConsole.info(
            "<bold><#38bdf8>Polar<#e0f2fe>Lobby</bold><white> has been successfully enabled!"
        )
    }

    /**
     * Wordt aangeroepen wanneer de plugin wordt uitgeschakeld.
     * Laat een bericht zien in de console.
     */
    override fun onDisable() {
        ServerConsole.info(
            "<bold><#38bdf8>Polar<#e0f2fe>Lobby</bold><white> has been successfully disabled!"
        )
    }
}
