package com.gmail.nossr50.antidupe.listeners;

import com.gmail.nossr50.antidupe.AntiDupe;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * This plugin was made for floris to solve a problem on his server involving duping by dropping items off boats
 */

public class PlayerListener implements Listener {
    private AntiDupe antiDupe;


    public PlayerListener(AntiDupe antiDupe)
    {
        this.antiDupe = antiDupe;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDropItemEvent(EntityDropItemEvent event)
    {
        if(event.isCancelled())
            return;

        if(event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if(player.isInsideVehicle())
            {
                event.setCancelled(true);
                alertAdmins(player);
            }
        }
    }

    private void alertAdmins(Player player) {
        for (Player admin : antiDupe.getServer().getOnlinePlayers()) {
            if (admin.isOp()) {
                admin.sendMessage(ChatColor.RED+"[nossr50 exploit alert] " +ChatColor.YELLOW+ player.getName() +ChatColor.WHITE+ " was trying to drop an item from a vehicle!");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event)
    {
        if(event.isCancelled())
            return;

        Player player = event.getPlayer();
        if(player.isInsideVehicle())
        {
            event.setCancelled(true);
            alertAdmins(player);
        }
    }
}
