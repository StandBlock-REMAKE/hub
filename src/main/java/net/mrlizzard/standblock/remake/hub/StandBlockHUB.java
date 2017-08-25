package net.mrlizzard.standblock.remake.hub;

import net.mrlizzard.standblock.remake.hub.config.EventSQLConnector;
import net.mrlizzard.standblock.remake.hub.config.SQLConnector;
import net.mrlizzard.standblock.remake.hub.listeners.player.PlayerConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
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

        if(!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        File configFile = new File(this.getDataFolder(), "config.yml");

        if(!configFile.exists()) {
            this.consoleLog("§fFichier de configuration inexistant. Création en cours...");
            configFile.mkdir();
            // TODO: Copie du contenu se trouvant dans resources/config.yml dans les sources du plugin (try/catch)
            this.consoleLog("§7Fichier de configuration créé avec succès !");
        }

        this.debugMode = this.getConfig().getBoolean("core.debug-mode", false);

        this.consoleLog("§fDémarrage de " + this.getDescription().getName() + " en version §c" + this.getDescription().getVersion() + "§7.");
        this.consoleLog("§fLe plugin est actuellement en mode " + (debugMode ? "§6DÉVELOPPEMENT" : "§aPRODUCTION") + "§f.");
        this.consoleLog("§fPlugin créé à l'occasion d'un évènement public pour StandBlock.");
        this.consoleLog("§fPlus d'informations à cette adresse: §bhttps://github.com/StandBlock-REMAKE/hub");
        this.consoleLog("§f" + this.getDescription().getAuthors().size() + " développeur" + (this.getDescription().getAuthors().size() > 1 ? "s" : "") + " ont participé" + (this.getDescription().getAuthors().size() > 1 ? "s" : "") + " au développement de ce plugin.");

        this.connector = new EventSQLConnector(this);

        // Listeners
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerConnectionListener(), this);
    }

    public void consoleLog(String message) {
        this.getServer().getConsoleSender().sendMessage("§f[§2" + getDescription().getName() + "§f] §r§f" + message);
    }

    public void consoleDebugLog(String message) {
        this.consoleLog(ChatColor.LIGHT_PURPLE + "-> [DEBUG] §f" + message);
    }

    public void consoleErrorLog(String message, Exception error) {
        this.consoleLog(message);

        if(debugMode)
            this.consoleDebugLog(error.getMessage());
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
        this.connector.onDisable();

        this.consoleLog("Kick des joueurs encore présents (sécurité supplémentaire)");
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cFermeture du serveur."));

        this.consoleLog("Nettoyage des entitées présentes sur la map...");
        Bukkit.getWorlds().forEach(world -> world.getEntities().clear());
    }

}
