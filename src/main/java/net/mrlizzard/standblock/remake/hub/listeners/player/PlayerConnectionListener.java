package net.mrlizzard.standblock.remake.hub.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * PlayerConnectionListener class.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public class PlayerConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void onPlayerJoinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }

}
