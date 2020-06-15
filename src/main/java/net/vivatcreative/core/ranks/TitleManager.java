package net.vivatcreative.core.ranks;

import net.vivatcreative.core.database.MySQLDatabase;
import net.vivatcreative.core.exceptions.InvalidTitleException;
import net.vivatcreative.core.utils.Logger;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TitleManager {

    private final Set<Title> titles = new HashSet<>();
    private static final TitleManager INSTANCE = new TitleManager();

    private TitleManager() {
    }

    /**
     * Registers a title.
     *
     * @param title the title that needs to be registered
     */
    public static void register(Title title) {
        INSTANCE.titles.add(title);
    }

    public static void clearEntries() {
        INSTANCE.titles.clear();
    }

    public static Set<Title> getTitles() {
        return INSTANCE.titles;
    }

    public static void loadFromDatabase(){
        String statement = "SELECT * FROM vivat_titles";
        MySQLDatabase.query(statement, (resultSet) -> {
            String key, name;
            int blockData;
            String[] requirements, description;
                try {
                    while(resultSet.next()) {
                        key = resultSet.getString("key");
                        name = resultSet.getString("name");
                        blockData = resultSet.getInt("block_data");
                        requirements = resultSet.getString("requirements").split("<br>");
                        description = resultSet.getString("requirements").split("<br>");
                        INSTANCE.titles.add(new Title(key, name, blockData, description, requirements));
                    }
                } catch (SQLException e) {
                    Logger.exception(e);
                }
        });
    }

    /**
     * @param titleName the name of the title
     * @return the TitleRank object from the name
     */
    public static Title fromString(String titleName) throws InvalidTitleException {
        for (Title rank : INSTANCE.titles)
            if (rank.getKey().equalsIgnoreCase(titleName)) return rank;
        throw new InvalidTitleException(titleName);
    }


}
