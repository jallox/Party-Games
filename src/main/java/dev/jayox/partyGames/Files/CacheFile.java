package dev.jayox.partyGames.Files;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CacheFile {

    private final File file;
    private JSONObject jsonObject;

    public CacheFile(File file) {
        this.file = file;
        load();
    }

    /**
     * Loads the cache file into a JSONObject for easy manipulation.
     */
    private void load() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(file)) {
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current state of the JSONObject back to the file.
     */
    private void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assign a party ID to the cache file.
     *
     * @param partyId The party ID to assign
     */
    public void setParty(String partyId) {
        jsonObject.put("partyId", partyId);
        save();
    }

    /**
     * Set whether the file should be deleted when the party finishes.
     *
     * @param delete Whether the file should be deleted
     */
    public void deleteWhenPartyFinish(boolean delete) {
        jsonObject.put("deleteWhenFinish", delete);
        save();
    }

    /**
     * Assign a game ID to the cache file.
     *
     * @param gameId The game ID to assign
     */
    public void setGame(String gameId) {
        jsonObject.put("gameId", gameId);
        save();
    }

    /**
     * Set whether the file should be deleted when the game finishes.
     *
     * @param delete Whether the file should be deleted
     */
    public void deleteWhenGameFinish(boolean delete) {
        jsonObject.put("deleteWhenFinish", delete);
        save();
    }

    /**
     * Set whether the file should be deleted when the server stops.
     *
     * @param delete Whether the file should be deleted
     */
    public void deleteWhenStop(boolean delete) {
        jsonObject.put("delete", delete);
        save();
    }

    /**
     * Deletes the cache file.
     */
    public void delete() {
        file.delete();
    }

    /**
     * Gets the value of a specific characteristic stored in the cache file data.
     *
     * @param key The key of the characteristic you want to retrieve.
     * @return The value of the specified characteristic, or null if it doesn't exist.
     */
    public Object getData(String key) {

        if (jsonObject != null && jsonObject.containsKey("data")) {
            JSONObject dataObject = (JSONObject) jsonObject.get("data");

            if (dataObject.containsKey(key)) {
                return dataObject.get(key);
            }
        }
        return null;
    }

    /**
     * Adds data to the cache file's data section.
     *
     * @param key The key for the data
     * @param value The value to associate with the key
     */
    public void addData(String key, Object value) {
        JSONObject data = (JSONObject) jsonObject.get("data");
        data.put(key, value);
        save();
    }
}
