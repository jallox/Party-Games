package dev.jayox.partyGames.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Worlds {
    private final Map<String, World> worlds = new HashMap<>();

    /**
     * Creates a new world with a specified name, type, seed, and environment.
     * If type is "VOID", it will generate an empty void world.
     *
     * @param name         the name of the world.
     * @param type         the type of the world, e.g., FLAT, NORMAL, or VOID.
     * @param seed         the seed for the world generation.
     * @param environment  the environment of the world (NORMAL, NETHER, END).
     * @return the created world.
     */
    public World createWorld(String name, String type, long seed, World.Environment environment) {
        WorldCreator creator = new WorldCreator(name);
        creator.seed(seed);
        creator.environment(environment);

        // Set world type or custom generator based on the specified type
        if ("VOID".equalsIgnoreCase(type)) {
            creator.generator(new VoidWorldGenerator());  // Use custom void generator
        } else if ("FLAT".equalsIgnoreCase(type)) {
            creator.type(WorldType.FLAT);
        } else {
            creator.type(WorldType.NORMAL);
        }

        World world = creator.createWorld();
        if (world != null) {
            worlds.put(name, world);
        }
        return world;
    }

    // Other methods remain the same

    /**
     * Custom chunk generator to create a void world (empty).
     */
    public static class VoidWorldGenerator extends ChunkGenerator {
        @Override
        public ChunkData generateChunkData(World world, java.util.Random random, int x, int z, BiomeGrid biome) {
            return createChunkData(world); // Return an empty chunk
        }
    }

    /**
     * Loads an existing world by name.
     *
     * @param name the name of the world.
     * @return the loaded world or null if not found.
     */
    public World loadWorld(String name) {
        World world = Bukkit.getWorld(name);
        if (world != null) {
            worlds.put(name, world);
        }
        return world;
    }

    /**
     * Unloads a world by name, optionally saving changes.
     *
     * @param name    the name of the world to unload.
     * @param save    whether to save the world's state.
     * @return true if the world was successfully unloaded, false otherwise.
     */
    public boolean unloadWorld(String name, boolean save) {
        World world = worlds.get(name);
        if (world != null) {
            boolean result = Bukkit.unloadWorld(world, save);
            if (result) {
                worlds.remove(name);
            }
            return result;
        }
        return false;
    }

    /**
     * Retrieves a world by its name.
     *
     * @param name the name of the world.
     * @return the world or null if not found.
     */
    public World getWorld(String name) {
        return worlds.get(name);
    }

    /**
     * Generates a unique world name.
     *
     * @return a unique world name.
     */
    public String generateUniqueWorldName() {
        return "world_" + UUID.randomUUID();
    }

    /**
     * Lists all worlds managed by this class.
     *
     * @return a map of world names to World objects.
     */
    public Map<String, World> listWorlds() {
        return new HashMap<>(worlds);
    }

    /**
     * Deletes a world by its name. This will unload the world and delete its folder.
     * Be careful, as this action is irreversible.
     *
     * @param name the name of the world to delete.
     * @return true if the world was successfully deleted, false otherwise.
     */
    public boolean deleteWorld(String name) {
        // Attempt to unload the world first
        World world = Bukkit.getWorld(name);
        if (world != null) {
            if (!Bukkit.unloadWorld(world, false)) { // Unload without saving changes
                System.out.println("Failed to unload world: " + name);
                return false;
            }
        }

        // Delete the world folder from the filesystem
        File worldFolder = new File(Bukkit.getWorldContainer(), name);
        if (worldFolder.exists() && worldFolder.isDirectory()) {
            deleteDirectory(worldFolder);
            System.out.println("World deleted successfully: " + name);
            return true;
        } else {
            System.out.println("World folder not found: " + name);
            return false;
        }
    }

    /**
     * Recursively deletes a directory and its contents.
     *
     * @param directory the directory to delete.
     * @return true if deletion was successful, false otherwise.
     */
    private boolean deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directory.delete();
    }
}
