package net.mrlizzard.standblock.remake.hub.objects;

import net.mrlizzard.standblock.remake.hub.StandBlockHUB;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * StandBlockPlayer class.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public class StandBlockPlayer {

    private static Map<UUID, StandBlockPlayer> players = new HashMap<>();

    private Player          player;
    private UUID            uuid;

    private int             experience;

    public StandBlockPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;

        try {
            this.experience = StandBlockHUB.get().getConnector().query("SELECT 'experience' WHERE 'uuid' = '" + this.uuid.toString() + "'").getInt("experience");
        } catch(Exception error) {
            StandBlockHUB.get().consoleErrorLog("§cErreur lors de la mise en cache des données de " + player.getName(), error);
        }

        // On ajoute le joueur à la base de donnée
        addPlayer(player, this);
    }

    public int getExperience() {
        return experience;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static Map<UUID, StandBlockPlayer> getPlayers() {
        return players;
    }

    public static void removePlayer(Player player) {
        if(players.containsKey(player.getUniqueId()))
            players.remove(player.getUniqueId());
    }

    public static void addPlayer(Player player, StandBlockPlayer standPlayer) {
        if(!players.containsKey(player.getUniqueId()))
            players.put(player.getUniqueId(), standPlayer);
    }

}
