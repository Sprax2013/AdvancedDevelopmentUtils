package de.sprax2013.advanced_dev_utils.spigot.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerFixUtils {
    /**
     * <b><u>This method could be removed in the near future!</u></b><br>
     * <br>
     * Fixes player invisibility.<br>
     * Ein Bug, der <b>NUR</b> in der kack 1.8 auftritt.<br>
     * Pls update to the latest version - Warum eine 4 Jahre alte MC Version benutzen D:
     */
    @SuppressWarnings("deprecation")
    public static void fixPlayerInvisibility(JavaPlugin plugin, Player p) {
//		try {
//			for (Player online : Bukkit.getOnlinePlayers()) {
//				if (online.canSee(p)) {
//					online.hidePlayer(plugin, p);
//					online.showPlayer(plugin, p);
//				} else {
//					online.showPlayer(plugin, p);
//					online.hidePlayer(plugin, p);
//				}
//
//				if (p.canSee(online)) {
//					p.hidePlayer(plugin, online);
//					p.showPlayer(plugin, online);
//				} else {
//					p.showPlayer(plugin, online);
//					p.hidePlayer(plugin, online);
//				}
//			}
//		} catch (@SuppressWarnings("unused") NoSuchMethodError ex) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.canSee(p)) {
                online.hidePlayer(p);
                online.showPlayer(p);
            } else {
                online.showPlayer(p);
                online.hidePlayer(p);
            }

            if (p.canSee(online)) {
                p.hidePlayer(online);
                p.showPlayer(online);
            } else {
                p.showPlayer(online);
                p.hidePlayer(online);
            }
        }
//		}
    }
}