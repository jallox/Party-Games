package dev.jayox.partyGames.Map;

import dev.jayox.partyGames.PartyGames;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MapMetadata {
    private final File metadataFile;
    private final FileConfiguration config;

    public MapMetadata(PartyGames plugin, String mapName) {
        // Initialize metadata file and load configuration
        this.metadataFile = new File(plugin.getDataFolder(), "maps/" + mapName + ".yml");
        this.config = YamlConfiguration.loadConfiguration(metadataFile);
    }

    // Method to save configuration changes
    private void saveConfig() {
        try {
            config.save(metadataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add data to the configuration
    public void addData(String path, Object data) {
        config.set(path, data);
        saveConfig();
    }

    // Method to retrieve data from the configuration
    public Object get(String path) {
        return config.get(path);
    }

    // Method to check if a path exists in the configuration
    public boolean contains(String path) {
        return config.contains(path);
    }

    // Method to remove a data entry
    public void removeData(String path) {
        config.set(path, null);
        saveConfig();
    }
}
