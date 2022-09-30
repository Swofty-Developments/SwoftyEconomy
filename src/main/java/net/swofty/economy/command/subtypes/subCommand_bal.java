package net.swofty.economy.command.subtypes;

import net.swofty.economy.SwoftyEconomy;
import net.swofty.economy.command.CommandParameters;
import net.swofty.economy.command.CommandSource;
import net.swofty.economy.command.EconomyCommand;
import net.swofty.economy.utilities.SUtil;
import net.swofty.economy.utilities.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CommandParameters(permission = "economy.bal")
public class subCommand_bal extends EconomyCommand {

    @Override
    public void run(CommandSource sender, String[] args, SwoftyEconomy plugin) {
        if (args.length == 1) {
            send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.balance")),
                    Arrays.asList(Map.entry("$NAME", sender.getPlayer().getName()), Map.entry("$BALANCE", String.valueOf(sender.getUser().getBalance())))));
            return;
        }

        String name = args[1];

        if (Bukkit.getPlayer(name) == null) {
            send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.player-not-found")), Arrays.asList(Map.entry("$NAME", name))));
            return;
        }

        send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.balance")),
                Arrays.asList(Map.entry("$NAME", name), Map.entry("$BALANCE", String.valueOf(User.getUser(Bukkit.getPlayer(name)).getBalance())))));
    }

    @Override
    public List<String> tabCompleters(CommandSender sender, String alias, String[] args) {
        if (args.length < 3)
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        return null;
    }
}
