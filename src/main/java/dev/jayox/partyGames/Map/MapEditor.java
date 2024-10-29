package dev.jayox.partyGames.Map;

import dev.jayox.partyGames.PartyGames;
import dev.jayox.partyGames.Worlds.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class MapEditor {
    private final PartyGames plugin;
    private final Worlds worldManager;

    private boolean mapEditorRunning;
    public MapEditor (PartyGames plugin) {
        this.plugin = plugin;
        worldManager = new Worlds();
    }

    public boolean createMapEditor(String mapname, Player editorPlayer) {
        if(!mapEditorRunning) {

            mapEditorRunning = true;
            plugin.getLogger().info("[Map Editor Main]: Creating new MapEditor...");

            // Create the world
            worldManager.createWorld(mapname, "VOID", 1, World.Environment.NORMAL);

            if (editorPlayer instanceof Player) {
                // Teleport the player
                editorPlayer.teleport(worldManager.getWorld(mapname).getSpawnLocation());
                plugin.getLogger().info("[Map Editor Main]: Player teleported to world!");

                // Create a new scoreboard manager and scoreboard
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();

                // Create an objective in the sidebar to display the scoreboard
                Objective objective = scoreboard.registerNewObjective("mapEditor", "dummy", plugin.getMessageUtil().colorText("&a&lMAP EDITOR"));
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                // Add some information to the scoreboard
                Score mapNameScore = objective.getScore(String.valueOf(plugin.getMessageUtil().colorText("&fWorld name: &a" + mapname)));
                mapNameScore.setScore(2);

                Score examplePointsScore = objective.getScore(String.valueOf(plugin.getMessageUtil().colorText("&fMap type: &aVOID")));
                examplePointsScore.setScore(1);

                // Show the scoreboard to the player
                editorPlayer.setScoreboard(scoreboard);

            } else {
                plugin.getLogger().warning("[Map Editor Main]: FATAL ERROR: Console can't create map editors! Please use the command in-game.");
                worldManager.deleteWorld(mapname);
                plugin.getLogger().warning("[Map Editor Main]: Deleted world " + mapname + " (Can't send any player, world is unused)");

                return false;
            }
            return true;
        } else {
            plugin.getLogger().warning("[Map Editor Main]: A map editor is already running!");
            return  false;
        }
    }
}
