package net.swofty.economy.command;

import lombok.Getter;
import net.swofty.economy.utilities.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public class CommandSource {
    private final CommandSender sender;
    private final Player player;
    private final User user;

    public CommandSource(CommandSender sender) {
        this.sender = sender;
        this.player = sender instanceof Player ? (Player) sender : null;
        this.user = sender instanceof Player ? User.getUser(((Player) sender).getUniqueId()) : null;
    }

    public void send(String message) {
        sender.sendMessage(message);
    }
}