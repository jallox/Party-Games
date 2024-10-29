package dev.jayox.partyGames.Map;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.session.ClipboardHolder;
import dev.jayox.partyGames.PartyGames;
import dev.jayox.partyGames.Worlds.Worlds;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Nullable;

public class MapEditor {
    private final PartyGames plugin;
    private final Worlds worldManager;

    private boolean mapEditorRunning;
    public MapEditor (PartyGames plugin) {
        this.plugin = plugin;
        worldManager = new Worlds();
    }

    public boolean createMapEditor(String mapname, Player editorPlayer) {
        if (!mapEditorRunning) {
            mapEditorRunning = true;
            plugin.getLogger().info("[Map Editor Main]: Creating new MapEditor...");

            // Create the world
            worldManager.createWorld(mapname, "VOID", 1, org.bukkit.World.Environment.NORMAL);
            org.bukkit.World editorWorld = worldManager.getWorld("editor_" + mapname);

            if (editorPlayer != null && editorWorld != null) {
                // Teleport player to coordinates (0, 0, 0)
                editorPlayer.teleport(new org.bukkit.Location(editorWorld, 0, 0, 0));
                plugin.getLogger().info("[Map Editor Main]: Player teleported to (0, 0, 0) in world!");

                // Open schematic selection GUI
                openSchematicSelectionGui(editorPlayer, editorWorld, mapname);

                // Create scoreboard
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();
                Objective objective = scoreboard.registerNewObjective("mapEditor", "dummy", plugin.getMessageUtil().colorText("&a&lMAP EDITOR"));
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                // Add basic info to scoreboard
                Score mapNameScore = objective.getScore(plugin.getMessageUtil().chatColor("&aMap name: " + mapname));
                mapNameScore.setScore(3);

                Score textScore1 = objective.getScore(plugin.getMessageUtil().chatColor("&aSave: &n/pg map save"));
                textScore1.setScore(2);

                Score textScore2 = objective.getScore(plugin.getMessageUtil().chatColor("&Leave: &n/pg map exit"));
                textScore2.setScore(1);

                editorPlayer.setScoreboard(scoreboard);
            }
            return true;
        } else {
            plugin.getLogger().warning("[Map Editor Main]: A map editor is already running!");
            return false;
        }
    }

    private void openSchematicSelectionGui(Player player, org.bukkit.World editorWorld, String mapname) {
        Gui gui = Gui.gui()
                .title(plugin.getMessageUtil().colorText("&6Select Schematic"))
                .rows(3)
                .create();

        File schematicsFolder = new File(plugin.getDataFolder(), "schematics");
        if (schematicsFolder.exists() && schematicsFolder.isDirectory()) {
            for (File schematicFile : schematicsFolder.listFiles()) {
                if (schematicFile.getName().endsWith(".schematic")) {
                    gui.addItem(ItemBuilder.from(Material.PAPER)
                            .name(plugin.getMessageUtil().colorText("&a" + schematicFile.getName()))
                            .asGuiItem(event -> {
                                try {
                                    loadAndPasteSchematic(editorWorld, schematicFile, player, mapname);
                                } catch (IOException e) {
                                    plugin.getLogger().warning("[Map Editor Main]: Failed to load schematic " + schematicFile.getName());
                                    player.sendMessage(plugin.getMessageUtil().colorText("&cFailed to load schematic!"));
                                }
                                gui.close(player);
                            }));
                }
            }
        } else {
            plugin.getLogger().warning("[Map Editor Main]: No schematics folder found!");
        }

        gui.open(player);
    }

    private void loadAndPasteSchematic(org.bukkit.World editorWorld, File schematicFile, Player player, String mapname) throws IOException {
        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        if (format != null) {
            try (FileInputStream fis = new FileInputStream(schematicFile);
                 EditSession editSession = plugin.getWorldEdit().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(editorWorld), -1)) {

                Clipboard clipboard = format.getReader(fis).read();
                BlockVector3 pasteLocation = BlockVector3.at(0, 0, 0);
                ClipboardHolder holder = new ClipboardHolder(clipboard);

                editSession.setBlock(pasteLocation, (Pattern) holder.createPaste(editSession).to(pasteLocation).ignoreAirBlocks(false).build());
                plugin.getLogger().info("[Map Editor Main]: Schematic pasted at (0, 0, 0) in world " + mapname);

                // Teleport player to paste location
                player.teleport(new org.bukkit.Location(editorWorld, 0, 0, 0));

                // Update scoreboard with schematic name
                Scoreboard scoreboard = player.getScoreboard();
                Objective objective = scoreboard.getObjective("mapEditor");
                if (objective != null) {
                    Score schematicScore = objective.getScore(plugin.getMessageUtil().chatColor("&aSchematic: " + schematicFile.getName()));
                    schematicScore.setScore(2);
                }
            } catch (MaxChangedBlocksException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean saveEditor(Player editorPlayer) {
        // Check if is any editor running
        if (!mapEditorRunning) {
            editorPlayer.sendMessage(plugin.getMessageUtil().colorText("&cYou don't have any editor running! Exit."));
            return false;
        }

        // Get the world where the player is, and check if is an editor world
        World currentWorld = (World) editorPlayer.getWorld();
        if(!currentWorld.getName().startsWith("editor_")) {
            editorPlayer.sendMessage(plugin.getMessageUtil().colorText("&cYou are in a normal world! To save, go to an editor world"));
            return false;
        }
        return true;
    }
}
