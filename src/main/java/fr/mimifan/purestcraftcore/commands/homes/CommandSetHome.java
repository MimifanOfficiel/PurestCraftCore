package fr.mimifan.purestcraftcore.commands.homes;

import fr.mimifan.purestcraftcore.api.configuration.HomesFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return true;

        if(args.length < 1){
            player.sendMessage("§cUtilisation: /sethome <nom>");
            return true;
        }

        if(HomesFile.getInstance().getNumberOfHomes(player.getName()) == 3 && !player.hasPermission("purestcraft.bypasshomes")){
            player.sendMessage("§cVous avez déjà 3 résidences.");
            return true;
        }

        HomesFile.getInstance().setHomeLocation(player.getName(), args[0], player.getLocation());
        HomesFile.getInstance().save();
        player.sendMessage("§aVotre résidence a été définie à votre emplacement.");

        return true;
    }
}
