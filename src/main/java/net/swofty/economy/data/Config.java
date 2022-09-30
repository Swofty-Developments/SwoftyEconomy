package net.swofty.economy.data;

import net.swofty.economy.SwoftyEconomy;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config extends YamlConfiguration {
    private final File file;

    public Config(File parent, String name, SwoftyEconomy plugin) {
        this.file = new File(parent, name);

        if (!file.exists()) {
            options().copyDefaults(true);
            plugin.saveResource(name, false);
        }
        load();
    }

    public Config(String name, SwoftyEconomy plugin) {
        this(plugin.getDataFolder(), name, plugin);
    }

    public void load() {
        try {
            super.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            super.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}