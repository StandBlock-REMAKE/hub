package net.mrlizzard.standblock.remake.hub.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RankUtils enum.
 * @author MrLizzard
 * @licence GNU GPL v3.0
 */
public enum RankUtils {

    OWNER       (100, "Admin", ChatColor.RED, Arrays.asList("join.message", "hub.*")),
    PLAYER      (0, "Joueur", ChatColor.GRAY, new ArrayList<>());

    public int                  identifier;
    public String               name;
    public ChatColor            color;
    public List<String>         permissions;

    RankUtils(int identifier, String name, ChatColor color, List<String> permissions) {
        this.identifier = identifier;
        this.name = name;
        this.color = color;
        this.permissions = permissions;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public static RankUtils getRankById(int id) {
        for (RankUtils rank : RankUtils.values()) {
            if(rank.getIdentifier() == id)
                return rank;
        }

        return RankUtils.PLAYER;
    }

}
