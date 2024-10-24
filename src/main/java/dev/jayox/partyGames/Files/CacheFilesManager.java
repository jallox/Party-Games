package dev.jayox.partyGames.Files;

import dev.jayox.partyGames.PartyGames;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.json.simple.JSONObject;

public class CacheFilesManager {

    private final PartyGames plugin;
    private final File cacheFolder;

    public CacheFilesManager(PartyGames plugin) {
        this.plugin = plugin;
        this.cacheFolder = new File(plugin.getDataFolder(), "cache");

        // Create the cache folder if it doesn't exist
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs();
        }
    }

    /**
     * Creates a new cache file with the given name.
     *
     * @param cacheName The name of the cache file (without .json extension)
     * @return A new CacheFile instance
     */
    public CacheFile createFile(String cacheName) {
        File file = new File(cacheFolder, cacheName + ".json");

        if (!file.exists()) {
            try {
                file.createNewFile();
                // Initialize the JSON object structure
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileId", UUID.randomUUID().toString());
                jsonObject.put("data", new JSONObject());
                jsonObject.put("deleteWhenFinish", false);

                // Write the empty data structure to the file
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(jsonObject.toJSONString());
                }

            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create cache file: " + e.getMessage());
            }
        }

        return new CacheFile(file);
    }

    /**
     * Deletes all cache files.
     */
    public void deleteAllCacheFiles() {
        for (File file : cacheFolder.listFiles()) {
            file.delete();
        }
    }

    /**
     * Searches for a cache file where the name contains the specified string.
     *
     * @param namePart the part of the file name to search for
     * @return the matching CacheFile, or null if not found
     */
    public CacheFile searchByNameContaining(String namePart) {
        // Iterate over files in the cache folder
        // TODO: Find a more efficient way to do this before is too late!
        for (File file : Objects.requireNonNull(cacheFolder.listFiles())) {
            if (file.isFile() && file.getName().contains(namePart)) {
                // Found a file containing the specified string in its name
                return new CacheFile(file);
            }
        }
        // No matching file found
        return null;
    }
}
