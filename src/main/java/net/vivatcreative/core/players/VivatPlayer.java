package net.vivatcreative.core.players;

import net.vivatcreative.core.database.MySQLDatabase;
import net.vivatcreative.core.exceptions.InvalidTitleException;
import net.vivatcreative.core.ranks.BuildRank;
import net.vivatcreative.core.ranks.StaffRank;
import net.vivatcreative.core.ranks.Title;
import net.vivatcreative.core.utils.VivatWorld;
import org.bukkit.Bukkit;

import java.util.UUID;

// https://www.baeldung.com/jackson-object-mapper-tutorial
public interface VivatPlayer {

    // Database

    default void addPlayer(){
        String query = "INSERT INTO `vivat_users`(uuid,player_name) VALUES ('%s','%s')";
        MySQLDatabase.update(query, getUUID().toString(), Bukkit.getOfflinePlayer(getUUID()).getName());
    }


    // Setters

    void setBuildRank(BuildRank rank);
    void setStaffRank(StaffRank rank);
    void setTitle(String title) throws InvalidTitleException;
    void setPlotcount(VivatWorld world, int amount);
    void setReviewAvailable(boolean available);
    void setRankupAvailable(boolean available);

    // Getters

    int getPlotcount(VivatWorld world);
    UUID getUUID();
    BuildRank getBuildRank();
    StaffRank getStaffRank();
    Title getTitle() throws InvalidTitleException;
    boolean getReviewAvailable();
    boolean getRankupAvailable();

    // Comparison

    boolean equals(Object o);
    int hashCode();
}




