package de.sprax2013.advanced_dev_utils.spigot;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.sprax2013.advanced_dev_utils.spigot.events.CustomEventManager;
import de.sprax2013.advanced_dev_utils.spigot.fake_player.FakePlayerManager;
import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.JavaPluginUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.plugin_messaging.PluginMessagingChannelUtils;

public class Main extends JavaPlugin {
	private static Main plugin;

	private final List<String> optionalDepends = Arrays.asList();

	public static final String prefix = "§eAdvancedDevUtils-Spigot §8» §r";

	FileWriter fW;

	@Override
	public void onEnable() {
		plugin = this;

		Bukkit.getConsoleSender()
				.sendMessage(Main.prefix + "§2Erkannte Server-Version§7: §e" + BukkitServerUtils.getBukkitVersion());

		if (!JavaPluginUtils.checkDependencies(this, prefix, optionalDepends)) {
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			de.sprax2013.advanced_dev_utils.Main.init();

			CustomEventManager.init();
			PluginMessagingChannelUtils.init();

			// Create Files
//			getYAMLConfig();

			// Init plugin
//			ClientSettingsAPI.init();

			registerListeners();

			// DEBUG
			FakePlayerManager.debug();
		}
	}

	@Override
	public void onDisable() {
		de.sprax2013.advanced_dev_utils.Main.deInit();
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new CustomEventManager(), this);
	}

	public static Main getInstance() {
		return plugin;
	}

//	public static FileConfiguration getYAMLConfig() {
//		YAMLFile file = YAMLFileManager.getFile(getInstance(), "config.yml");
//
//		if (file.getCountOfDefaultValues() == 0) {
//			file.addDefault("PlayerInfoAPI.ForceMojangAPI", false).save();
//		}
//
//		return file.getCfg();
//	}
}