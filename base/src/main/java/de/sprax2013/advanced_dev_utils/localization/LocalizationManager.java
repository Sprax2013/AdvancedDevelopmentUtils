package de.sprax2013.advanced_dev_utils.localization;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class LocalizationManager {
    /*
     * <b>Key</b>: Unique identifier (Case-Insensitive) <br> <b>Value</b>:
     * Localization-Object
     */
    private static HashMap<String, Localization> localizations = new HashMap<>();

    /**
     * Returns a cached Localization-Object.
     *
     * @param identifier The identifier (Case-Insensitive)
     *
     * @return The Localization-Object or null
     *
     * @see #getLocalization(String, File, Locale, HashMap)
     */
    public static Localization getLocalization(String identifier) {
        return localizations.get(identifier.toLowerCase());
    }

    /**
     * Creates a new Localization-Object with the given default values. A cached
     * Localization will be removed from the HashMap.<br>
     * It is good practice to use {@link #getLocalization(String)} instead of
     * applying it to a variable (<b>outside of methods</b>) <br>
     *
     * <b><u>How does it work?</u></b><br>
     * <i>The Localization-Object will parse files inside <i>destFile</i>. The
     * default values are not be automatically removed/modified by default.</i>
     *
     * @param identifier    A unique identifier (Case-Insensitive). It will be used in destDir
     *                      (destDir/identifier/lang-Code.lang) and in
     *                      {@link #getLocalization(String)}
     * @param destDir       The directory should be empty, non existing or created/managed by
     *                      this method. Sub-Directories with Localization-Files (for each
     *                      language) be created.
     *                      (<i>destDir</i>/<i>identifier</i>/lang.properties)
     * @param defaultLocale The default locale/language that should be used
     * @param defaultValues The Key is the locale/language, the strings are for. Value is
     *                      HashMap<String, String>. Inside that the key is a Case-Sensitive
     *                      identifier. The value is the translated string.
     *
     * @return the localization
     *
     * @throws IllegalStateException When defaultValues is <b>not</b> empty and does not contain
     *                               <i>defaultLocale</i>
     * @see #getLocalization(String)
     */
    public static Localization getLocalization(String identifier, File destDir, Locale defaultLocale,
                                               HashMap<Locale, HashMap<String, String>> defaultValues) {
        if (!defaultValues.isEmpty() && !defaultValues.containsKey(defaultLocale)) {
            throw new IllegalStateException("'defaultValues' does not contain any values for 'defaultLocale'"
                    + (defaultLocale == null ? "" : " (" + defaultLocale.getLanguage() + ")"));
        }

        Localization result = new Localization(identifier, destDir, defaultLocale, defaultValues);

        localizations.put(identifier.toLowerCase(), result);

        return result;
    }
}