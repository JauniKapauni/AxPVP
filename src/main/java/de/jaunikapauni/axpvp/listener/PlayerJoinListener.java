package de.jaunikapauni.axpvp.listener;

import de.jaunikapauni.axpvp.AxPVP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    AxPVP reference;
    public PlayerJoinListener(AxPVP reference){
        this.reference = reference;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        if(!reference.getPlayerManager().playerInDB(p)){
            reference.getPlayerManager().fillTable(p);
        }
    }
}
