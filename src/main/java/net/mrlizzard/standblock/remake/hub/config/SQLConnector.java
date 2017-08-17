package net.mrlizzard.standblock.remake.hub.config;

import net.mrlizzard.standblock.remake.hub.StandBlockHUB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

/**
 * SQLConnector abstract class.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public abstract class SQLConnector {

    protected Connection          connection;
    protected StandBlockHUB       plugin;

    public abstract void initiateConnection();

    public Connection getConnection() {
        return connection;
    }

    public abstract ResultSet query(String query);

    public abstract void updateQuery(String query, Map<String, Object> datas);

}
