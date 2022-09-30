package net.swofty.economy.listener.listeners;

import net.swofty.economy.data.MongoLoader;
import net.swofty.economy.listener.PListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class PlayerQuitEvent extends PListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent e) {
        new MongoLoader().save(e.getPlayer().getUniqueId());
    }

}
