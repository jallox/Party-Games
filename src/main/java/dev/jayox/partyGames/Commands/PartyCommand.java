package dev.jayox.partyGames.Commands;

import dev.jayox.partyGames.PartyGames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class PartyCommand implements CommandExecutor {
    private final PartyGames plugin;

    public PartyCommand(PartyGames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("partygames.command.party") || !sender.isOp()) {
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("errors.no-permission")
            ));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("errors.only-players")
            ));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.usage")
            ));
            return true;
        }

        return switch (args[0].toLowerCase()) {
            case "create" -> handleCreateParty(player, args);
            case "invite" -> handleInvite(player, args);
            case "join" -> handleJoinParty(player, args);
            default -> {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.invalid-subcommand")
                ));
                yield true;
            }
        };
    }

    private boolean handleCreateParty(Player player, String[] args) {
        try {
            if (args.length < 2) {
                player.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.usage")
                ));
                return true;
            }

            String partyName = args[1].toLowerCase();
            if (partyName.isBlank()) {
                player.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.usage")
                ));
                return true;
            }

            String partyId = UUID.randomUUID().toString().substring(0, 4);
            String insertPartyQuery = "INSERT INTO party (partyId, partyName, ownerName, ownerUUID, inGame) VALUES (?, ?, ?, ?, ?)";

            plugin.getDbManager().query(insertPartyQuery, new Object[]{partyId, partyName, player.getName(), player.getUniqueId().toString(), false});

            player.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.party-created").replace("{name}", partyName)
            ));

            return true;
        } catch (Exception err) {
            player.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.error-creating")
            ));
            plugin.getLogger().warning("Error creating party: " + err.getMessage());
            plugin.getLogger().warning(Arrays.toString(err.getStackTrace()));
            return true;
        }
    }

    private boolean handleInvite(Player sender, String[] args) {
        try {
            if (args.length < 2) {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.usage")
                ));
                return true;
            }

            String findPartyQuery = "SELECT * FROM party WHERE ownerName = ?";
            ResultSet rs = plugin.getDbManager().queryResult(findPartyQuery, new Object[]{sender.getName()});
            if (!rs.next()) {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.no-party")
                ));
                return true;
            }

            Player invitedPlayer = plugin.getServer().getPlayer(args[1]);
            if (invitedPlayer == null || !invitedPlayer.isOnline()) {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.player-not-exists")
                ));
                return true;
            }

            String checkPlayerPartyQuery = "SELECT * FROM party WHERE invitedPlayers LIKE ?";
            ResultSet playerRs = plugin.getDbManager().queryResult(checkPlayerPartyQuery, new Object[]{"%" + invitedPlayer.getName() + "%"});
            if (playerRs.next()) {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.player-already-in-party")
                ));
                return true;
            }

            String partyId = rs.getString("partyId").substring(0, 4);

            invitedPlayer.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.invited")
                            .replace("{owner}", sender.getName())
                            .replace("{code}", partyId)
            ));

            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.invite-sent")
                            .replace("{player}", invitedPlayer.getName())
                            .replace("{code}", partyId)));

            return true;
        } catch (SQLException err) {
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.error-inviting")
            ));
            plugin.getLogger().warning("Error inviting player: " + err.getMessage());
            return true;
        }
    }

    private boolean handleJoinParty(Player sender, String[] args) {
        try {
            if (args.length < 2) {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.usage")
                ));
                return true;
            }

            String partyCode = args[1];
            sender.sendMessage(partyCode);
            String findPartyByCodeQuery = "SELECT * FROM party WHERE partyId = ?";
            ResultSet rs = plugin.getDbManager().queryResult(findPartyByCodeQuery, new Object[]{partyCode});
            if (!rs.next()) {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.invalid-code")
                ));
                return true;
            }

            // Check if the player is already in a party
            String checkPlayerPartyQuery = "SELECT * FROM party WHERE invitedPlayers LIKE ?";
            ResultSet playerRs = plugin.getDbManager().queryResult(checkPlayerPartyQuery, new Object[]{"%" + sender.getName() + "%"});
            if (playerRs.next()) {
                sender.sendMessage(plugin.getMessageUtil().colorText(
                        plugin.getLangManager().getString("party-command.already-in-party")
                ));
                return true;
            }

            String invitedPlayers = rs.getString("invitedPlayers");
            invitedPlayers = invitedPlayers == null ? sender.getName() : invitedPlayers + ";" + sender.getName();

            String updatePartyPlayersQuery = "UPDATE party SET invitedPlayers = ? WHERE partyId = ?";
            plugin.getDbManager().query(updatePartyPlayersQuery, new Object[]{invitedPlayers, partyCode});

            String owner = rs.getString("ownerName");
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.joined-party").replace("{owner}", owner)
            ));

            return true;
        } catch (SQLException err) {
            sender.sendMessage(plugin.getMessageUtil().colorText(
                    plugin.getLangManager().getString("party-command.error-joining")
            ));
            plugin.getLogger().warning("Error joining party: " + err.getMessage());
            return true;
        }
    }

}
