package net.vivatcreative.core.players;

import net.vivatcreative.core.database.MySQLDatabase;
import net.vivatcreative.core.exceptions.InvalidTitleException;
import net.vivatcreative.core.ranks.BuildRank;
import net.vivatcreative.core.ranks.StaffRank;
import net.vivatcreative.core.ranks.Title;
import net.vivatcreative.core.ranks.TitleManager;
import net.vivatcreative.core.utils.JsonUtil;
import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.core.utils.VivatWorld;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.UUID;

public class VivatOfflinePlayer implements VivatPlayer {

    // Variable declarations

    private static final String UPDATE_FIELD = "UPDATE vivat_users SET `%s` = '%s' WHERE `uuid` = '%s'",
            GET_FIELD = "SELECT `%s` FROM vivat_users WHERE `uuid` = '%s'";
    private final UUID uuid;

    // Constructor

    public VivatOfflinePlayer(UUID uuid) { this.uuid = uuid; }

    // Database

    private String getString(String column){
        String[] result = new String[1];
        MySQLDatabase.query(GET_FIELD, (resultSet) -> {
            try {
                if (resultSet.next())
                    result[0] = resultSet.getString(column);
            } catch (SQLException | NullPointerException e) {
                Logger.exception(e);
            }
        }, column, uuid.toString());
        return result[0];
    }

    private boolean getBoolean(String column){
        boolean[] result = new boolean[1];
        MySQLDatabase.query(GET_FIELD, (resultSet) -> {
            try {
                if (resultSet.next())
                    result[0] = resultSet.getBoolean(column);
            } catch (SQLException | NullPointerException e) {
                Logger.exception(e);
            }
        }, column, uuid.toString());
        return result[0];
    }

    private void set(String key, String v){ MySQLDatabase.update(UPDATE_FIELD, key, v, uuid.toString()); }

    // Setters

    @Override
    public void setBuildRank(BuildRank rank) {
        set("build_rank", rank.toString());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                String.format("lp user %s parent settrack build %s", Bukkit.getOfflinePlayer(uuid).getName(), rank.toString().toLowerCase()));
    }

    @Override
    public void setStaffRank(StaffRank rank) {
        set("staff_rank", rank.toString());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                String.format("lp user %s parent settrack staff %s",
                        Bukkit.getOfflinePlayer(uuid).getName(), rank.toString().toLowerCase()));
    }

    @Override
    public void setTitle(String title) throws InvalidTitleException {
        Title t = TitleManager.fromString(title);
        set("title", t.getKey());
    }

    @Override
    public void setPlotcount(VivatWorld world, int amount) {
        PlotcountObject plotcountObject = JsonUtil.fromJSON(getString("plotcount"), PlotcountObject.class);
        switch (world) {
            case FREEBUILD:
                plotcountObject.freebuild = amount;
                break;
            case BRONZE:
                plotcountObject.bronze = amount;
                break;
            case SILVER:
                plotcountObject.silver = amount;
                break;
            case GOLD:
                plotcountObject.gold = amount;
                break;
            case DIAMOND:
                plotcountObject.diamond = amount;
                break;
            case EMERALD:
                plotcountObject.emerald = amount;
                break;
            case MASTER:
                plotcountObject.master = amount;
                break;
        }
        set("plotcount", JsonUtil.toJSON(plotcountObject));
    }

    @Override
    public void setReviewAvailable(boolean available) { set("review_available", available ? "1" : "0"); }

    @Override
    public void setRankupAvailable(boolean available) { set("rankup_available", available ? "1" : "0"); }

    // Getters

    @Override
    public int getPlotcount(VivatWorld world) {
        PlotcountObject plotcountObject = JsonUtil.fromJSON(getString("plotcount"), PlotcountObject.class);
        switch (world) {
            case FREEBUILD:
                return plotcountObject.freebuild;
            case BRONZE:
                return plotcountObject.bronze;
            case SILVER:
                return plotcountObject.silver;
            case GOLD:
                return plotcountObject.gold;
            case DIAMOND:
                return plotcountObject.diamond;
            case EMERALD:
                return plotcountObject.emerald;
            case MASTER:
                return plotcountObject.master;
            default:
                return 0;
        }
    }



    @Override
    public UUID getUUID() { return uuid; }

    @Override
    public BuildRank getBuildRank() { return BuildRank.fromString(getString("build_rank")); }

    @Override
    public StaffRank getStaffRank() { return StaffRank.fromString(getString("staff_rank")); }

    @Override
    public Title getTitle() throws InvalidTitleException { return TitleManager.fromString(getString("title")); }

    @Override
    public boolean getReviewAvailable() { return getBoolean("review_available"); }

    @Override
    public boolean getRankupAvailable() { return getBoolean("rankup_available"); }
}
