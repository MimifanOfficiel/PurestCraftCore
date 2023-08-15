package fr.mimifan.purestcraftcore.commands.auth;

import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandRegister implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return true;
        if(args.length == 0) return false;

        PurestCraftPlayer purestCraftPlayer = PlayerManager.getInstance().get(player);
        if(purestCraftPlayer == null) return false;

        if(purestCraftPlayer.isRegistered()){
            player.sendMessage("§aVous êtes déjà enregistré(e).");
            return true;
        }

        purestCraftPlayer.register(args[0], player.getUniqueId());

        return false;
    }

}
