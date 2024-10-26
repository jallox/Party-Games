package dev.jayox.partyGames.DB;

import dev.jayox.partyGames.PartyGames;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDB implements Database {
    private PartyGames plugin;
    private Connection connection;

    public MySQLDB(PartyGames plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        String host = plugin.getConfigManager().getConfig().getString("database.mysql.host");
        String port = plugin.getConfigManager().getConfig().getString("database.mysql.port");
        String database = plugin.getConfigManager().getConfig().getString("database.mysql.database");
        String user = plugin.getConfigManager().getConfig().getString("database.mysql.user");
        String password = plugin.getConfigManager().getConfig().getString("database.mysql.password");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            connection = DriverManager.getConnection(url, user, password);
            createTables();
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
        String sql = "CREATE TABLE IF NOT EXISTS example_table (id INT PRIMARY KEY, name VARCHAR(255))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}
