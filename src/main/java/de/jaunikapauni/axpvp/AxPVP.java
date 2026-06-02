package de.jaunikapauni.axpvp;

import de.jaunikapauni.axpvp.manager.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxPVP extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        databaseManager = new DatabaseManager(this);
        if (!databaseManager.initDatabaseTable1()) {
            getLogger().severe("Error creating db table!");
            Bukkit.getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
