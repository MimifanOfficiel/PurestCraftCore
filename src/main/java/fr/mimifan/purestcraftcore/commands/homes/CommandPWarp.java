package fr.mimifan.purestcraftcore.commands.homes;

import fr.mimifan.purestcraftcore.api.configuration.HomesFile;
import fr.mimifan.purestcraftcore.api.configuration.PWarpsFile;
import fr.mimifan.purestcraftcore.inventory.PWarpsInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandPWarp implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return true;
        if(args.length == 0){
            player.openInventory(new PWarpsInventory().getInventory());
        } else if(args.length == 1){
            if(args[0].equalsIgnoreCase("set")){
                PWarpsFile.getInstance().setLocation("PWarps." + player.getName(), player.getLocation());
                PWarpsFile.getInstance().save();
                player.sendMessage("§aVous avez défini votre warp ici. Les autres joueurs pourront s'y téléporter.");
            } else {
                try{
                    player.teleport(PWarpsFile.getInstance().getLocation("PWarps." + player.getName()));
                    player.sendMessage("§aVous avez été téléporté au warp de §e" + args[0] + "§a.");
                } catch (Exception e){
                    player.sendMessage("§cUne erreur est survenue.");
                }
            }
        }
        return true;
    }

}
