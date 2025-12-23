package org.polarnl.polarLobby.util

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.polarnl.polarLobby.PolarLobby
import java.util.UUID

class AllowBlockBreak(private val plugin: PolarLobby) : Listener {
    val allowedPlayers: MutableSet<UUID> = mutableSetOf()

    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (allowedPlayers.contains(event.player.uniqueId)) {
            return
        }
        event.isCancelled = true
        PlayerMessage().send("<red><bold>You cannot break blocks in this server", event.player)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (allowedPlayers.contains(event.player.uniqueId)) {
            return
        }
        event.isCancelled = true
        PlayerMessage().send("<red><bold>You cannot place blocks in this server", event.player)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        val block = event.clickedBlock ?: return
        val player = event.player

        if (allowedPlayers.contains(player.uniqueId)) {
            return
        }

        val blockedBlocks = setOf(
            Material.CHEST, Material.TRAPPED_CHEST, Material.ENDER_CHEST,
            Material.BARREL, Material.FURNACE, Material.BLAST_FURNACE,
            Material.SMOKER, Material.HOPPER, Material.DROPPER,
            Material.DISPENSER, Material.CRAFTING_TABLE, Material.ENCHANTING_TABLE,
            Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL,
            Material.BREWING_STAND, Material.BEACON,
            Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR,
            Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR,
            Material.CRIMSON_DOOR, Material.WARPED_DOOR, Material.IRON_DOOR,
            Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.BIRCH_TRAPDOOR,
            Material.JUNGLE_TRAPDOOR, Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR,
            Material.CRIMSON_TRAPDOOR, Material.WARPED_TRAPDOOR, Material.IRON_TRAPDOOR,
            Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE,
            Material.JUNGLE_FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE,
            Material.CRIMSON_FENCE_GATE, Material.WARPED_FENCE_GATE,
            Material.STONE_BUTTON, Material.OAK_BUTTON, Material.LEVER, Material.COMPOSTER
        )

        if (block.type in blockedBlocks) {
            event.isCancelled = true
            PlayerMessage().send("<red><bold>You cannot interact with blocks in this server", player)
        }
    }
}