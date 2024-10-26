package dev.jayox.partyGames.DB;

import dev.jayox.partyGames.PartyGames;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DBManager {
    private final PartyGames plugin;
    private final String dbType;
    private Database database;

    public DBManager(PartyGames plugin, String dbType) throws SQLException {
        this.plugin = plugin;
        this.dbType = dbType; // MySQL or SQLite
        initDB(dbType);
    }

    public void initDB(String dbType) throws SQLException {
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
                "inGame BOOLEAN DEFAULT 0," +
                "invitedPlayers TEXT," +
                "inviteToken TEXT" +
                ");";
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
    public ResultSet queryResult(String sql, Object[] params) throws SQLException {
        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
        }
        return statement.executeQuery(); // Remember to close this ResultSet in the calling method
    }
}
