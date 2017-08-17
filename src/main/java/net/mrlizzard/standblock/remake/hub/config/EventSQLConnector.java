package net.mrlizzard.standblock.remake.hub.config;

import net.mrlizzard.standblock.remake.hub.StandBlockHUB;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.Map;

/**
 * EventSQLConnector class.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public class EventSQLConnector extends SQLConnector {

    public EventSQLConnector(StandBlockHUB plugin) {
        this.plugin = plugin;
    }

    @Override
    public void initiateConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://<host>/<database>?user=<user>&password=<password>";
            url = StringUtils.replace(url, "<host>", plugin.getConfig().getString("mysql.host", "127.0.0.1"));
            url = StringUtils.replace(url, "<database>", plugin.getConfig().getString("mysql.database", "event"));
            url = StringUtils.replace(url, "<user>", plugin.getConfig().getString("mysql.username", "root"));
            url = StringUtils.replace(url, "<password>", plugin.getConfig().getString("mysql.password", "root"));

            connection = DriverManager.getConnection(url);
        } catch(Exception error) {
            plugin.consoleErrorLog("§cImpossible de mettre en place la connection SQL.", error);
        }
    }

    @Override
    public ResultSet query(String query) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            return resultSet;
        } catch(SQLException error) {
            plugin.consoleErrorLog("§cErreur lors du traitement de la requête", error);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException ignored) {}
            }

            if(statement != null) {
                try {
                    statement.close();
                } catch(SQLException ignored) {}
            }
        }

        return null;
    }

    @Override
    public void updateQuery(String query, Map<String, Object> datas) {
        PreparedStatement preparedStatement = null;

        try {
            int loop = 0;
            preparedStatement = connection.prepareStatement(query);

            for (Map.Entry<String, Object> entry : datas.entrySet()) {
                if(entry.getKey().equals("int")) preparedStatement.setInt(loop, ((int) entry.getValue()));
                else if(entry.getKey().equals("string")) preparedStatement.setString(loop, ((String) entry.getValue()));

                loop++;
            }

            preparedStatement.executeUpdate();
        } catch(SQLException error) {
            plugin.consoleErrorLog("§cErreur lors du traitement de la requête", error);
        } finally {
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException ignored) {}
            }
        }
    }

}
