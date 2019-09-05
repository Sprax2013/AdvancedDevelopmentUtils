package de.sprax2013.advanced_dev_utils.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class JavaPluginUtils {
	/**
	 * @return Value of
	 *         <code>JavaPluginUtils.checkDependencies(<b>plugin</b>, null)</code>
	 * 
	 * @see #checkDependencies(JavaPlugin, String)
	 */
	public static boolean checkDependencies(JavaPlugin plugin) {
		return checkDependencies(plugin, null);
	}

	/**
	 * @return Value of
	 *         <code>JavaPluginUtils.checkDependencies(<b>plugin</b>, <b>prefix</b>, new ArrayList<>())</code>
	 * 
	 * @see #checkDependencies(JavaPlugin, String, List)
	 */
	public static boolean checkDependencies(JavaPlugin plugin, String prefix) {
		return checkDependencies(plugin, prefix, new ArrayList<>());
	}

	/**
	 * Checks the dependencies of a plugin.<br>
	 * <br>
	 * <b>Tutorial / Usage:</b> In your plugin.yml put <i>Plugin1</i> and
	 * <i>Plugin2</i> to <i>softdepend</i>. For your plugin Plugin1 is optional but
	 * it works without it. Plugin2 is required. Using this method you can put
	 * <i>Plugin1</i> into <b>optionalDepends</b> and this method will print a clean
	 * and user-friendly message if <i>Plugin2</i> is missing.<br>
	 * <b>Info: Does not work with <i>depend</i> only with <i>softdepend</i></b>
	 *
	 * @param plugin          The plugin to check
	 * @param prefix          Prefix to use in console messages - May be <i>null</i>
	 * @param optionalDepends a list of Plugin-Names that are optional but not
	 *                        required
	 * @return True, if all required plugins are enabled - False, if at least one
	 *         required plugin is not enabled
	 */
	public static boolean checkDependencies(JavaPlugin plugin, String prefix, List<String> optionalDepends) {
		if (!plugin.getDescription().getSoftDepend().isEmpty()) {

			if (prefix == null) {
				prefix = "[" + plugin.getName() + "] ";
			} else if (!prefix.endsWith(" ")) {
				prefix += " ";
			}

			List<String> needed = new ArrayList<>();

			if (!plugin.getDescription().getSoftDepend().isEmpty()) {
				Bukkit.getConsoleSender().sendMessage(prefix + "§ePrüfe auf Abhängigkeiten...");

				PluginManager pm = Bukkit.getPluginManager();
				for (String pl : plugin.getDescription().getSoftDepend()) {
					if (optionalDepends.contains(pl)) {
						if (pm.isPluginEnabled(pl)) {
							Bukkit.getConsoleSender().sendMessage("§7[§a✓§7] (§6Optional§7)§8: §r" + pl);
						} else {
							Bukkit.getConsoleSender().sendMessage("§7[§4✗§7] (§6Optional§7)§8: §r" + pl);
						}
					} else {
						if (pm.isPluginEnabled(pl)) {
							Bukkit.getConsoleSender().sendMessage("§7[§a✓§7] (§cBenötigt§7)§8: §r" + pl);
						} else {
							Bukkit.getConsoleSender().sendMessage("§7[§4✗§7] (§cBenötigt§7)§8: §r" + pl);

							needed.add(pl);
						}
					}
				}

				if (needed.isEmpty()) {
					Bukkit.getConsoleSender()
							.sendMessage(prefix + "§2Alle Abhängigkeiten sind erfüllt §a٩̋(ˊ•͈ ꇴ •͈ˋ)و");
				} else {
					Bukkit.getConsoleSender().sendMessage(prefix
							+ "§cEs sind nicht alle Abhängigkeiten erfüllt! §4~(>_<~) §cBitte prüfen und den Server neustarten.");
				}
			}

			return needed.isEmpty();
		}

		return true;
	}

	/**
	 * Checks if a plugin is enabled.
	 *
	 * @param plugin The plugin's name (<b>Case-sensitive</b>)
	 * 
	 * @return true, if is plugin enabled
	 */
	public static boolean isPluginEnabled(String plugin) {
		Plugin pl = Bukkit.getPluginManager().getPlugin(plugin);

		return pl != null && pl.isEnabled();
	}
}