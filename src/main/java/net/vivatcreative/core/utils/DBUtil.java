package net.vivatcreative.core.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    public static void closeSilently(ResultSet rs) {

        if (rs == null) return;
        Statement stmt = null;
        try {
            stmt = rs.getStatement();
        } catch (SQLException e) {
            Logger.exception(e);
        }

        try {
            rs.close();
            if (stmt != null) closeSilently(stmt);
        } catch (SQLException e) {
            Logger.exception(e);
        }
    }

    public static void closeSilently(Statement st) {
        if (st == null) return;
        try {
            st.close();
        } catch (SQLException e) {
            Logger.exception(e);
        }
    }

    public static void closeSilently(Connection co) {
        if (co == null) return;
        try {
            co.close();
        } catch (SQLException e) {
            Logger.exception(e);
        }
    }

}