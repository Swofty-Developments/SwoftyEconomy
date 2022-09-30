package net.swofty.economy.utilities;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class User {

    public static HashMap<UUID, User> USER_CACHE = new HashMap<UUID, User>();

    public UUID uuid;
    public Double balance;
    public String username;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.balance = 0D;
        this.username = "";

        // TO-DO; remove function code from constructor
        USER_CACHE.put(uuid, this);
    }

    public static User getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    public static User getUser(UUID uuid) {
        if (Bukkit.getPlayer(uuid) == null) return null;
        if (USER_CACHE.containsKey(uuid)) {
            return USER_CACHE.get(uuid);
        }
        return new User(uuid);
    }

    public static void updateUserCache(User user) {
        USER_CACHE.put(user.getUuid(), user);
    }

}
