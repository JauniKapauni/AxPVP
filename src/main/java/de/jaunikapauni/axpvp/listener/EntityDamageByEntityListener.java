package de.jaunikapauni.axpvp.listener;

import de.jaunikapauni.axpvp.AxPVP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    AxPVP reference;
    public EntityDamageByEntityListener(AxPVP reference){
        this.reference = reference;
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player attacker) || !(e.getEntity() instanceof Player victim)){
            return;
        }
        boolean pvpStatusOfAttacker = reference.getPlayerManager().getPVPStatus(attacker);
        boolean pvpStatusOfVictim = reference.getPlayerManager().getPVPStatus(victim);
        if(!pvpStatusOfAttacker){
            attacker.sendActionBar("You have PVP disabled!");
            e.setCancelled(true);
        }
        if(!pvpStatusOfVictim){
            attacker.sendActionBar(victim.getName() + " has PVP disabled!");
            e.setCancelled(true);
        }
    }
}
