package fr.radi3nt.explosiveores.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ExplosiveOreTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        if (args.length==1) {
            commands.add("start");
            commands.add("stop");
        }
        return commands;
    }
}
