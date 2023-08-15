package fr.mimifan.purestcraftcore.commands.auth;

import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandLogin implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return true;
        if(args.length == 0) return false;

        PurestCraftPlayer purestCraftPlayer = PlayerManager.getInstance().get(player);
        if(purestCraftPlayer == null) return false;

        if(!purestCraftPlayer.isRegistered()){
            player.sendMessage("§cVous n'êtes pas enregistré(e). Faites le avec /register <motdepasse>");
            return true;
        }

        if(purestCraftPlayer.isLogged()){
            player.sendMessage("§aVous êtes déjà connecté(e).");
            return true;
        }

        if(purestCraftPlayer.login(args[0])){
            purestCraftPlayer.login();
            player.sendMessage("§cConnexion réussie.");
        } else {
            player.sendMessage("§cMot de passe incorrect");
        }

        return false;
    }

}
