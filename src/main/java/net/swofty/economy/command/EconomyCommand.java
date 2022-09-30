package net.swofty.economy.command;

import net.swofty.economy.SwoftyEconomy;
import net.swofty.economy.utilities.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class EconomyCommand implements CommandExecutor, TabCompleter {
    private static final Map<UUID, HashMap<String, Long>> CMD_COOLDOWN = new HashMap<>();
    public static final String COMMAND_SUFFIX = "subCommand_";

    private final CommandParameters params;
    private final String name;
    private final List<String> aliases;
    private final String permission;

    private CommandSource sender;

    protected EconomyCommand() {
        this.params = this.getClass().getAnnotation(CommandParameters.class);
        this.name = this.getClass().getSimpleName().replace(COMMAND_SUFFIX, "").toLowerCase();
        this.aliases = Arrays.asList(this.params.aliases().split(","));
        this.permission = this.params.permission();
    }

    public abstract void run(CommandSource sender, String[] args, SwoftyEconomy plugin);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public static void register(SwoftyEconomy plugin) {
        plugin.commandMap.register("", new EconomyCommandHandler(plugin));
    }

    public static class EconomyCommandHandler extends Command {

        public EconomyCommand economyCommand;
        public SwoftyEconomy plugin;

        public EconomyCommandHandler(SwoftyEconomy plugin) {
            super("economy", "Access and manage everything to do with SwoftyEconomy", "", new ArrayList<>());
            this.plugin = plugin;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            if (!(sender instanceof Player)) {
                System.out.println("Console senders cannot use commands");
                return false;
            }

            if (args.length == 0) {
                plugin.messages.getStringList("messages.command.usage-overall").forEach(s -> {
                    sender.sendMessage(SUtil.translateColorWords(s));
                });
                return false;
            }

            for (EconomyCommand economyCommand1 : CommandLoader.commands) {
                if (economyCommand1.name.equals(args[0]) || economyCommand1.aliases.contains(args[0])) {
                    this.economyCommand = economyCommand1;
                }
            }

            if (this.economyCommand == null) {
                plugin.messages.getStringList("messages.command.usage-overall").forEach(s -> {
                    sender.sendMessage(SUtil.translateColorWords(s));
                });
                return false;
            }

            economyCommand.sender = new CommandSource(sender);

            if (!economyCommand.permission.equals("") && !sender.hasPermission(economyCommand.permission)) {
                sender.sendMessage(SUtil.translateColorWords(plugin.messages.getString("messages.command.no-permission")));
                return false;
            }

            economyCommand.run(economyCommand.sender, args, plugin);
            return false;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
            if (args.length <= 1) {
                List<String> list = new ArrayList<>();
                CommandLoader.commands.stream().forEach(entry -> list.add(entry.name));
                return list;
            } else {
                for (EconomyCommand economyCommand1 : CommandLoader.commands) {
                    if (economyCommand1.name.equals(args[0]) || economyCommand1.aliases.contains(args[0])) {
                        this.economyCommand = economyCommand1;
                        return economyCommand.tabCompleters(sender, alias, args);
                    }
                }

                this.economyCommand = null;
                return new ArrayList<>();
            }
        }
    }

    public abstract List<String> tabCompleters(CommandSender sender, String alias, String[] args);

    public void send(String message, CommandSource sender) {
        sender.send(ChatColor.GRAY + message.replace("&", "§"));
    }

    public void send(String message) {
        send(SUtil.translateColorWords(message), sender);
    }

    public void send(List<String> message) {
        SUtil.translateColorWords(message).forEach(message2 -> {
            sender.send(message2);
        });
    }
}