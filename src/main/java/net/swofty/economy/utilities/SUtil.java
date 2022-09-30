package net.swofty.economy.utilities;

import net.swofty.economy.SwoftyEconomy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;

public class SUtil {

    public static void delay(Runnable runnable, long delay, SwoftyEconomy plugin) {
        new BukkitRunnable() {
            public void run() {
                runnable.run();
            }
        }.runTaskLater(plugin, delay);
    }

    public static void runAsync(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static List<String> variableize(List<String> list, List<Map.Entry<String, String>> entries) {
        entries.forEach(entry -> {
            list.replaceAll(s -> s.replace(entry.getKey(), entry.getValue()));
        });
        return list;
    }

    public static String variableize(String str, List<Map.Entry<String, String>> entries) {
        for (Map.Entry<String, String> entry : entries) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }

    public static void sendMessageList(Player player, List<String> list) {
        SUtil.translateColorWords(list).forEach(player::sendMessage);
    }

    public static List<String> translateColorWords(List<String> s) {
        s.replaceAll(string -> string
                .replace("%%black%%", "§0")
                .replace("%%dark-blue%%", "§1")
                .replace("%%dark-green%%", "§2")
                .replace("%%dark-aqua%%", "§3")
                .replace("%%dark-red%%", "§4")
                .replace("%%purple%%", "§5")
                .replace("%%gold%%", "§6")
                .replace("%%gray%%", "§7")
                .replace("%%dark-gray%%", "§8")
                .replace("%%blue%%", "§9")
                .replace("%%green%%", "§a")
                .replace("%%aqua%%", "§b")
                .replace("%%red%%", "§c")
                .replace("%%pink%%", "§d")
                .replace("%%yellow%%", "§e")
                .replace("%%white%%", "§f")
                .replace("%%clear%%", "§r")
                .replace("%%magic%%", "§k")
                .replace("%%bold%%", "§l")
                .replace("%%strike%%", "§m")
                .replace("%%underlined%%", "§n"));
        return color(s);
    }

    public static String translateColorWords(String s) {
        return color(s
                .replaceAll("%%black%%", "§0")
                .replaceAll("%%dark-blue%%", "§1")
                .replaceAll("%%dark-green%%", "§2")
                .replaceAll("%%dark-aqua%%", "§3")
                .replaceAll("%%dark-red%%", "§4")
                .replaceAll("%%purple%%", "§5")
                .replaceAll("%%gold%%", "§6")
                .replaceAll("%%gray%%", "§7")
                .replaceAll("%%dark-gray%%", "§8")
                .replaceAll("%%blue%%", "§9")
                .replaceAll("%%green%%", "§a")
                .replaceAll("%%aqua%%", "§b")
                .replaceAll("%%red%%", "§c")
                .replaceAll("%%pink%%", "§d")
                .replaceAll("%%yellow%%", "§e")
                .replaceAll("%%white%%", "§f")
                .replaceAll("%%clear%%", "§r")
                .replaceAll("%%magic%%", "§k")
                .replaceAll("%%bold%%", "§l")
                .replaceAll("%%strike%%", "§m")
                .replaceAll("%%underlined%%", "§n"));
    }

    private static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private static List<String> color(List<String> string) {
        string.replaceAll(str -> str.replace("&", "§"));
        return string;
    }
}
