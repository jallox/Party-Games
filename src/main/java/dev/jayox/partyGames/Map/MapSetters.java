package dev.jayox.partyGames.Map;

import dev.jayox.partyGames.PartyGames;
import org.bukkit.Location;

public class MapSetters {
    private final PartyGames plugin;

    public MapSetters(PartyGames plugin) {
        this.plugin = plugin;
    }

    public boolean setSpawn(String mapname, Location position) {
        MapMetadata mapMetadata = new MapMetadata(plugin, mapname);

        int x = position.getBlockX();
        int y = position.getBlockY();
        int z = position.getBlockZ();

        float pitch = position.getPitch();
        float yaw = position.getYaw();

        mapMetadata.addData("spawn.x", x);
        mapMetadata.addData("spawn.y", y);
        mapMetadata.addData("spawn.z", z);
        mapMetadata.addData("spawn.yaw", yaw);
        mapMetadata.addData("spawn.pitch", pitch);

        plugin.getLogger().info("[Map Editor Positions]: Set spawn position");

        return true;
    }

    public boolean setRespawn(String mapname, Location position) {
        MapMetadata mapMetadata = new MapMetadata(plugin, mapname);

        int x = position.getBlockX();
        int y = position.getBlockY();
        int z = position.getBlockZ();

        float pitch = position.getPitch();
        float yaw = position.getYaw();

        mapMetadata.addData("respawn.x", x);
        mapMetadata.addData("respawn.y", y);
        mapMetadata.addData("respawn.z", z);
        mapMetadata.addData("respawn.yaw", yaw);
        mapMetadata.addData("respawn.pitch", pitch);

        plugin.getLogger().info("[Map Editor Positions]: Set respawn position");

        return true;
    }

    public boolean setWaiting(String mapname, Location position) {
        MapMetadata mapMetadata = new MapMetadata(plugin, mapname);

        int x = position.getBlockX();
        int y = position.getBlockY();
        int z = position.getBlockZ();

        float pitch = position.getPitch();
        float yaw = position.getYaw();

        mapMetadata.addData("waiting.x", x);
        mapMetadata.addData("waiting.y", y);
        mapMetadata.addData("waiting.z", z);
        mapMetadata.addData("waiting.yaw", yaw);
        mapMetadata.addData("waiting.pitch", pitch);

        plugin.getLogger().info("[Map Editor Positions]: Set waiting position");

        return true;
    }
}
