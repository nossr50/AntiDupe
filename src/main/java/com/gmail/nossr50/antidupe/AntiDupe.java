package com.gmail.nossr50.antidupe;

import com.gmail.nossr50.antidupe.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin was made for floris to solve a problem on his server involving duping by dropping items off boats
 */
public class AntiDupe extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getLogger().info("AntiDupe is loaded.");

    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("AntiDupe has been disabled.");
    }

}
