package fr.mimifan.purestcraftcore.commands.homes;

import fr.mimifan.purestcraftcore.api.configuration.HomesFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandDelHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return true;

        if(args.length < 1) {
            player.sendMessage("§cUtilisation: /delhome <nom>");
            return true;
        }

        if(!HomesFile.getInstance().homeExists(player.getName(), args[0])){
            player.sendMessage("§cCette résidence n'existe pas.");
            return true;
        }

        HomesFile.getInstance().removeHome(player.getName(), args[0]);
        HomesFile.getInstance().save();
        player.sendMessage("§aLa résidence §e" + args[0] + " §aest supprimée.");

        return true;
    }
}
