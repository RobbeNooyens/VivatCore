package net.vivatcreative.core.database;

import net.vivatcreative.core.VivatCore;
import net.vivatcreative.core.utils.DBUtil;
import net.vivatcreative.core.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.function.Consumer;

public class MySQLDatabase {

    private String user, database, password, port, hostname;
    private Connection connection = null;
    private static final MySQLDatabase db = new MySQLDatabase();

    /**
     * Creates a new MySQL instance.
     */
    private MySQLDatabase() {
    }

    public void reconnect() {
        VivatCore.get().reloadConfig();
        FileConfiguration config = VivatCore.get().getConfig();
        this.hostname = config.getString("database.host");
        this.port = config.getString("database.port");
        this.database = config.getString("database.database");
        this.user = config.getString("database.username");
        this.password = config.getString("database.password");

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Logger.info("VivatCore >> MySQL Disconnected");
            }
        } catch (SQLException | NullPointerException e) {
            Logger.exception(e);
            return;
        }
        openConnection();
    }

    private void openConnection() {
        try {
            synchronized (this) {
                if (connection != null && !connection.isClosed()) return;

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection(
                        "jdbc:mysql://" + this.hostname + ':' + this.port + '/' + this.database + "?useSSL=false",
                        this.user, this.password));
                Logger.info("VivatCore >> MySQL Connected");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static MySQLDatabase get() {
        return db;
    }

    public static void update(String query, Object... placeholders){
        db.updateSQL(placeholders == null ? query : String.format(query, placeholders));
    }

    public static void query(String query, Consumer<ResultSet> result, Object... placeholders){
        db.querySQL(placeholders == null ? query : String.format(query, placeholders), result);
    }

    private void updateSQL(String query) {
        Logger.info("VivatCore >> " + query);
        openConnection();
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (Exception e) {
            Logger.exception(e);
        } finally {
            DBUtil.closeSilently(statement);
        }
    }

    private void querySQL(String query, Consumer<ResultSet> result) {
        openConnection();
        Logger.info("VivatCore >> " + query);
        PreparedStatement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            result.accept(resultSet);
        } catch (Exception e) {
            Logger.exception(e);
        } finally {
            DBUtil.closeSilently(resultSet);
        }

    }
}
