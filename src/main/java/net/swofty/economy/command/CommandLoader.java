package net.swofty.economy.command;

import java.util.ArrayList;
import java.util.List;

public class CommandLoader {
    public static List<EconomyCommand> commands;

    public CommandLoader() {
        this.commands = new ArrayList<>();
    }

    public void register(EconomyCommand command) {
        commands.add(command);
    }

    public int getCommandAmount() {
        return commands.size();
    }
}