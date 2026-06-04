package de.jaunikapauni.axpvp.placeholder;

import de.jaunikapauni.axpvp.AxPVP;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PVPPlaceholder extends PlaceholderExpansion {
    AxPVP reference;
    public PVPPlaceholder(AxPVP reference){
        this.reference = reference;
    }

    @Override
    public @NotNull String getIdentifier() {
        return reference.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return (String.join("", reference.getDescription().getAuthors()));
    }

    @Override
    public @NotNull String getVersion() {
        return reference.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer p, @NotNull String params){
        if(params.equalsIgnoreCase("pvp")){
            boolean pvpStatus = reference.getPlayerManager().getPVPStatus((Player) p);
            if(pvpStatus){
                return "Activated!";
            } else {
                return "Deactivated!";
            }
        }
        return params;
    }
}
