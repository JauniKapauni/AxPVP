package de.jaunikapauni.axpvp;

import de.jaunikapauni.axpvp.command.PVPCommand;
import de.jaunikapauni.axpvp.listener.EntityDamageByEntityListener;
import de.jaunikapauni.axpvp.listener.PlayerJoinListener;
import de.jaunikapauni.axpvp.manager.DatabaseManager;
import de.jaunikapauni.axpvp.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxPVP extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }
    PlayerManager playerManager;
    public PlayerManager getPlayerManager(){
        return playerManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        databaseManager = new DatabaseManager(this);
        playerManager = new PlayerManager(this);
        if (!databaseManager.initDatabaseTable1()) {
            getLogger().severe("Error creating db table!");
            Bukkit.getServer().shutdown();
        }
        getCommand("pvp").setExecutor(new PVPCommand(this));
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
