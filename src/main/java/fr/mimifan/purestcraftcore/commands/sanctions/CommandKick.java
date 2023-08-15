package fr.mimifan.purestcraftcore.commands.sanctions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandKick implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length < 1) return true;
        StringBuilder reason = new StringBuilder("§cVous avez été expulsé(e) du serveur.");
        if(args.length > 1){
            reason = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i].replace('&', '§')).append(" ");
            }
        }
        Player player = Bukkit.getPlayer(args[0]);

        if(player == null) return false;

        player.kickPlayer("§6Purest§cCraft \n" + reason);
        commandSender.sendMessage("§aLe joueur §e" + args[0] + " §aa été expulsé du serveur.");

        return false;
    }
}
