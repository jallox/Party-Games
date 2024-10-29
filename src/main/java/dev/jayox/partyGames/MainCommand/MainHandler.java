package dev.jayox.partyGames.MainCommand;

import dev.jayox.partyGames.Map.MapSetters;
import dev.jayox.partyGames.PartyGames;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainHandler {
    private MapSetters mapSetters;
    public MainHandler(PartyGames plugin) {
        mapSetters = new MapSetters(plugin);
    }

    public boolean handleSetSpawn(CommandSender sender, PartyGames plugin) {
        Player player = (Player) sender;

        player.sendMessage(plugin.getMessageUtil().colorText("&aSpawn point set for this map!"));
        player.sendMessage(plugin.getMessageUtil().colorText("&7Next: &n/pg map setrespawn"));
        return mapSetters.setSpawn(player.getWorld().getName(), player.getLocation());

    }

    public boolean handleSetRespawn(CommandSender sender, PartyGames plugin) {
        Player player = (Player) sender;

        player.sendMessage(plugin.getMessageUtil().colorText("&aRespawn point set for this map!"));
        player.sendMessage(plugin.getMessageUtil().colorText("&7Next: &n/pg map setwaiting"));
        return mapSetters.setRespawn(player.getWorld().getName(), player.getLocation());

    }

    public boolean handleSetWaiting(CommandSender sender, PartyGames plugin) {
        Player player = (Player) sender;

        player.sendMessage(plugin.getMessageUtil().colorText("&aWaiting position set for this map!"));
        player.sendMessage(plugin.getMessageUtil().colorText("&7Next: &n/pg map setminigames"));
        return mapSetters.setWaiting(player.getWorld().getName(), player.getLocation());

    }
}
