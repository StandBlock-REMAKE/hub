package net.mrlizzard.standblock.remake.hub.objects;

import net.mrlizzard.standblock.remake.hub.StandBlockHUB;
import net.mrlizzard.standblock.remake.hub.utils.RankUtils;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private RankUtils       rank;

    private int             experience;

    public StandBlockPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;

        try {
            ResultSet resultSet = StandBlockHUB.get().getConnector().query("SELECT * FROM players WHERE uuid = '" + this.getUuid() + "'");

            while(resultSet.next()) {
                this.experience = resultSet.getInt("experience");
                this.rank = RankUtils.getRankById(resultSet.getInt("rank"));
            }
        } catch(Exception ignored) {
            StandBlockHUB.get().consoleLog("§cErreur lors de la mise en cache des données de " + player.getName());
        }

        // On ajoute le joueur à la base de donnée
        addPlayer(player, this);
    }

    public boolean hasPermission(String perm) {
        return this.rank.getPermissions().contains(perm);
    }

    public int getExperience() {
        return experience;
    }

    public Player getPlayer() {
        return player;
    }

    public RankUtils getRank() {
        return rank;
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
