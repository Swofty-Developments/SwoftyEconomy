package net.swofty.economy.command.subtypes;

import net.swofty.economy.SwoftyEconomy;
import net.swofty.economy.command.CommandParameters;
import net.swofty.economy.command.CommandSource;
import net.swofty.economy.command.EconomyCommand;
import net.swofty.economy.utilities.SUtil;
import net.swofty.economy.utilities.User;
import org.bukkit.command.CommandSender;

import java.util.*;

@CommandParameters(permission = "economy.earn")
public class subCommand_earn extends EconomyCommand {

    public static HashMap<UUID, Long> cooldown = new HashMap<>();

    @Override
    public void run(CommandSource sender, String[] args, SwoftyEconomy plugin) {
        if (cooldown.containsKey(sender.getPlayer().getUniqueId())) {
            if (System.currentTimeMillis() - cooldown.get(sender.getPlayer().getUniqueId()) < 60000) {
                send("§cYou are currently on cooldown and have " + (60 - ((System.currentTimeMillis() - cooldown.get(sender.getPlayer().getUniqueId())) / 1000)) + "s left");
                return;
            }
        }
        cooldown.put(sender.getPlayer().getUniqueId(), System.currentTimeMillis());

        int amount = new Random().nextInt(5) + 1;

        sender.getUser().setBalance(sender.getUser().getBalance() + amount);
        User.updateUserCache(sender.getUser());

        send(SUtil.variableize(SUtil.translateColorWords(plugin.messages.getString("messages.command.earn")),
                Arrays.asList(Map.entry("$AMOUNT", String.valueOf(amount)))));
    }

    @Override
    public List<String> tabCompleters(CommandSender sender, String alias, String[] args) {
        return null;
    }
}
