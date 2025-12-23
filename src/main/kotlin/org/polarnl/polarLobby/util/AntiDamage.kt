package org.polarnl.polarLobby.util

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.polarnl.polarLobby.PolarLobby

class AntiDamage(private val plugin: PolarLobby): Listener {
    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onDamage(event: org.bukkit.event.entity.EntityDamageEvent) {
        if (event is org.bukkit.event.entity.EntityDamageByEntityEvent) {
            val damager = event.damager
            if (damager is Player) {
                if (damager.hasPermission("polarLobby.admin")) {
                    return
                }
                if (event.entity is Player) event.isCancelled = true
            }
        }
    }
    @EventHandler
    fun onFoodLevelChange(event: org.bukkit.event.entity.FoodLevelChangeEvent) {
        if (event.entity is Player) {
            event.isCancelled = true
        }
    }
}