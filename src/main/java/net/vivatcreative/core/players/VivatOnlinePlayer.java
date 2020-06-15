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

public class VivatOnlinePlayer implements VivatPlayer {

    // Variable declaration

    private final UUID uuid;
    private BuildRank buildRank;
    private StaffRank staffRank;
    private Title title;
    private String plotcountJSON;
    private PlotcountObject plotcountObject;
    private boolean reviewAvailable, rankupAvailable;

    // Constructor

    public VivatOnlinePlayer(UUID uuid) {
        this.uuid = uuid;
        loadFromDatabase();
    }

    // Database

    public void loadFromDatabase() {
        String statement = "SELECT * FROM vivat_users WHERE uuid = '%s'";
        MySQLDatabase.query(statement, (resultSet) -> {
            try {
                if (resultSet.next()) {
                    this.buildRank = BuildRank.valueOf(resultSet.getString("build_rank"));
                    this.staffRank = StaffRank.valueOf(resultSet.getString("staff_rank"));
                    try {
                        this.title = TitleManager.fromString(resultSet.getString("title"));
                    } catch (InvalidTitleException e){
                        Logger.exception(e);
                    }
                    this.plotcountJSON = resultSet.getString("plotcount");
                    this.reviewAvailable = resultSet.getBoolean("review_available");
                    this.rankupAvailable = resultSet.getBoolean("rankup_available");
                } else {
                    addPlayer();
                }
            } catch (SQLException | NullPointerException e) {
                Logger.exception(e);
            }
        }, uuid.toString());
        if(buildRank == null) loadFromDatabase();
        else plotcountObject = JsonUtil.fromJSON(plotcountJSON, PlotcountObject.class);
    }

    public void saveToDatabase() {
        plotcountJSON = JsonUtil.toJSON(plotcountObject);
        String statement = "UPDATE vivat_users SET build_rank = '%s', staff_rank = '%s', title = '%s'" + (plotcountJSON == null ? "" : ", plotcount = '%s'") + " WHERE uuid = '%s'";
        MySQLDatabase.update(statement, buildRank, staffRank, title, plotcountJSON, uuid.toString());
    }

    private void saveValue(String column, String value) {
        String statement = "UPDATE vivat_users SET `%s` = '%s' WHERE uuid = '%s'";
        MySQLDatabase.update(statement, column, value, uuid.toString());
    }

    private void savePlotcount() {
        plotcountJSON = JsonUtil.toJSON(plotcountObject);
        String statement = "UPDATE vivat_users SET plotcount = '%s' WHERE uuid = '%s'";
        MySQLDatabase.update(statement, plotcountJSON, uuid.toString());
    }

    // Setters

    @Override
    public void setBuildRank(BuildRank rank) {
        if (rank == buildRank) return;
        buildRank = rank;
        saveValue("build_rank", buildRank.toString());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                String.format("lp user %s parent settrack build %s", Bukkit.getOfflinePlayer(uuid).getName(), rank.toString().toLowerCase()));
    }

    @Override
    public void setStaffRank(StaffRank rank) {
        if (rank == staffRank) return;
        staffRank = rank;
        saveValue("staff_rank", staffRank.toString());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                String.format("lp user %s parent settrack staff %s", Bukkit.getOfflinePlayer(uuid).getName(), rank.toString().toLowerCase()));
    }

    @Override
    public void setTitle(String title) throws InvalidTitleException {
        if (this.title.getKey().equalsIgnoreCase(title)) return;
        this.title = TitleManager.fromString(title);
        saveValue("title", title.toUpperCase());
    }

    @Override
    public void setPlotcount(VivatWorld world, int amount) {
        switch (world) {
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
            case FREEBUILD:
                plotcountObject.freebuild = amount;
                break;
            default:
                return;
        }
        savePlotcount();
    }

    @Override
    public void setReviewAvailable(boolean available){
        this.reviewAvailable = available;
        this.saveValue("review_available", available ? "1" : "0");
    }

    @Override
    public void setRankupAvailable(boolean available){
        this.rankupAvailable = available;
        this.saveValue("rankup_available", available ? "1" : "0");
    }

    // Getters

    @Override
    public BuildRank getBuildRank() {
        return buildRank;
    }

    @Override
    public StaffRank getStaffRank() {
        return staffRank;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public int getPlotcount(VivatWorld world) {
        switch (world) {
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
            case FREEBUILD:
                return plotcountObject.freebuild;
            default:
                return 0;
        }
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean getReviewAvailable(){ return reviewAvailable; }

    @Override
    public boolean getRankupAvailable() { return rankupAvailable; }

    // Comparison

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof VivatPlayer)) return false;
        VivatPlayer p = (VivatPlayer) o;
        return this.uuid.equals(p.getUUID());
    }

    @Override
    public int hashCode() {
        return 31 * this.uuid.hashCode();
    }

}
