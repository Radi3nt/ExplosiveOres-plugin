package fr.radi3nt.explosiveores.commands;

import fr.radi3nt.explosiveores.MainExplosiveOres;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ExplosiveOreCommand implements CommandExecutor {

    Plugin plugin = MainExplosiveOres.getPlugin(MainExplosiveOres.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("exo.manage")) {
            if (args.length > 0) {
                if (args[0].equals("start")) {
                    plugin.getConfig().set("activated", true);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.DARK_GREEN + "[ExplosivesOres]" + ChatColor.GOLD + " Explosions activated");
                }
                if (args[0].equals("stop")) {
                    plugin.getConfig().set("activated", false);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.DARK_GREEN + "[ExplosivesOres]" + ChatColor.GOLD + " Explosions deactivated");
                }
            } else {
                sender.sendMessage(ChatColor.DARK_GREEN + "[ExplosivesOres]" + ChatColor.RED + " This command require an argument");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_GREEN + "[ExplosivesOres]" + ChatColor.RED + " No permission");
        }
        return true;
    }
}
