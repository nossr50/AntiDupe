package com.gmail.nossr50.antidupe.listeners;

import com.gmail.nossr50.antidupe.AntiDupe;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;

/**
 * This plugin was made for floris to solve a problem on his server involving duping by dropping items off boats
 */

public class PlayerListener implements Listener {
    private AntiDupe antiDupe;

    private boolean entityPluginsRecorded = false;
    private boolean playerDropPluginsRecorded = false;

    ArrayList<String> pluginsUsingThisEvent = new ArrayList<>();


    public PlayerListener(AntiDupe antiDupe)
    {
        this.antiDupe = antiDupe;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDropItemEvent(EntityDropItemEvent event)
    {
        if(entityPluginsRecorded == false)
        {
            blamePlugins(event.getHandlers());

            entityPluginsRecorded = true;
        }

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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event)
    {
        if(playerDropPluginsRecorded == false)
        {
            blamePlugins(event.getHandlers());

            playerDropPluginsRecorded = true;
        }

        if(event.isCancelled())
            return;

        Player player = event.getPlayer();
        if(player.isInsideVehicle())
        {
            event.setCancelled(true);
            alertAdmins(player);
        }
    }

    private void blamePlugins(HandlerList handlers) {
        for (RegisteredListener otherPlugins : handlers.getRegisteredListeners()) {
            pluginsUsingThisEvent.add(otherPlugins.getPlugin().getName());
        }

        System.out.print("[nossr50 BEGIN ANTI DUPE PLUGIN BLAME REPORT]");
        for (String s : pluginsUsingThisEvent) {

            System.out.println(s + " is interacting with the EntityDropItemEvent!");
        }
        System.out.print("[nossr50 END ANTI DUPE PLUGIN BLAME REPORT]");
    }

    private void alertAdmins(Player player) {
        for (Player admin : antiDupe.getServer().getOnlinePlayers()) {
            if (admin.isOp()) {
                admin.sendMessage(ChatColor.RED+"[nossr50 exploit alert] " +ChatColor.YELLOW+ player.getName() +ChatColor.WHITE+ " was trying to drop an item from a vehicle!");
            }
        }
    }
}
