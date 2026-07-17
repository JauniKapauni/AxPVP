package de.jaunikapauni.axpvp.manager;

import de.jaunikapauni.axpvp.AxPVP;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerManager {
    AxPVP reference;
    public PlayerManager(AxPVP reference){
        this.reference = reference;
    }

    public boolean getPVPStatus(Player p){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM players WHERE uuid = ?")){
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getBoolean("pvp_status");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public void togglePVPStatus(Player p){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            boolean pvpStatus = getPVPStatus(p);
            if(pvpStatus){
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET pvp_status = FALSE WHERE uuid = ?")){
                    ps.setString(1, p.getUniqueId().toString());
                    ps.executeUpdate();
                }
            } else {
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET pvp_status = TRUE WHERE uuid = ?")){
                    ps.setString(1, p.getUniqueId().toString());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
