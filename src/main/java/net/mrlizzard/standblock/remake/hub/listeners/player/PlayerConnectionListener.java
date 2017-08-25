package net.mrlizzard.standblock.remake.hub.listeners.player;

import net.mrlizzard.standblock.remake.hub.objects.StandBlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * PlayerConnectionListener class.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public class PlayerConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        StandBlockPlayer standPlayer = new StandBlockPlayer(player);

        if(standPlayer.hasPermission("join.message"))
            event.setJoinMessage(standPlayer.getRank().getColor() + " [" + standPlayer.getRank().getName() + "] " + player.getName() + " §6a rejoint ce hub !");
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerQuitServer(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        StandBlockPlayer.removePlayer(player);

        event.setQuitMessage("");
    }

}
