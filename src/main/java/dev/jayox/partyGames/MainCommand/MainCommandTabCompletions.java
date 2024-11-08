package dev.jayox.partyGames.MainCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTabCompletions implements org.bukkit.command.TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            List<String> values = new ArrayList<>();
            values.add("map");
            values.add("reload");
            values.add("help");
            return values;
        } else if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("map")) {
                List<String> values = new ArrayList<>();
                values.add("editor");
                values.add("setspawn");
                values.add("setpoint");
                values.add("setrespawn");
                values.add("setwaiting");
                values.add("minigame");
                values.add("setpoint");
                values.add("save");
                values.add("modify");
                return values;
            }

            if(args[0].equalsIgnoreCase("help")) {
                List<String> values = new ArrayList<>();
                values.add("config");
                values.add("map");
                values.add("general");
                return values;
            }

                if(args[1].equalsIgnoreCase("minigame")) {
                    List<String> values = new ArrayList<>();
                    values.add("cut-the-grass");
                    values.add("knockback");
                    values.add("all-vs-all");
                    values.add("answer-racers");
                    return values;
                }

                if(args[1].equalsIgnoreCase("setpoint")) {
                    List<String> values = new ArrayList<>();
                    values.add("point-type");
                    return values;
                }

        }







        return List.of();

    }
}
