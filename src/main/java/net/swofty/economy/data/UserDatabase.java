package net.swofty.economy.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.*;

public class UserDatabase {
    public static final Map<UUID, Map<String, Object>> GLOBAL_USER_CACHE = new HashMap<>();
    public static final Map<String, Document> Document_Cache = new HashMap<>();

    public final String id;

    public static MongoClient client;
    public static MongoDatabase database;
    public static MongoCollection<Document> collection;

    public UserDatabase(String id) {
        this.id = id;
    }

    public static void connect(String URI) {
        ConnectionString cs = new ConnectionString(URI);
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(cs).build();
        client = MongoClients.create(settings);

        database = client.getDatabase("Economy");
        collection = database.getCollection("data");
    }

    public void set(String key, Object value) {
        insertOrUpdate(key, value);
    }

    public Object get(String key, Object def) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return def;
        }
        return doc.get(key);
    }

    public List<Document> getAll() {
        FindIterable<Document> results = collection.find();
        List<Document> list = new ArrayList<>();
        for (Document doc : results) {
            list.add(doc);
        }
        return list;
    }

    public String getString(String key, String def) {
        return get(key, def).toString();
    }

    public int getInt(String key, int def) {
        return Integer.parseInt(get(key, def).toString());
    }

    public long getLong(String key, long def) {
        return Long.parseLong(getString(key, def + ""));
    }

    public boolean getBoolean(String key, boolean def) {
        return Boolean.parseBoolean(get(key, def).toString());
    }

    public <T> List<T> getList(String key, Class<T> t) {
        Document query = new Document("_id", id);
        Document found = collection.find(query).first();

        if (found == null) {
            return new ArrayList<>();
        }

        return found.getList(key, t);
    }

    public Document getDocument() {
        Document query = new Document("_id", id);
        return collection.find(query).first();
    }

    public boolean remove(String id) {
        Document query = new Document("_id", id);
        Document found = collection.find(query).first();

        if (found == null) {
            return false;
        }

        collection.deleteOne(query);
        return true;
    }

    public void insertOrUpdate(String key, Object value) {
        if (exists()) {
            Document query = new Document("_id", id);
            Document found = collection.find(query).first();

            assert found != null;
            collection.updateOne(found, Updates.set(key, value));
            return;
        }

        Document New = new Document("_id", id);
        New.append(key, value);
        collection.insertOne(New);
    }

    public boolean exists() {
        Document query = new Document("_id", id);
        Document found = collection.find(query).first();
        return found != null;
    }

    public static UUID fetchUUID(String username) {
        Document doc = collection.find(Filters.eq("username", username)).first();
        if (doc == null)
            return null;
        return UUID.fromString(doc.getString("_id"));
    }

    public static Document fetchDocument(String uniqueId) {
        return collection.find(Filters.eq("_id", uniqueId)).first();
    }
}
