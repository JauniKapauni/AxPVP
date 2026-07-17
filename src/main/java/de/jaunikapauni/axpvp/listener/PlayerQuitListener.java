package de.jaunikapauni.axpvp.listener;

import de.jaunikapauni.axpvp.AxPVP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    AxPVP reference;
    public PlayerQuitListener(AxPVP reference){
        this.reference = reference;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        reference.getPlayerManager().removePlayer(e.getPlayer());
    }
}
