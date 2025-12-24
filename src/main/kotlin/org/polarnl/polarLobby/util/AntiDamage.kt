package org.polarnl.polarLobby.util

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.polarnl.polarLobby.PolarLobby

/**
 * AntiDamage - Bescherming systeem voor de lobby
 * 
 * Deze klasse voorkomt dat spelers:
 * - Elkaar schade toebrengen (PvP)
 * - Honger krijgen
 * 
 * Admins met de "polarLobby.admin" permissie zijn vrijgesteld van PvP bescherming,
 * zodat zij indien nodig kunnen ingrijpen.
 */
class AntiDamage(private val plugin: PolarLobby): Listener {
    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    /**
     * Voorkomt dat spelers elkaar schade toebrengen.
     * Admins kunnen nog steeds schade toebrengen (handig voor moderatie).
     */
    @EventHandler
    fun onDamage(event: org.bukkit.event.entity.EntityDamageEvent) {
        if (event is org.bukkit.event.entity.EntityDamageByEntityEvent) {
            val damager = event.damager
            if (damager is Player) {
                // Sta admins toe om schade toe te brengen
                if (damager.hasPermission("polarLobby.admin")) {
                    return
                }
                // Voorkom dat spelers andere spelers schade toebrengen
                if (event.entity is Player) event.isCancelled = true
            }
        }
    }
    
    /**
     * Voorkomt dat spelers honger krijgen in de lobby.
     * Dit zorgt ervoor dat de honger balk altijd vol blijft.
     */
    @EventHandler
    fun onFoodLevelChange(event: org.bukkit.event.entity.FoodLevelChangeEvent) {
        if (event.entity is Player) {
            event.isCancelled = true
        }
    }
}