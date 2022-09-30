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

@CommandParameters(permission = "economy.setbal")
public class subCommand_setbal extends EconomyCommand {

    @Override
    public void run(CommandSource sender, String[] args, SwoftyEconomy plugin) {
        if (args.length < 3) {
            send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.usage-command")), Arrays.asList(Map.entry("$USAGE", "/economy setbal <name> <bal>"))));
            return;
        }

        String name = args[1];

        if (Bukkit.getPlayer(name) == null) {
            send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.player-not-found")), Arrays.asList(Map.entry("$NAME", name))));
            return;
        }

        try {
            double balance = Double.parseDouble(args[2]);

            if (balance < 0) {
                throw new NumberFormatException();
            }

            User user = User.getUser(Bukkit.getPlayer(name));
            user.setBalance(balance);

            send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.set-balance")),
                    Arrays.asList(Map.entry("$NAME", name), Map.entry("$BALANCE", String.valueOf(user.getBalance())))));

            User.updateUserCache(user);
        } catch (NumberFormatException ex) {
            send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.invalid-number-input")), Arrays.asList(Map.entry("$INPUT", args[2]))));
        }
    }

    @Override
    public List<String> tabCompleters(CommandSender sender, String alias, String[] args) {
        if (args.length < 3)
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        return null;
    }
}
