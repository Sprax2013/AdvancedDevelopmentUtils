package de.sprax2013.advanced_dev_utils.localization;

import de.sprax2013.advanced_dev_utils.ProjectInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

//TODO create JavaDocs
public class Localization {
    private String identifier;

    private File destDir;

    private String defaultLang;

    private HashMap<String, HashMap<String, String>> values = new HashMap<>();
    private HashMap<String, HashMap<String, String>> defaultValues = new HashMap<>();

    /**
     * Instantiates a new Localization-Object.<br>
     * You should use
     * {@link LocalizationManager#getLocalization(String, File, Locale, HashMap)}.
     *
     * @param identifier    A unique identifier (Case-Insensitive). It will be used
     *                      in destDir (destDir/identifier/lang-Code.lang) and in
     *                      {@link #getLocalization(String)}
     * @param destDir       The directory should be empty, non existing or
     *                      created/managed by this method. Sub-Directories with
     *                      Localization-Files (for each language) be created.
     *                      (<i>destDir</i>/<i>identifier</i>/lang.properties)
     * @param defaultLocale The default locale/language that should be used
     * @param defaultValues The Key is the locale/language, the strings are for.
     *                      Value is HashMap<String, String>. Inside that the key is
     *                      a Case-Sensitive identifier. The value is the translated
     *                      string.
     *
     * @see LocalizationManager#getLocalization(String)
     * @see LocalizationManager#getLocalization(String, File, Locale, HashMap)
     */
    public Localization(String identifier, File destDir, Locale defaultLocale,
                        HashMap<Locale, HashMap<String, String>> defaultValues) {
        this.identifier = identifier;

        this.destDir = destDir;

        this.defaultLang = defaultLocale.getLanguage();

        for (Locale loc : defaultValues.keySet()) {
            this.defaultValues.put(loc.getLanguage(), defaultValues.get(loc));
        }

        refreshFromFile();
        saveToFile();
    }

    public String getString(String key) {
        if (values.containsKey(defaultLang) && values.get(defaultLang).containsKey(key)) {
            return values.get(defaultLang).get(key);
        }

        return defaultValues.get(defaultLang).get(key);
    }

    public String getString(String key, Locale lang) {
        if (lang == null) {
            return getString(key);
        }

        if (values.containsKey(lang.getLanguage()) && values.get(lang.getLanguage()).containsKey(key)) {
            return values.get(lang.getLanguage()).get(key);
        }

        if (defaultValues.containsKey(lang.getLanguage()) && defaultValues.get(lang.getLanguage()).containsKey(key)) {
            return defaultValues.get(lang.getLanguage()).get(key);
        }

        return null;
    }

    public String getStringOrDefault(String key, Locale lang) {
        if (lang != null && values.containsKey(lang.getLanguage()) && values.get(lang.getLanguage()).containsKey(key)) {
//			System.out.println(0);

            return values.get(lang.getLanguage()).get(key);
        }

//		for (Locale loc : defaultValues.keySet()) {
//			System.out.println("keySet: " + loc.toString());
//		}
//
//		if (lang == null) {
//			System.out.println("lang: NULL");
//		} else {
//			System.out.println("lang: " + lang.toString());
//		}
//
//		System.out.println("defaultValues.containsKey(lang): " + defaultValues.containsKey(lang));
//		System.out.println("defaultValues.get(lang).containsKey(key): " + defaultValues.get(lang).containsKey(key));

        if (lang != null && defaultValues.containsKey(lang.getLanguage())
                && defaultValues.get(lang.getLanguage()).containsKey(key)) {
//			System.out.println(1);

            return defaultValues.get(lang.getLanguage()).get(key);
        }

//		System.out.println(2);
        return getString(key);
    }

    public String getDefaultString(String key) {
        return defaultValues.get(defaultLang).get(key);
    }

    public String getDefaultString(String key, Locale lang) {
        if (lang == null) {
            return getDefaultString(key);
        }

        if (defaultValues.containsKey(lang.getLanguage()) && defaultValues.get(lang.getLanguage()).containsKey(key)) {
            return defaultValues.get(lang.getLanguage()).get(key);
        }

        return null;
    }

    public void setCustomValue(Locale lang, String key, String value) {
        HashMap<String, String> valueMap = values.getOrDefault(lang, new HashMap<>());

        valueMap.put(key, value);

        values.put(lang.getLanguage(), valueMap);
    }

    public void setDefaultValue(Locale lang, String key, String value) {
        HashMap<String, String> valueMap = defaultValues.getOrDefault(lang, new HashMap<>());

        valueMap.put(key, value);

        defaultValues.put(lang.getLanguage(), valueMap);
    }

    /**
     * Will look for existing files and load it's content.<br>
     * You can call this method in case changes to the .lang-files were made.
     */
    public void refreshFromFile() {
        values.clear();

        File dest = new File(destDir, identifier);

        if (dest.exists()) {
            for (File f : dest.listFiles()) {
                try {
                    Locale lang = Locale.forLanguageTag(getBaseName(f.getName()));

                    Properties prop = new Properties();

                    prop.load(new FileInputStream(f));

                    HashMap<String, String> stringMap = new HashMap<>();

                    boolean locInDefaultMap = defaultValues.containsKey(lang.getLanguage());
                    for (Object key : prop.keySet()) {
                        String value = prop.getProperty(key.toString());
                        if (!locInDefaultMap || !defaultValues.get(lang.getLanguage()).get(key).equals(value)) {
                            stringMap.put(key.toString(), value);
                        }
                    }

                    if (!stringMap.isEmpty()) {
                        values.put(lang.getLanguage(), stringMap);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Saves everything into files. Each language has its own file.
     */
    public void saveToFile() {
        List<String> langs = new ArrayList<>();
        langs.addAll(values.keySet());
        langs.addAll(defaultValues.keySet());

        for (String lang : langs) {
            File dest = new File(destDir, identifier + File.separator + lang + ".lang");

            dest.getParentFile().mkdirs();

            Properties prop = new Properties();

            if (defaultValues.containsKey(lang)) {
                for (String key : defaultValues.get(lang).keySet()) {
                    prop.setProperty(key, defaultValues.get(lang).get(key));
                }
            }

            if (values.containsKey(lang)) {
                for (String key : values.get(lang).keySet()) {
                    prop.setProperty(key, values.get(lang).get(key));
                }
            }

            try {
                prop.store(new FileOutputStream(dest),
                        "This File has been created by " + ProjectInfo.getName() + " v" + ProjectInfo.getVersion());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Methods from FilenameUtils (Apache IO)
    private String getBaseName(final String filename) {
        return removeExtension(getName(filename));
    }

    private String getName(final String filename) {
        return filename.substring(indexOfLastSeparator(filename) + 1);
    }

    private int indexOfLastSeparator(final String filename) {
        return Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
    }

    private String removeExtension(final String filename) {
        final int index = indexOfExtension(filename);

        if (index == -1) {
            return filename;
        }

        return filename.substring(0, index);
    }

    private int indexOfExtension(final String filename) {
        final int extensionPos = filename.lastIndexOf('.');
        return indexOfLastSeparator(filename) > extensionPos ? -1 : extensionPos;
    }
}