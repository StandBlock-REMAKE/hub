package net.mrlizzard.standblock.remake.hub.config;

import net.mrlizzard.standblock.remake.hub.StandBlockHUB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * SQLConnector abstract class.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public abstract class SQLConnector {

    protected Connection            connection;
    protected StandBlockHUB         plugin;
    protected String                database, username, password;
    protected List<String>          tables;

    public abstract void initiateConnection();

    public abstract void createDatabaseAndConnect();

    public abstract void checkTables() throws SQLException;

    public abstract void importData(Connection conn);

    public Connection getConnection() {
        return connection;
    }

    public abstract ResultSet query(String query);

    public abstract void updateQuery(String query, Map<String, Object> datas);

    public abstract void onDisable();

    public List<String> getTables() {
        return tables;
    }

}
