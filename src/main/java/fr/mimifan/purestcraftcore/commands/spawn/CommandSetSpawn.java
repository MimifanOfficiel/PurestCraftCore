package fr.mimifan.purestcraftcore.commands.spawn;

import fr.mimifan.purestcraftcore.api.configuration.ConfigurationFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)) return true;
        ConfigurationFile.getInstance().setLocation("spawn", player.getLocation());
        ConfigurationFile.getInstance().save();
        player.sendMessage("§aLe spawn a été défini à votre emplacement actuel.");

        return true;
    }

}
