package dev.jayox.partyGames.Commands;

import dev.jayox.partyGames.MainCommand.HandleMapCommand;
import dev.jayox.partyGames.PartyGames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    private final PartyGames plugin;
    public MainCommand(PartyGames plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("partygames.command.admin") || !sender.isOp()) {
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("errors.no-permission")
            ));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg reload"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Reloads plugin configuration"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg help (page)"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about a specified page"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Pages:&n general, map, config"));           //! When adding more categories do it here and in other lines with this comment
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg licenseinfo"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about your license"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Required permission: partygames.admin.license"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> handleReload(sender, args);
            case "help" -> handleHelp(sender, args);
            case "map" -> {
                new HandleMapCommand(sender, args, plugin);
                return true;
            }
        }
        return false;
    }

    // Handle config reload
    private boolean handleReload(CommandSender sender, String[] args) {
        int startTime = (int) System.currentTimeMillis();

        sender.sendMessage(plugin.getMessageUtil().colorText("&fReloading configuration file &7(config.yml)"));
        plugin.reloadConfig();
        sender.sendMessage(plugin.getMessageUtil().colorText("&a&lDone!"));

        int endTime = (int) System.currentTimeMillis();
        int totalTime = endTime - startTime;        // Calculate reloading time im milliseconds

        plugin.getLogger().info("Config reloaded in " + totalTime + "ms by " + sender.getName());

        return true;
    }

    private boolean handleHelp(CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg reload"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Reloads plugin configuration"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg help (page)"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about a specified page"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Pages:&n general, map, config"));       //! When adding more categories do it here and in other lines with this comment
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
            sender.sendMessage(plugin.getMessageUtil().colorText(" "));

            sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg licenseinfo"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about your license"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&8Required permission: partygames.admin.license"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
            sender.sendMessage(plugin.getMessageUtil().colorText("&cYou must specify a valid page (category)"));

            return true;
        }
        String page = args[1];
        switch (page.toLowerCase()) {
            case "general" -> {
                sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg reload"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Reloads plugin configuration"));
                sender.sendMessage(plugin.getMessageUtil().colorText(" "));

                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg help (page)"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about a specified page"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&8Pages:&n general, map, config, others"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
                sender.sendMessage(plugin.getMessageUtil().colorText(" "));

                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg licenseinfo"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about your license"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&8Required permission: partygames.admin.license"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
            }
            case "map" -> {
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

                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg map modify <mapname>"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Modifies a map (Launch a new editor world)"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));

                sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (Map)-----------------"));
            }
            case "config" -> {
                sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (Configuration)-----------------"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg reload"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Reloads plugin configuration"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (Configuration)-----------------"));
            }
            default -> {
                sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg reload"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Reloads plugin configuration"));
                sender.sendMessage(plugin.getMessageUtil().colorText(" "));

                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg help (page)"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about a specified page"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&8Pages:&n general, map, config"));   //! When adding more categories do it here and in other lines with this comment
                sender.sendMessage(plugin.getMessageUtil().colorText("&8Also watch &nhttps://partygames.gitbook.io/partygames-documentation/"));
                sender.sendMessage(plugin.getMessageUtil().colorText(" "));

                sender.sendMessage(plugin.getMessageUtil().colorText("&e/pg licenseinfo"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7Get information about your license"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&8Required permission: partygames.admin.license"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&7&lHelp Menu (General)-----------------"));
                sender.sendMessage(plugin.getMessageUtil().colorText("&cYou must specify a valid page (category)"));
            }
        }
        return true;
    }
}
