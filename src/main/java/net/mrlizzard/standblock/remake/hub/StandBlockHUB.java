package net.mrlizzard.standblock.remake.hub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author MrLizzard
 * @version 0.1-SNAPSHOT
 * @licence GNU General Public Licence v3.0
 */
public class StandBlockHUB extends JavaPlugin {

    @Override
    public void onEnable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        File mapConfig = new File(getDataFolder(), "map.yml");

        if(!mapConfig.exists()) {
            consoleLog("§7Fichier de configuration de map inexistant. Création en cours...");
            mapConfig.mkdir();
            // TODO: Copie du contenu se trouvant dans resources/map.yml dans les sources du plugin (try/catch)
            consoleLog("§7Fichier de configuration de map créé avec succès !");
        }

        consoleLog("§fPlugin créé à l'occasion d'un évènement public pour StandBlock.");
        consoleLog("§fPlus d'informations à cette adresse: §bhttps://github.com/StandBlock-REMAKE/hub");
    }

    public void consoleLog(String message) {
        getServer().getConsoleSender().sendMessage("§f[§2" + getDescription().getName() + "§f] §r" + message);
    }

    @Override
    public void onDisable() {
        consoleLog("§7Kick des joueurs encore présents (sécurité supplémentaire)");
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cFermeture du serveur."));

        consoleLog("§7Nettoyage des entitées présentes sur la map...");
        Bukkit.getWorlds().forEach(world -> world.getEntities().clear());
    }

}
