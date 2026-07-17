package de.jaunikapauni.axpvp.command;

import de.jaunikapauni.axpvp.AxPVP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PVPCommand implements CommandExecutor {
    AxPVP reference;
    public PVPCommand(AxPVP reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axpvp.pvp")){
            p.sendMessage("You don't have the permission! [axpvp.pvp]");
            return true;
        }
        boolean pvpStatus = reference.getPlayerManager().getPVPStatus(p);
        if(pvpStatus){
            reference.getPlayerManager().togglePVPStatus(p);
            p.sendActionBar("PVP disabled!");
        } else {
            reference.getPlayerManager().togglePVPStatus(p);
            p.sendActionBar("PVP enabled!");
        }
        return true;
    }
}
