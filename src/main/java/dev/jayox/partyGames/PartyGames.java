package dev.jayox.partyGames;

import dev.jayox.partyGames.Commands.PartyCommand;
import dev.jayox.partyGames.Files.CacheFile;
import dev.jayox.partyGames.Files.CacheFilesManager;
import dev.jayox.partyGames.Files.ConfigurationManager;
import dev.jayox.partyGames.Files.LanguageManager;
import dev.jayox.partyGames.Utils.Message;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@SuppressWarnings({ "unused" })
public final class PartyGames extends JavaPlugin {

    private ConfigurationManager configManager;
    private YamlConfiguration configuration;

    private LanguageManager langManager;

    private CacheFilesManager cacheFileManager;

    private Message messageUtil;
    @Override
    public void onEnable() {
        // Initialize the ConfigurationManager
        configManager = new ConfigurationManager(this);
        getLogger().info("Login arangement: {}, starting config::service");

        // Check if the config exists; if not, create it
        if (!configManager.existsConfig()) {
            configManager.createConfigFile();
        } else {
            configuration = configManager.getConfig();
        }

        // Initialize the LanguageManager
        langManager = new LanguageManager(this);
        getLogger().info("Using default language manager (v" + langManager.langManagerVersion + ")");

        // Initialize the CacheFileManager
        cacheFileManager = new CacheFilesManager(this);



        // Initialize the MessageUtil
        messageUtil = new Message();

        // Load commands
        PluginCommand partyCommand = getCommand("party");
        partyCommand.setExecutor(new PartyCommand(this));

        // TODO: Implement bStats metrics, and license verification

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Get configuration manager
     *
     * @return Configuration Manager
     */
    public ConfigurationManager getConfigManager() {
        return configManager;
    }

    /**
     * Get language manager
     *
     * @return Language Manager
     */
    public LanguageManager getLangManager() {
        return langManager;
    }

    /**
     * Get cache file manager
     *
     * @return Cache File Manager
     */
    public CacheFilesManager getCacheFileManager() {
        return cacheFileManager;
    }

    /**
     * Get message util
     *
     * @return Message Util
     */
    public Message getMessageUtil() {
        return messageUtil;
    }
}
