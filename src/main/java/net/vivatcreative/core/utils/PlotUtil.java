package net.vivatcreative.core.utils;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.entity.Player;

public class PlotUtil {

    // Plot Object

    /**
     * @param world is the world where the plot is located
     * @param id is the Id of the plot
     * @return a Plot from a given world and a given PlotId.
     */
    public static Plot getPlot(String world, String id) {
        return PS.get().getPlot(PS.get().getPlotArea(world, id), PlotId.fromString(id));
    }

    /**
     * @param p is the player who'se current Plot is requested
     * @return the Plot a Player is currently standing on.
     */
    public static Plot getCurrentPlot(Player p) {
        return PlotPlayer.wrap(p).getCurrentPlot();
    }

    // Conversion

    /**
     * Returns String containing Plot info: {worldname}:{plotid}
     * @return String containing a Plots world and a Plots Id
     */
    public static String formatPlot(Plot plot) {
        return plot.getWorldName() + ":" + plot.getId().toString();
    }

    /**
     * @param plotLocation is the location of the plot, indicated by world:id
     * @return a Plot from a String containing a worldname and a PlotId.
     */
    public static Plot getPlot(String plotLocation) {
        String world = getWorld(plotLocation);
        String id = getPlotId(plotLocation);
        return getPlot(world, id);
    }

    /**
     * @param plotLocation is the location of the plot, indicated by world:id
     * @return String with the name of the world of a Plot
     */
    public static String getWorld(String plotLocation) {
        return plotLocation.substring(0, plotLocation.indexOf(":"));
    }

    /**
     * @param plotLocation is the location of the plot, indicated by world:id
     * @return String with the id of the world of a Plot
     */
    public static String getPlotId(String plotLocation) {
        return plotLocation.substring(plotLocation.indexOf(":") + 1);
    }
}
