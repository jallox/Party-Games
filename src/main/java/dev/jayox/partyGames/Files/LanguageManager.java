// package me.requie.langManager;
package dev.jayox.partyGames.Files;

/*
LanguageManager v 0.4.2Br/12q
Language manager allows to manage language in your minecraft plugins, for support MD me at discord: reqvdev1

DO NOT MODIFY THIS CLASS, ONLY REPLACE (getConfigMngr()) WITH THE METHOD THAT YOU HAVE IMPLEMENTED IN THE PLUGIN MAIN
IF YOU KNOW WHAT ARE YOU DOING, AND YOU MODIFY THIS FILE, YOU WON'T GET SUPPORT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
import dev.jayox.partyGames.PartyGames;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class LanguageManager {

    private final PartyGames plugin;
    private YamlConfiguration languageConfig;
    private final Logger logger;
    public LanguageManager(PartyGames plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        setupLanguage();
    }

    /**
     * Sets up the language file based on the locale specified in the config file.
     * If the locale does not match any available language file, it defaults to English.
     */
    private void setupLanguage() {
        // Get the locale from the config
        String locale = plugin.getConfigManager().getConfig().getString("locale", "en");

        // Define the lang folder path
        File langFolder = new File(plugin.getDataFolder(), "lang");

        // Check if lang folder exists; if not, create and populate it with default language files
        if (!langFolder.exists()) {
            langFolder.mkdirs();
            cloneDefaultLangFiles();
        }

        // Check if the corresponding language file exists
        File langFile = new File(langFolder, "lang_" + locale + ".yml");

        if (!langFile.exists()) {
            logger.warning("Locale " + locale + " not found! Defaulting to English.");
            langFile = new File(langFolder, "lang_en.yml"); // Fallback to English
        }

        // Load the language configuration
        this.languageConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public String getPrefix() {
        return languageConfig.getString("prefix");
    }

    /**
     * Clones the default language files from the resources folder into the plugin's lang folder.
     */
    private void cloneDefaultLangFiles() {
        try {
            plugin.saveResource("lang/lang_en.yml", false); // False to prevent overwriting
            plugin.saveResource("lang/lang_es.yml", false);
            logger.info("Default language files copied to the lang folder.");
        } catch (Exception e) {
            logger.severe("Failed to copy default language files: " + e.getMessage());
        }
    }

    /**
     * Gets a string from the loaded language file based on the specified path.
     *
     * @param path the path to the string in the language file (e.g., "messages.welcome")
     * @return the value of the string at the specified path, or an error message if not found
     */
    public String getString(String path) {
        if (languageConfig.contains(path)) {
            return languageConfig.getString(path).replace("{prefix}", languageConfig.getString("prefix"));
        } else {
            logger.warning("String not found for path: " + path);
            return "Missing translation: " + path;
        }
    }
}
