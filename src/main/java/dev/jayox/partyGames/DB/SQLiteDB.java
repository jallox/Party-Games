package dev.jayox.partyGames.DB;

import dev.jayox.partyGames.PartyGames;
import org.checkerframework.framework.qual.Unused;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB implements Database {
    private PartyGames plugin;
    private Connection connection;

    public SQLiteDB(PartyGames plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        String url = "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/database.db";
        try {
            connection = DriverManager.getConnection(url);
            // createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            init();
        }
        return connection;
    }
    private void createTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS example_table (id INTEGER PRIMARY KEY, name TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}
