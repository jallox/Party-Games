package dev.jayox.partyGames;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import dev.jayox.partyGames.Commands.MainCommand;
import dev.jayox.partyGames.Commands.PartyCommand;
import dev.jayox.partyGames.DB.DBManager;
import dev.jayox.partyGames.Files.ConfigurationManager;
import dev.jayox.partyGames.Files.LanguageManager;
import dev.jayox.partyGames.Utils.Message;
import org.apache.maven.model.License;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.http.HttpClient;
import java.sql.SQLException;

@SuppressWarnings({ "unused" })
public final class PartyGames extends JavaPlugin {

    private ConfigurationManager configManager;

    private LanguageManager langManager;

    private DBManager dbManager;

    private Message messageUtil;



    @Override
    public void onEnable() {

        // TODO: Fix problem when starting first the plugin related to config.

        // I don't know what is happening here, but this fking piece of code is making me cry
        // why is it not working? I don't know, just not working

        // Initialize the ConfigurationManager
        configManager = new ConfigurationManager(this);
        getLogger().info("Awaiting for plugin to start...");

        // Check if the config exists; if not, create it
        if (!configManager.existsConfig()) {
            configManager.createConfigFile();
        }
        getLogger().info("PartyGames! by Jayox");
        YamlConfiguration configuration = configManager.getConfig();

        // Initialize the LanguageManager
        langManager = new LanguageManager(this);
        getLogger().info("Using language " + configuration.getString("locale"));

        // Initialize the MessageUtil
        messageUtil = new Message();


        // Initialize the DBManager
        try {
            dbManager = new DBManager(this, configuration.getString("database.type"));  //! It seems to be that this is called before the config is ready?


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Load commands
        PluginCommand partyCommand = getCommand("party");
        partyCommand.setExecutor(new PartyCommand(this));

        PluginCommand mainCommand = getCommand("pg");
        mainCommand.setExecutor(new MainCommand(this));


        LicenseActivator licenseActivator = new LicenseActivator();
        switch (licenseActivator.activateLicense(getConfig().getString("LICENSE_KEY"), this.getName())) {
            case 1 -> {
                getLogger().info("[License activator]: License is valid!");
            }
            case 2 -> {
                getLogger().warning("[License activator]:  You have reached your daily quota! Disabling plugin!");
                this.setEnabled(false);
            }
            default -> {
                getLogger().warning("[License activator]: Your license is not valid! Disabling plugin!");
                this.setEnabled(false);
            }
        }

        // TODO: Implement bStats metrics
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
     * Get message util
     *
     * @return Message Util
     */
    public Message getMessageUtil() {
        return messageUtil;
    }

    /**
     * Get database manager
     *
     * @return DB Manager
     */
    public DBManager getDbManager() {
        return dbManager;
    }

    public WorldEdit getWorldEdit() {

        return WorldEdit.getInstance();
    }

}
