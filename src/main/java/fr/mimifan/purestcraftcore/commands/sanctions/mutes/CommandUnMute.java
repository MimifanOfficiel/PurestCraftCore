package fr.mimifan.purestcraftcore.commands.sanctions.mutes;

import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandUnMute implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length < 1) return true;
        PurestCraftPlayer player = PlayerManager.getInstance().get(Bukkit.getPlayer(args[0]));

        if(player == null) return false;

        player.setMuted(false);
        commandSender.sendMessage("§aLe joueur §e" + args[0] + " §apeut à nouveau parler.");
        player.getPlayer().sendMessage("§aVous pouvez à nouveau parler.");

        return false;
    }
}
