package dev.jayox.partyGames.DB;

import dev.jayox.partyGames.PartyGames;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DBManager {
    private PartyGames plugin;
    private String dbType;
    private Database database;

    public DBManager(PartyGames plugin) throws SQLException {
        this.plugin = plugin;
        this.dbType = plugin.getConfigManager().getConfig().getString("database.type"); // MySQL or SQLite
        initDB();
    }

    public void initDB() throws SQLException {
        if (dbType.equalsIgnoreCase("MySQL")) {
            database = new MySQLDB(plugin);
        } else if (dbType.equalsIgnoreCase("SQLite")) {
            database = new SQLiteDB(plugin);
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
        String createTableQuery = "CREATE TABLE IF NOT EXISTS party (" +
                "partyId TEXT PRIMARY KEY, " +
                "partyName TEXT, " +
                "ownerName TEXT, " +
                "ownerUUID TEXT, " +
                "inGame BOOLEAN DEFAULT 0)";
        query(createTableQuery, null);
        database.init();
    }

    public void query(String sql, Object[] params) {
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Database error: " + e.getMessage());
        }
    }
    public ResultSet queryResult(String sql, Object[] params) {
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            plugin.getLogger().severe("Database error: " + e.getMessage());
            return null;
        }
    }
}
