package de.jaunikapauni.axpvp.manager;

import de.jaunikapauni.axpvp.AxPVP;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    AxPVP reference;
    public PlayerManager(AxPVP reference){
        this.reference = reference;
    }
    Map<UUID, Boolean> cache = new ConcurrentHashMap<>();

    public void loadPlayer(Player p){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT pvp_status FROM players WHERE uuid = ?")){
                ps.setString(1, p.getUniqueId().toString());
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        cache.put(p.getUniqueId(), rs.getBoolean("pvp_status"));
                    } else {
                        cache.put(p.getUniqueId(), false);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getPVPStatus(Player p){
        return cache.get(p.getUniqueId());
    }

    public boolean togglePVPStatus(Player p){
        UUID uuid = p.getUniqueId();
        boolean newStatus = !cache.get(uuid);
        cache.put(uuid, newStatus);
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET pvp_status = ? WHERE uuid = ?")){
                ps.setBoolean(1, newStatus);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newStatus;
    }

    public void fillTable(Player p){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("INSERT INTO players(uuid, pvp_status) VALUES(?, FALSE)")){
                ps.setString(1, p.getUniqueId().toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean playerInDB(Player p) throws SQLException {
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM players WHERE uuid = ?")){
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                return rs.next();
            }
        }
    }

    public void removePlayer(@NotNull Player player) {
        cache.remove(player.getUniqueId());
    }
}
