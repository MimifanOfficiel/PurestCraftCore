package fr.mimifan.purestcraftcore.commands.homes;

import fr.mimifan.purestcraftcore.api.configuration.HomesFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHome implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return true;

        if(args.length < 1) {
            player.sendMessage("§cUtilisation: /home <nom>");
            return true;
        }

        if(!HomesFile.getInstance().homeExists(player.getName(), args[0])){
            player.sendMessage("§cCette résidence n'existe pas.");
            return true;
        }

        if(HomesFile.getInstance().getHomeLocation(player.getName(), args[0]) == null){
            player.sendMessage("§cUne erreur est survenue.");
            return false;
        }

        player.teleport(HomesFile.getInstance().getHomeLocation(player.getName(), args[0]));
        player.sendMessage("§aVous avez été transporté(e) à votre résidence.");

        return false;
    }

}
