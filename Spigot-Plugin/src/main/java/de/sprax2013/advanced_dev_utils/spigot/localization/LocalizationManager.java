package de.sprax2013.advanced_dev_utils.spigot.localization;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import org.bukkit.plugin.java.JavaPlugin;

import de.sprax2013.advanced_dev_utils.localization.Localization;

/**
 * This class uses
 * <code>de.sprax2013.advanced_dev_utils.localization.LocalizationManager</code>
 */
public class LocalizationManager {
	/**
	 * Returns a cached Localization-Object.
	 *
	 * @see #getLocalization(JavaPlugin, Locale, HashMap)
	 * 
	 * @return The Localization-Object or null
	 */
	public static Localization getLocalization(JavaPlugin plugin) {
		return de.sprax2013.advanced_dev_utils.localization.LocalizationManager.getLocalization(plugin.getName());
	}

	/**
	 * <b>You should not use <code>./plugins/YOUR_PLUGIN/lang/</code>! It is used by
	 * this Methode</b><br>
	 * 
	 * Creates a new Localization-Object with the given default values. A cached
	 * Localization will be removed from the HashMap.<br>
	 * It is good practice to use {@link #getLocalization(String)} instead of
	 * applying it to a variable (<b>outside of methods</b>) <br>
	 * 
	 * <b><u>How does it work?</u></b><br>
	 * <i>The Localization-Object will parse files inside <i>destFile</i>. The
	 * default values are not be automatically removed/modified by default.</i>
	 *
	 * @see #getLocalization(JavaPlugin)
	 *
	 * @param defaultLocale
	 *            The default Locale that should be used
	 * @param defaultValues
	 *            The Key is the Locale, the strings are for. Value is
	 *            HashMap<String, String>. Inside that the key is a Case-Sensitive
	 *            identifier. The value is the translated string.
	 * 
	 * @return the localization
	 * 
	 * @throws IllegalStateException
	 *             When defaultValues is <b>not</b> empty and does not contain
	 *             <i>defaultLocale</i>
	 */
	public static Localization getLocalization(JavaPlugin plugin, Locale defaultLocale,
			HashMap<Locale, HashMap<String, String>> defaultValues) {
		return de.sprax2013.advanced_dev_utils.localization.LocalizationManager.getLocalization(plugin.getName(),
				new File(plugin.getDataFolder(), "lang"), defaultLocale, defaultValues);
	}
}