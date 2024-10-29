package dev.jayox.partyGames.MainCommand;

import com.sun.tools.javac.Main;
import dev.jayox.partyGames.Map.MapEditor;
import dev.jayox.partyGames.PartyGames;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandleMapCommand {
    // TODO: Add logging
    public HandleMapCommand(CommandSender sender, String[] args, PartyGames plugin) {
        if (!sender.hasPermission("partygames.admin.map") || !sender.isOp()) {
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("errors.no-permission")
            ));
        }

        if (args.length < 2) {
            sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (Map)-----------------"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map editor <mapname>"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Launch a new map editor world"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map setspawn"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Sets the spawn in the selected map"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map setrespawn"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Sets the respawn point in the selected map"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map setwaiting"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Sets the waiting point in the selected map"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map setminigame <minigame>"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Select the corresponding minigame to that map"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map setpoint <type>"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7If the minigame requires it, select a specific point"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map save"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Save the updated map"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg modify <mapname>"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Modifies a map (Launch a new editor world)"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));

            sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (Map)-----------------"));
        } else {
            MainHandler mainHandler = new MainHandler(plugin);
            switch (args[1].toLowerCase()) {
                case "editor" -> new MapEditor(plugin).createMapEditor(args[2], (Player) sender);
                case "setspawn" -> mainHandler.handleSetSpawn(sender, plugin);
                case "setrespawn" -> mainHandler.handleSetRespawn(sender, plugin);
                case "setwaiting" -> mainHandler.handleSetWaiting(sender, plugin);
            }
        }
    }
}
