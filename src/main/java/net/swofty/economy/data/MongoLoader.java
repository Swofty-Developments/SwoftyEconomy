package net.swofty.economy.data;

import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import net.swofty.economy.utilities.SUtil;
import net.swofty.economy.utilities.User;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.*;

public class MongoLoader {

    public Map<String, Object> userCache;

    public MongoLoader() {
        userCache = new HashMap<>();
    }

    public void load(UUID uuid) {
        User user = User.getUser(uuid);
        Document base = UserDatabase.collection.find(Filters.eq("_id", uuid.toString())).first();

        if (base == null) return;

        user.username = base.getString("username");
        user.balance = base.getDouble("balance");

        User.updateUserCache(user);
    }

    @SneakyThrows
    public void save(UUID uuid) {
        User user = User.getUser(uuid);
        User.USER_CACHE.remove(uuid);
        UserDatabase db = new UserDatabase(uuid.toString());

        updateCache("username", Bukkit.getPlayer(uuid).getName());
        updateCache("balance", user.getBalance());

        SUtil.runAsync(() -> saveCache(db));
    }

    public void updateCache(String key, Object value) {
        userCache.put(key, value);
    }

    public void saveCache(UserDatabase db) {
        if (db.exists()) {
            Document query = new Document("_id", db.id);
            Document found = UserDatabase.collection.find(query).first();

            Document updated = new Document();
            userCache.forEach(updated::append);

            assert found != null;
            UserDatabase.collection.replaceOne(found, updated);
            return;
        }
        Document New = new Document("_id", db.id);
        userCache.forEach(New::append);

        UserDatabase.collection.insertOne(New);
    }

}
