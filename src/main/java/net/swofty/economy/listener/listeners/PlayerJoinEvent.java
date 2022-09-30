package net.swofty.economy.listener.listeners;

import net.swofty.economy.data.MongoLoader;
import net.swofty.economy.listener.PListener;
import net.swofty.economy.utilities.SUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinEvent extends PListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent e) {
        SUtil.delay(() -> new MongoLoader().load(e.getPlayer().getUniqueId()), 2, getPlugin());
    }

}
