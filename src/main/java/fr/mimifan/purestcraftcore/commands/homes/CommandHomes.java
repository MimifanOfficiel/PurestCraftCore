package fr.mimifan.purestcraftcore.commands.homes;

import fr.mimifan.purestcraftcore.api.configuration.HomesFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHomes implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)) return true;

        if(HomesFile.getInstance().getNumberOfHomes(player.getName()) == 0){
            player.sendMessage("§aVous n'avez placé aucune résidence pour le moment.");
            return true;
        }

        player.sendMessage(HomesFile.getInstance().listHomes(player.getName()).toString());

        return false;
    }
}
