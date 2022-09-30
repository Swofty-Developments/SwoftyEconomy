package net.swofty.economy;

import lombok.Getter;
import net.swofty.economy.command.CommandLoader;
import net.swofty.economy.command.EconomyCommand;
import net.swofty.economy.data.Config;
import net.swofty.economy.data.UserDatabase;
import net.swofty.economy.listener.PListener;
import net.swofty.economy.utilities.SUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import javax.annotation.Untainted;
import java.lang.reflect.Field;

public final class SwoftyEconomy extends JavaPlugin {

    public CommandLoader commandLoader;
    public CommandMap commandMap;
    @Getter
    public Config messages;
    @Getter
    public Config config;

    @Override
    public void onEnable() {
        // Handle commands
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        commandLoader = new CommandLoader();
        loadCommands(this);

        // Handle data
        messages = new Config("messages.yml", this);
        config = new Config("config.yml", this);
        UserDatabase.connect(config.getString("mongo-uri"));

        // Handle listeners
        loadListeners();
    }

    @Untainted
    @Override
    public void onDisable() {}

    private void loadCommands(SwoftyEconomy plugin) {
        EconomyCommand.register(plugin);

        Reflections reflection = new Reflections("net.swofty.economy.command.subtypes");
        for(Class<? extends EconomyCommand> l:reflection.getSubTypesOf(EconomyCommand.class)) {
            try {
                EconomyCommand command = l.newInstance();
                commandLoader.register(command);
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadListeners() {
        PListener.setPlugin(this);
        Reflections reflection = new Reflections("net.swofty.economy.listener.listeners");
        for(Class<? extends PListener> l:reflection.getSubTypesOf(PListener.class)) {
            try {
                PListener clazz = l.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }
}
