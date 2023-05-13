package me.scaffus.survival;

import org.bukkit.plugin.java.JavaPlugin;

public final class Survival extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
