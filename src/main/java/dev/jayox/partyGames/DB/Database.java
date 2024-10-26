package dev.jayox.partyGames.DB;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    void init();
    Connection getConnection() throws SQLException;
}
