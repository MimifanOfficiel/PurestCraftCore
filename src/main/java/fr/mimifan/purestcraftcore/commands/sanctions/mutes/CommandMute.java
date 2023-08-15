package fr.mimifan.purestcraftcore.commands.sanctions.mutes;

import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandMute implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length < 1) return true;
        PurestCraftPlayer player = PlayerManager.getInstance().get(Bukkit.getPlayer(args[0]));

        if(player == null) return false;
        StringBuilder reason = new StringBuilder("§7Aucune raison fournie");
        if(args.length > 1){
            reason = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i].replace('&', '§')).append(" ");
            }
        }
        player.setMuted(true, reason.toString());
        commandSender.sendMessage("§aLe joueur §e" + args[0] + " §ane peut plus parler.");
        player.getPlayer().sendMessage("§cVous ne pouvez plus parler : " + reason);



        return true;
    }
}
