package net.mrlizzard.standblock.remake.hub;

import net.mrlizzard.standblock.remake.hub.config.EventSQLConnector;
import net.mrlizzard.standblock.remake.hub.config.SQLConnector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * StandBlockHUB main class.
 * @author MrLizzard
 * @licence GNU General Public Licence v3.0
 */
public class StandBlockHUB extends JavaPlugin {

    private static StandBlockHUB    instance;

    private boolean                 debugMode;
    private SQLConnector            connector;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        File configFile = new File(getDataFolder(), "config.yml");

        if(!configFile.exists()) {
            consoleLog("§7Fichier de configuration inexistant. Création en cours...");
            configFile.mkdir();
            // TODO: Copie du contenu se trouvant dans resources/config.yml dans les sources du plugin (try/catch)
            consoleLog("§7Fichier de configuration créé avec succès !");
        }

        debugMode = getConfig().getBoolean("core.debug-mode", false);

        consoleLog("§7Démarrage de " + getDescription().getName() + " en version §c" + getDescription().getVersion() + "§7.");
        consoleLog("§fPlugin créé à l'occasion d'un évènement public pour StandBlock.");
        consoleLog("§fPlus d'informations à cette adresse: §bhttps://github.com/StandBlock-REMAKE/hub");
        consoleLog("§f" + getDescription().getAuthors().size() + " développeur" + (getDescription().getAuthors().size() > 1 ? "s" : "") + " ont participé" + (getDescription().getAuthors().size() > 1 ? "s" : "") + " au développement de ce plugin.");

        connector = new EventSQLConnector(this);
    }

    public void consoleLog(String message) {
        getServer().getConsoleSender().sendMessage("§f[§2" + getDescription().getName() + "§f] §r" + message);
    }

    public void consoleErrorLog(String message, Exception error) {
        consoleLog(message);

        if(debugMode) {
            consoleLog(ChatColor.LIGHT_PURPLE + "-> [DEBUG] §f" + error.getMessage());
        }
    }

    public SQLConnector getConnector() {
        return connector;
    }

    public static StandBlockHUB get() {
        return instance;
    }

    @Override
    public void onDisable() {
        instance = null;

        consoleLog("§7Kick des joueurs encore présents (sécurité supplémentaire)");
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cFermeture du serveur."));

        consoleLog("§7Nettoyage des entitées présentes sur la map...");
        Bukkit.getWorlds().forEach(world -> world.getEntities().clear());
    }

}
