package net.mrlizzard.standblock.remake.hub.config;

import net.mrlizzard.standblock.remake.hub.StandBlockHUB;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * EventSQLConnector class.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public class EventSQLConnector extends SQLConnector {

    public EventSQLConnector(StandBlockHUB plugin) {
        this.plugin = plugin;
        this.username = plugin.getConfig().getString("mysql.username", "root");
        this.password = plugin.getConfig().getString("mysql.password", "root");
        this.database = plugin.getConfig().getString("mysql.database", "standblock-remake-event");
        this.tables = Arrays.asList("players");

        try {
            initiateConnection();
            checkTables();
        } catch(Exception ignored) {}
    }

    @Override
    public void initiateConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://<host>/<database>";
            url = StringUtils.replace(url, "<host>", this.plugin.getConfig().getString("mysql.host", "127.0.0.1"));
            url = StringUtils.replace(url, "<database>", this.database);

            this.plugin.consoleDebugLog("Mise en place de la connexion SQL. (" + url + ")");
            this.connection = DriverManager.getConnection(url, this.username, this.password);
            this.plugin.consoleLog("§fConnexion SQL établie avec succès.");
        } catch(Exception error) {
            if(error.getMessage().contains("Unknown database")) createDatabaseAndConnect();
            else this.plugin.consoleErrorLog("§cImpossible de mettre en place la connection SQL.", error);
        }
    }

    @Override
    public void createDatabaseAndConnect() {
        boolean exec = false;

        try {
            this.plugin.consoleLog("§fBase de donnée '" + this.database + "' inexistante. Création en cours...");

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://<host>/";
            url = StringUtils.replace(url, "<host>", this.plugin.getConfig().getString("mysql.host", "127.0.0.1"));

            this.connection = DriverManager.getConnection(url, this.username, this.password);
            this.queryDataManipulation("CREATE DATABASE `" + this.database + "`");
            this.plugin.consoleLog("§fBase de donnée crée avec succès. Importation des tables en cours... (#SOON)");
            this.importData(this.connection);
            this.plugin.consoleLog("§fTables importées avec succès.");

            exec = true;
        } catch(Exception error) {
            plugin.consoleErrorLog("§cImpossible de créer la base de donnée '" + this.database + "'", error);
            exec = false;
        } finally {
            this.connection = null;

            if(exec)
                initiateConnection();
        }
    }

    @Override
    public void checkTables() throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ArrayList tablesList = new ArrayList();
        ResultSet resultSet = databaseMetaData.getTables(this.database, null, null, null);

        try {
            while(resultSet.next()) {
                tablesList.add(resultSet.getString("TABLE_NAME"));
            }
        } finally {
            resultSet.close();
        }

        plugin.consoleLog("§fListe des tables présentes: §e" + StringUtils.join(tablesList, ", "));
        plugin.consoleLog("§f" + (((this.tables.size() - tablesList.size()) == 0) ? "Aucunes" : (this.tables.size() - tablesList.size())) + " tables manquantes.");
    }

    @Override
    public void importData(Connection conn) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            Statement statement = conn.createStatement();
            FileReader fileReader = new FileReader("data.sql");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            statement.execute(bufferedReader.readLine());
            statement.close();
        } catch(Exception error) {
            this.plugin.consoleErrorLog("§cImpossible d'importer les données depuis le fichier data.sql", error);
        }
    }

    private boolean queryDataManipulation(String query) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            boolean res = statement.execute(query);
            plugin.consoleDebugLog("Execution data manipulation query: §e" + query + "§f.");

            return res;
        } catch(SQLException error) {
            plugin.consoleErrorLog("§cErreur lors du traitement de la requête", error);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch(SQLException ignored) {}
            }
        }

        return false;
    }

    @Override
    public ResultSet query(String query) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            plugin.consoleDebugLog("Execution basic query: §e" + query + "§f.");
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            plugin.consoleDebugLog("Résultats retournés: " + resultSet.getFetchSize());

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
            plugin.consoleDebugLog("Execution update query: §e" + query + "§f.");

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

    @Override
    public void onDisable() {
        this.plugin.consoleLog("§fDéconnexion des modules SQL...");
        try {
            this.connection.close();
        } catch(Exception ignored) {}
    }
}
