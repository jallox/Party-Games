package dev.jayox.partyGames.Files;

import dev.jayox.partyGames.PartyGames;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {
    private final PartyGames plugin;
    private YamlConfiguration config;

    /**
     * Constructor to initialize the ConfigurationManager.
     *
     * @param p the plugin instance (PartyGames) to access its data folder and other methods.
     */
    public ConfigurationManager(PartyGames p) {
        this.plugin = p;
        // Initialize config when the manager is created
        this.config = loadConfig();
    }

    /**
     * Checks if the config.yml file exists in the plugin's data folder.
     * If it does not exist, it will create a new config.yml file.
     *
     * @throws IOException if an I/O error occurs while creating the config file.
     */
    public void checkForConfig() throws IOException {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        // Create new file if it doesn't exist
        if (!configFile.exists()) {
            if (configFile.createNewFile()) {
                plugin.getLogger().info("Config file created.");
            } else {
                plugin.getLogger().warning("Failed to create config file.");
            }
        }
    }

    public boolean createConfigFile() {
        // Define the target file location in the plugin's data folder
        File configFile = new File(plugin.getDataFolder(), "config.yml");

        // Check if the config file already exists to avoid overwriting
        if (configFile.exists()) {
            plugin.getLogger().warning("Config file already exists.");
            return false;  // File already exists, no need to create a new one
        }

        // Ensure the plugin's data folder exists, create it if not
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        try {
            // Save the default config file from resources to the data folder
            plugin.saveResource("config.yml", false); // 'false' prevents overwriting if the file exists
            plugin.getLogger().info("Config file created successfully.");
            return true;  // File created successfully
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to create config file: " + e.getMessage());
            return false;  // An error occurred while creating the file
        }
    }

    /**
     * Checks if the config.yml file exists.
     *
     * @return true if the config.yml file exists, false if not.
     */
    public boolean existsConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        return configFile.exists();
    }

    /**
     * Regenerates the config.yml file by deleting the old one and generating a fresh file.
     * It also reloads the configuration into memory.
     */
    public void regenConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (configFile.delete()) {
            plugin.saveDefaultConfig();  // Save the default config from resources
            this.config = loadConfig();  // Reload the config
            plugin.getLogger().info("Config file regenerated.");
        } else {
            plugin.getLogger().warning("Failed to regenerate config file.");
        }
    }

    /**
     * Gets the current configuration loaded into memory.
     *
     * @return the YamlConfiguration object representing the config.yml.
     */
    public YamlConfiguration getConfig() {
        return this.config;
    }

    /**
     * Reloads the config.yml file from disk. This method will refresh the in-memory
     * configuration with the latest changes made on the file system.
     *
     * @return true if the config was reloaded successfully, false if any error occurred.
     */
    public boolean reloadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.getLogger().warning("Config file not found, cannot reload.");
            return false;
        }

        try {
            this.config = YamlConfiguration.loadConfiguration(configFile);  // Load updated config
            plugin.getLogger().info("Config reloaded successfully.");
            return true;
        } catch (Exception e) {
            plugin.getLogger().severe("Error reloading config: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads the config.yml file into memory.
     * This method is used internally to avoid code repetition.
     *
     * @return YamlConfiguration object of the loaded config.
     */
    private YamlConfiguration loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }
}
